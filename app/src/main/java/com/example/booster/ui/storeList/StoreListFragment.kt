package com.example.booster.ui.storeList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.booster.ui.storeDetail.MapActivity
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2nd_seminar.ui.ItemDecorator
import com.example.booster.R
import com.example.booster.ui.storeDetail.StoreDetailActivity
import com.example.booster.ui.storeDetail.StoreDetailViewModel
import kotlinx.android.synthetic.main.fragment_store_list.*
import kotlinx.android.synthetic.main.item_store_list.*


class StoreListFragment : Fragment() {

    private lateinit var viewModel: StoreListViewModel
    private lateinit var viewModel2: StoreDetailViewModel
    lateinit var adapter: StoreListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_store_list, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoreListViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(StoreDetailViewModel::class.java)
        initRv()
        setClick()
    }

    private fun setClick() {
        frag_store_list_ll_univ.setOnClickListener {
            val univListDialog = StoreListDialongFragment()
            univListDialog.show(
                requireActivity().supportFragmentManager,
                "schedule_dialog_fragment"
            )
        }

        frag_store_list_iv_map.setOnClickListener {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra("univ", frag_store_list_tv_univ.text)
            startActivity(intent)
        }
    }

    private fun initRv() {
        adapter = StoreListAdapter(requireContext(),
            object : StoreListViewHolder.onClickStoreItemListener {
                override fun onClickStoreItem(position: Int) {
                    val intent = Intent(requireContext(), StoreDetailActivity::class.java)
                    intent.putExtra("position", position)
                    startActivity(intent)
                }
            },
            object : StoreListViewHolder.onclickFavListener {
                override fun onClickFav(position: Int,imageView: ImageView) {
                    Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
                    imageView.setImageResource(R.drawable.store_ic_active_star)
                    viewModel2.postStoreFav(position+1)
                }
            })
        frag_store_list_rv.adapter = adapter
        frag_store_list_rv.layoutManager = LinearLayoutManager(requireContext())
        frag_store_list_rv.addItemDecoration(ItemDecorator(24))

        viewModel.storeList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }

}
