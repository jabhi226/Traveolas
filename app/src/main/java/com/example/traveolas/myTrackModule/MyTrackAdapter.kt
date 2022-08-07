package com.example.traveolas.myTrackModule

import android.annotation.SuppressLint
import android.widget.TextView
import com.example.traveolas.R
import com.example.traveolas.db.entites.MyTracks
import com.example.traveolas.utils.HelperBaseClasses.GenericListAdapter

@SuppressLint("ResourceType")
class MyTrackAdapter :
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
        }) {

}
