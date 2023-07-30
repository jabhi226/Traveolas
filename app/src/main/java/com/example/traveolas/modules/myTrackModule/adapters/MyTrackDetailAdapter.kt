package com.example.traveolas.modules.myTrackModule.adapters

import android.widget.TextView
import com.example.traveolas.R
import com.example.traveolas.db.entites.MyTracks
import com.example.traveolas.utils.helperBaseClasses.GenericListAdapter

class MyTrackDetailAdapter(onTrackDetailItemClickListener: OnTrackDetailItemClickListener) :
    GenericListAdapter<MyTracks>(
        layoutId = R.layout.item_my_track_detail_list,
        bind = { item, holder, itemCount ->
            val v = holder.itemView
            val position = v.findViewById<TextView>(R.id.id)
            val latitude = v.findViewById<TextView>(R.id.latitude)
            val name = v.findViewById<TextView>(R.id.name)
            val longitude = v.findViewById<TextView>(R.id.longitude)
            name.text = item.date
            holder.itemView.setOnClickListener {
                onTrackDetailItemClickListener.onTrackItemClicked(
                    item
                )
            }
        }) {

    interface OnTrackDetailItemClickListener {
        fun onTrackItemClicked(item: MyTracks)
    }

}