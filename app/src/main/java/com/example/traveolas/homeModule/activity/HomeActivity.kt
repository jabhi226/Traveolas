package com.example.traveolas.homeModule.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.traveolas.R
import com.example.traveolas.databinding.ActivityHomeBinding
import com.example.traveolas.homeModule.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class HomeActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityHomeBinding
    private val nameOfTabs = arrayOf(
        "Community",
        "Map",
        "Profile",
        "Profile"
    )

    private val iconsOfTabs = arrayOf(
        R.drawable.ic_baseline_home_24,
        R.drawable.ic_baseline_map_24,
        R.drawable.ic_baseline_person_24,
        R.drawable.ic_baseline_person_24
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)

        initViewPager()
    }

    private fun initViewPager() {
        viewBinding.viewPager.apply {
            adapter = ViewPagerAdapter(supportFragmentManager, this@HomeActivity.lifecycle)
            isUserInputEnabled = false
        }
        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = nameOfTabs[position]
            tab.icon = ContextCompat.getDrawable(this, iconsOfTabs[position])

            // to indicator round
//            tab.view.layoutParams = ViewGroup.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT
//            )
//            print("MY_PARENT_VIEW --> ")
//            when (tab.view.parent) {
//                is TextView -> {
//                    println("TextView | ")
//                }
//                is ImageView -> {
//                    println("ImageView")
//                }
//                is LinearLayout -> {
//                    println("LinearLayout")
//                }
//                is RelativeLayout -> {
//                    println("RelativeLayout")
//                }
//                is ConstraintLayout -> {
//                    println("ConstraintLayout")
//                }
//                else -> {
//                    println("else")
//                }
//            }
//            print("MY_VIEW --> ")
//            for (i in tab.view.children) {
//                when (i) {
//                    is TextView -> {
//                        println("TextView | ${i.text}")
//                    }
//                    is ImageView -> {
//                        println("ImageView")
//                    }
//                    is LinearLayout -> {
//                        println("LinearLayout")
//                    }
//                    is RelativeLayout -> {
//                        println("RelativeLayout")
//                    }
//                    is ConstraintLayout -> {
//                        println("ConstraintLayout")
//                    }
//                    else -> {
//                        println("else")
//                    }
//                }
//            }
//            val view = LayoutInflater.from(this).inflate(R.layout.tab_custom_view, null)
//            view.findViewById<ImageView>(R.id.img)
//                .setImageDrawable(ContextCompat.getDrawable(this, iconsOfTabs[position]))
//            view.findViewById<TextView>(R.id.txt).text = nameOfTabs[position]
//            if (position == viewBinding.viewPager.currentItem) {
//                view.background = ContextCompat.getDrawable(this, R.drawable.round_blue)
//            }
//            tab.customView = view
        }.attach()
    }
}