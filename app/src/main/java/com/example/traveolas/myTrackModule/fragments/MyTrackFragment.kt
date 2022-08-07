package com.example.traveolas.myTrackModule.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.traveolas.databinding.FragmentMyTrackBinding
import com.example.traveolas.db.daos.MyTracksDao
import com.example.traveolas.myTrackModule.MyTrackAdapter
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
//        binding = super._binding
        binding = FragmentMyTrackBinding.inflate(LayoutInflater.from(requireContext()))
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyTrackAdapter()
        binding?.recyclerView?.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            try {
                adapter.submitList(myTrackDao.getAllMyTracks())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}