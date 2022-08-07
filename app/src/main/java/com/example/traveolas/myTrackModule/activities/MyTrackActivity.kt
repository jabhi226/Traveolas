package com.example.traveolas.myTrackModule.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.example.traveolas.R
import com.example.traveolas.databinding.ActivityMyTrackBinding
import com.example.traveolas.myTrackModule.fragments.MyTrackFragment
import com.example.traveolas.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyTrackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyTrackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTrackBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(MyTrackFragment())
    }

    private fun replaceFragment(fragment: MyTrackFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.activityMyTrackFrame.id, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.my_track_menu, menu)
//        return true
//    }
}