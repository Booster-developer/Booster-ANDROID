package com.example.booster.ui.storeList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2nd_seminar.ui.ItemDecorator
import com.example.booster.R
import com.example.booster.data.datasource.model.MarkerData
import com.example.booster.data.datasource.model.StoreFavData
import com.example.booster.data.remote.network.BoosterServiceImpl
import com.example.booster.databinding.FragmentStoreListBinding
import com.example.booster.listener.onlyOneClickListener
import com.example.booster.ui.storeDetail.StoreDetailActivity
import com.example.booster.ui.storeDetail.StoreDetailViewModel
import com.example.booster.util.UserManager
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_store_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.abs

class StoreListFragment : Fragment() {

    private val DIALOG_FRAGMENT = 1
    private lateinit var viewModel: StoreListViewModel
    private lateinit var viewModel2: StoreDetailViewModel
    lateinit var adapter: StoreListAdapter
    lateinit var binding: FragmentStoreListBinding
    var markers = arrayListOf<MarkerData>()
    var univIdx = UserManager.univ
    var status = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_list, container, false)
        binding.lifecycleOwner = this@StoreListFragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        univIdx?.let { viewModel.getStoreList(it) }
        Log.e("stroeListFrag", "onResume")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoreListViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(StoreDetailViewModel::class.java)

        univIdx?.let { viewModel.getStoreList(it) }

        initRv()
        setClick()
        setAppBar()
        setUnivTv()
        refresh()
    }

    private fun refresh(){
        frag_store_list_srl.apply{
            setOnRefreshListener {
                univIdx?.let { viewModel.getStoreList(it) }
                this@apply.isRefreshing = false
            }
        }
    }

    private fun setAppBar(){
        frag_store_list_appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { frag_store_list_appBar, verticalOffset ->
            if (frag_store_list_appBar.totalScrollRange == 0 || verticalOffset == 0) {
                frag_store_list_iv_map.alpha = 1f
                return@OnOffsetChangedListener
            }
            val ratio = verticalOffset.toFloat() / frag_store_list_appBar.totalScrollRange.toFloat()
            frag_store_list_iv_map.alpha = 1f - abs(ratio)
        })
    }

    private fun setClick() {
        frag_store_list_ll_univ.onlyOneClickListener {
            val univListDialog = StoreListDialogFragment()

            univListDialog.setTargetFragment(this, DIALOG_FRAGMENT)
            univListDialog.show(requireActivity().supportFragmentManager, "dialog")
        }

        frag_store_list_iv_map.onlyOneClickListener {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra("univ", frag_store_list_tv_univ.text)
            intent.putParcelableArrayListExtra("marker", markers)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bundle = data!!.extras
        if(resultCode == DIALOG_FRAGMENT) {
            univIdx = bundle!!.getInt("univIdx")
            Log.e("univIdx", univIdx.toString())
            viewModel.getStoreList(univIdx!!)
            setUnivTv()
            Log.e("onactResult", bundle.getInt("univIdx").toString())
        }else{
            Log.e("onactResult", "fail")
        }
    }

    private fun initRv() {
        adapter = StoreListAdapter(requireContext(),
            object : StoreListViewHolder.onClickStoreItemListener {
                override fun onClickStoreItem(position: Int, storeIdx: Int) {
                    val intent = Intent(requireContext(), StoreDetailActivity::class.java)
                    intent.putExtra("storeIdx", storeIdx)
                    startActivity(intent)
                }
            },
            object : StoreListViewHolder.onClickFavListener {
                override fun onClickFav(position: Int, imageView: ImageView, fav: Int, storeIdx: Int) {
                    BoosterServiceImpl.service.putStoreFavRetrofit(storeIdx)
                        .enqueue(object : Callback<StoreFavData> {
                        override fun onFailure(call: Call<StoreFavData>, t: Throwable) {
                            //통신 실패
                            Log.e("putStoreFavRetrofit", "통신 실패")
                        }
                        override fun onResponse(
                            call: Call<StoreFavData>,
                            response: Response<StoreFavData>
                        ) {
                            //통신 성공
                            Log.e("putStoreFavRetrofit", response.body().toString())

                            val data = response.body()!!.status
                            if(data==201) {
                                imageView.setImageResource(R.drawable.store_ic_active_star)
                                univIdx?.let { viewModel.getStoreList(it) }
                            }
                            else if (data==200) {
                                imageView.setImageResource(R.drawable.store_ic_inactive_star)
                                univIdx?.let { viewModel.getStoreList(it) }
                            }
                        }

                    })
                }
            })
        frag_store_list_rv.adapter = adapter
        frag_store_list_rv.layoutManager = LinearLayoutManager(requireContext())
        frag_store_list_rv.addItemDecoration(ItemDecorator(24))

        viewModel.storeList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
            markers.clear()
            for(i in 0 .. it.size-1){
                markers.add(
                    MarkerData(
                        latitude = it[i].store_x_location,
                        longitude = it[i].store_y_location,
                        name = it[i].store_name,
                        idx = it[i].store_idx
                    )
                )
            }
        })
    }

    fun setUnivTv() {
        if(univIdx == 1){
            frag_store_list_tv_univ.text = "숭실대학교"
        }
        else if(univIdx == 2){
            frag_store_list_tv_univ.text = "중앙대학교"
        }
        else{
            frag_store_list_tv_univ.text = "서울대학교"
        }
    }
}
