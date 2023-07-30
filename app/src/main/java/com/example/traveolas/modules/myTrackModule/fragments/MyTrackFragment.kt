package com.example.traveolas.modules.myTrackModule.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveolas.databinding.FragmentMyTrackBinding
import com.example.traveolas.db.daos.MyTracksDao
import com.example.traveolas.db.entites.MyTracks
import com.example.traveolas.modules.myTrackModule.activities.MyTrackActivity
import com.example.traveolas.modules.myTrackModule.adapters.MyTrackAdapter
import com.example.traveolas.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class MyTrackFragment : Fragment() {
//    BaseViewBindingFragment<FragmentMyTrackBinding>() {

    private var binding: FragmentMyTrackBinding? = null

    @Inject
    lateinit var myTrackDao: MyTracksDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyTrackBinding.inflate(LayoutInflater.from(requireContext()))
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding?.addNewTrack?.setOnClickListener {
            Utils.showToast(requireContext(), "Start to record new track")
        }
    }

    private fun initRecyclerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val clickEvent = object : MyTrackAdapter.OnTrackItemClickListener {
            override fun onTrackItemClicked(item: MyTracks) {
                (requireActivity() as MyTrackActivity).addFragmentToBackStack(MyTrackDetailsFragment())
            }

        }
        val adapter = MyTrackAdapter(clickEvent)
        binding?.recyclerView?.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            adapter.submitList(myTrackDao.getAllMyTracks())
        }
    }

}