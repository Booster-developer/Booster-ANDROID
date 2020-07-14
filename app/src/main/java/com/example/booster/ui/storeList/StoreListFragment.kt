package com.example.booster.ui.storeList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2nd_seminar.ui.ItemDecorator
import com.example.booster.R
import com.example.booster.data.datasource.model.MarkerData
import com.example.booster.databinding.FragmentStoreListBinding
import com.example.booster.ui.storeDetail.MapActivity
import com.example.booster.ui.storeDetail.StoreDetailActivity
import com.example.booster.ui.storeDetail.StoreDetailViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_store_detail.*
import kotlinx.android.synthetic.main.fragment_store_list.*
import java.lang.Math.abs

class StoreListFragment : Fragment() {

    private val DIALOG_FRAGMENT = 1
    private lateinit var viewModel: StoreListViewModel
    private lateinit var viewModel2: StoreDetailViewModel
    lateinit var adapter: StoreListAdapter
    lateinit var binding: FragmentStoreListBinding
    var markers = arrayListOf<MarkerData>()
    var univData = "숭실대"
    var univIdx = 1
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
        viewModel.getStoreList(univIdx)
        if(univIdx==1){
            Log.e("onResume", "실행1")
        }else if(univIdx==2){
            Log.e("onResume", "실행2")
        }
        Log.e("onResume", "실행")
    }

    override fun onPause() {
        super.onPause()
        Log.e("onPause", "실행")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoreListViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(StoreDetailViewModel::class.java)

        initRv()
        setClick()
        setAppBar()
        viewModel.getStoreList(univIdx)
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
        frag_store_list_ll_univ.setOnClickListener {
            val univListDialog = StoreListDialogFragment()

            univListDialog.setTargetFragment(this, DIALOG_FRAGMENT)
            univListDialog.show(requireActivity().supportFragmentManager, "dialog")
        }

        frag_store_list_iv_map.setOnClickListener {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra("univ", frag_store_list_tv_univ.text )
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
            viewModel.getStoreList(univIdx)
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
                    viewModel2.putStoreFav(storeIdx)
                    viewModel2.favStatus.observe(requireActivity(), Observer {
                        Log.e("result -> ", it.message)
                        if(it.status==200){
                            imageView.setImageResource(R.drawable.store_ic_inactive_star)
                        } else if(it.status==201){
                            imageView.setImageResource(R.drawable.store_ic_active_star)
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
                        name = it[i].store_name
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
