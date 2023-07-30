package com.example.traveolas.modules.myTrackModule.adapters

import android.annotation.SuppressLint
import android.widget.TextView
import com.example.traveolas.R
import com.example.traveolas.db.entites.MyTracks
import com.example.traveolas.utils.helperBaseClasses.GenericListAdapter

@SuppressLint("ResourceType")
class MyTrackAdapter(onTrackItemClickListener: OnTrackItemClickListener) :
    GenericListAdapter<MyTracks>(
        layoutId = R.layout.item_my_track_list,
        bind = { item, holder, itemCount ->
            val v = holder.itemView
            val date = v.findViewById<TextView>(R.id.date)
            val name = v.findViewById<TextView>(R.id.name)
            val dist = v.findViewById<TextView>(R.id.distance)
            date.text = item.name
            name.text = item.date
            dist.text = item.distance
            holder.itemView.setOnClickListener { onTrackItemClickListener.onTrackItemClicked(item) }
        }) {

    interface OnTrackItemClickListener {
        fun onTrackItemClicked(item: MyTracks)
    }
}
