package com.example.damia.aktywnimobileapp.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.damia.aktywnimobileapp.MODEL.Sport
import com.example.damia.aktywnimobileapp.MODEL.SportObject
import com.example.damia.aktywnimobileapp.R

import kotlinx.android.synthetic.main.list_of_sports_item.view.*

internal object ListOfSportIndex {
     var row_index=0
}


class ListOfSportsAdapter (val items: Array<SportObject>, val context: Context, val defaultSet:Boolean,  val clickListener: (SportObject) -> Unit):  RecyclerView.Adapter<ViewHolder2>()
{

init {
    if (!defaultSet)
    {
        ListOfSportIndex.row_index=-1
    }
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        return ViewHolder2(LayoutInflater.from(context).inflate(R.layout.list_of_sports_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {

        val tf = Typeface.createFromAsset(context.assets,
                "fonts/fa-solid-900.ttf")
        holder.tvIco.setTypeface(tf)
        holder.tvIco.text=items.get(position).name
        holder.bind(items[position], clickListener)

     /*   holder.tvIco.setOnClickListener(View.OnClickListener {
            row_index = position
            notifyDataSetChanged()
        })*/

        if (ListOfSportIndex.row_index === position) {
            holder.tvIco.setTextColor(context.resources.getColor(R.color.button_color_not_choice_alternative_green))

        } else {
            holder.tvIco.setTextColor(context.resources.getColor(R.color.button_color))
        }




    }

    override fun getItemCount(): Int {
        return items.size
    }

}
class ViewHolder2 (view: View) : RecyclerView.ViewHolder(view) {
    var tvIco = view.sportIco

    fun bind(part:SportObject, clickListener: (SportObject) -> Unit) {
        tvIco.text = part.code
        tvIco.setOnClickListener{
            clickListener(part)
            if(ListOfSportIndex.row_index==position)
            {
                ListOfSportIndex.row_index=-1
            }
            else {
                ListOfSportIndex.row_index = position
            }
        }
    }

}
