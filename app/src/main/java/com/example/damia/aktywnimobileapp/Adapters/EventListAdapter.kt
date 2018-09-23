package com.example.damia.aktywnimobileapp.Adapters

import android.app.Fragment
import com.example.damia.aktywnimobileapp.MODEL.EventListItem
import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.R
import java.util.ArrayList
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.event_list_item.view.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import android.support.v4.app.FragmentActivity
import com.example.damia.aktywnimobileapp.API.replaceFragment
import com.example.damia.aktywnimobileapp.API.replaceFragment
import android.support.v7.app.AppCompatActivity
import com.example.damia.aktywnimobileapp.VIEW.*


class EventListAdapter(val items: ArrayList<EventListItem>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvName.text = items.get(position).name
        holder?.tvDate.text = items.get(position).data
        holder?.tvDescription.text = items.get(position).description

        val tf = Typeface.createFromAsset(context.assets,
                "fonts/fa-solid-900.ttf")

        holder?.tvIco.setTypeface(tf)
        holder?.tvIco.text = items.get(position).sport
        holder.itemView.setOnClickListener {
            if (items.get(position).isEvent) {
                val newFragment = CurentEventFragment.newInstance(items.get(position).name, items.get(position).adminLogin)
                (context as MainActivity).replaceFragment(newFragment, R.id.body)
            } else {
                val newFragment= EventAddkFragment.newInstance(items.get(position).name,items.get(position).data , items.get(position).latitude, items.get(position).longitude,items.get(position).discipline,items.get(position).description)
                (context as MainActivity).replaceFragment(newFragment, R.id.body)
            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvDate = view.event_list_data
    val tvDescription = view.event_list_description
    val tvName = view.event_list_name
    val tvIco = view.event_list_ico


}


