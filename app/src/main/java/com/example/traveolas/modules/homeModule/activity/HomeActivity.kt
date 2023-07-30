package com.example.traveolas.modules.homeModule.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.traveolas.R
import com.example.traveolas.databinding.ActivityHomeBinding
import com.example.traveolas.modules.homeModule.adapters.ViewPagerAdapter
import com.example.traveolas.modules.homeModule.viewModel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 101
    }

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var viewBinding: ActivityHomeBinding
    private val nameOfTabs = arrayOf(
        "Community",
        "Profile",
        "Map",
        "Profile"
    )

    private val iconsOfTabs = arrayOf(
        R.drawable.ic_earth__southeast_asia1,
        R.drawable.ic_user__avatar_1,
        R.drawable.ic_map1,
        R.drawable.ic_user__avatar_1
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)


        askPermissions()

        initViewPager()
        viewBinding.viewPager.currentItem = 2
        viewModel.addDemoData()
    }

    private fun askPermissions() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.INTERNET
        )
        requestPermissionsIfNecessary(permissions)
    }

    private fun requestPermissionsIfNecessary(permissions: Array<String>) {
        val permissionsToRequest: ArrayList<String> = ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toArray(arrayOfNulls(0)),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest: ArrayList<String?> = ArrayList()
        for (i in grantResults.indices) {
            permissionsToRequest.add(permissions[i])
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toArray(arrayOfNulls(0)),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
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