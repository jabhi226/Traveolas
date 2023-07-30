package com.example.traveolas.modules.myTrackModule.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import com.example.traveolas.databinding.ActivityMyTrackBinding
import com.example.traveolas.modules.myTrackModule.fragments.MyTrackFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyTrackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyTrackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTrackBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addFragmentToBackStack(MyTrackFragment())
    }

    fun addFragmentToBackStack(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(binding.activityMyTrackFrame.id, fragment, fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName)
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