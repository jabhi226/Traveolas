package com.example.traveolas.modules.myTrackModule.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveolas.databinding.FragmentMyTrackDetailsBinding
import com.example.traveolas.db.entites.MyTracks
import com.example.traveolas.modules.myTrackModule.activities.MyTrackActivity
import com.example.traveolas.modules.myTrackModule.adapters.MyTrackAdapter
import com.example.traveolas.modules.myTrackModule.adapters.MyTrackDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyTrackDetailsFragment : Fragment() {

    var binding: FragmentMyTrackDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyTrackDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val clickEvent = object : MyTrackDetailAdapter.OnTrackDetailItemClickListener {
            override fun onTrackItemClicked(item: MyTracks) {
            }

        }
        val adapter = MyTrackDetailAdapter(clickEvent)
        binding?.recyclerView?.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
//            adapter.submitList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}