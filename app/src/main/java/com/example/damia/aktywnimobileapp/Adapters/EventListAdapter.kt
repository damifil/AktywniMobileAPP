package com.example.damia.aktywnimobileapp.Adapters

import com.example.damia.aktywnimobileapp.MODEL.EventListItem
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.R
import java.util.ArrayList
import android.view.View
import kotlinx.android.synthetic.main.event_list_item.view.*
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventListAdapter(val items:ArrayList<EventListItem>, val context: Context): RecyclerView.Adapter<ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvName.text=items.get(position).name
        holder?.tvDate.text=items.get(position).data
        holder?.tvDescription.text=items.get(position).description
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tvDate =    view.event_list_data
    val tvDescription = view.event_list_description
    val tvName =  view.event_list_name
}


