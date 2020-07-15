package com.example.booster.ui.home

import android.animation.Animator
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.booster.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    val SPLASH_TIME_OUT: Long = 2000

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel


        frag_home_btn_alert.setOnClickListener {
            val intent = Intent(activity, AlertActivity::class.java)
            startActivity(intent)
        }

        Handler().postDelayed({

        }, SPLASH_TIME_OUT)

        frag_home_lt.setAnimation("1_door_open_0734.json")
        frag_home_lt.loop(true)
        frag_home_lt.playAnimation()
        frag_home_lt.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

}
