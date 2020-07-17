package com.example.booster.ui.home

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.booster.R
import com.example.booster.databinding.FragmentHomeBinding
import com.example.booster.listener.onlyOneClickListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.vm = (this@HomeFragment).viewModel
        setClick()
        viewModel.getHome()
        viewModel.homeRes.observe(requireActivity(), Observer {
            frag_home_user_name.text = it.data.user_name
            when (it.data.home_state) {
                0 -> {
                    frag_home_lt.setAnimation("home_s8_1.json")
                    frag_home_txt2.text = "인쇄를 시작해볼까요?"
                }
                1, 2 -> {
                    frag_home_lt.setAnimation("home_s8_2.json")
                    frag_home_txt2.text = "인쇄 진행 중이에요."
                }
                else -> {
                    frag_home_lt.setAnimation("home_s8_3.json")
                    frag_home_txt2.text = "인쇄가 완료되었어요 :)"
                }
            }
        })

//        setLottie()

        frag_home_lt.loop(true)
        frag_home_lt.playAnimation()

        parentFragmentManager.addOnBackStackChangedListener {
            viewModel.getHome()
            frag_home_lt.playAnimation()
            Log.e("onResume", "실행")
        }
    }

    override fun onPause() {
        super.onPause()
        frag_home_lt.cancelAnimation()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHome()
//        frag_home_lt.setAnimation("home_s8_1.json")
        frag_home_lt.loop(true)
        frag_home_lt.playAnimation()
        Log.e("HomeFrag", "onResume")
    }

    fun setLottie(){
        when (viewModel.homeRes.value!!.data.home_state) {
            0 -> {
                frag_home_lt.setAnimation("home_s8_1.json")
                frag_home_txt2.text = "인쇄를 시작해볼까요?"
            }
            1, 2 -> {
                frag_home_lt.setAnimation("home_s8_2.json")
                frag_home_txt2.text = "인쇄 진행 중이에요."
            }
            else -> {
                frag_home_lt.setAnimation("home_s8_3.json")
                frag_home_txt2.text = "인쇄가 완료되었어요 :)"
            }
        }
        frag_home_lt.repeatCount = 2
        frag_home_lt.playAnimation()
    }

    fun setClick() {
        frag_home_btn_alert.onlyOneClickListener {
            val intent = Intent(activity, AlertActivity::class.java)
            startActivity(intent)
        }
    }
}
