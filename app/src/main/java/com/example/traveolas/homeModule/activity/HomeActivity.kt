package com.example.traveolas.homeModule.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        }
        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = nameOfTabs[position]
            tab.icon = ContextCompat.getDrawable(this, iconsOfTabs[position])
        }.attach()
    }
}