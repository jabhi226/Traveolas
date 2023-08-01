package com.example.traveolas.modules.homeModule.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.traveolas.modules.homeModule.fragments.CommunityFragment
import com.example.traveolas.modules.homeModule.fragments.MapFragment
import com.example.traveolas.modules.homeModule.fragments.NewMapFragment
import com.example.traveolas.modules.homeModule.fragments.ProfileFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        const val NUMBER_OF_TABS = 4
    }

    override fun getItemCount(): Int {
        return NUMBER_OF_TABS
    }

    override fun createFragment(position: Int): Fragment {
        println("position -> $position")
        return when (position) {
            0 -> {
                CommunityFragment()
            }
            2 -> {
                NewMapFragment.newInstance()
            }
            1 -> {
                ProfileFragment()
            }
            else -> {
                CommunityFragment()
            }
        }
    }
}