package com.example.booster.ui.store

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2nd_seminar.ui.ItemDecorator

import com.example.booster.R
import kotlinx.android.synthetic.main.fragment_store_list.*

class StoreListFragment : Fragment() {

    private lateinit var viewModel: StoreListViewModel
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
        viewModel = ViewModelProviders.of(this).get(StoreListViewModel::class.java)
        initRv()
        setClick()
    }


    fun setClick(){
        frag_store_list_ll_univ.setOnClickListener {
            val univListDialog = StoreListDialongFragment()
            univListDialog.show(requireActivity().supportFragmentManager,"schedule_dialog_fragment")
        }
    }

    private fun initRv(){
        adapter = StoreListAdapter(requireContext())
        frag_store_list_rv.adapter = adapter
        frag_store_list_rv.layoutManager = LinearLayoutManager(requireContext())
        frag_store_list_rv.addItemDecoration(ItemDecorator(24))

        viewModel.storeList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }
}