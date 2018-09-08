package com.example.damia.aktywnimobileapp.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.R
import java.util.ArrayList
import android.view.View
import android.widget.Toast
import com.example.damia.aktywnimobileapp.MODEL.User

import kotlinx.android.synthetic.main.friends_list_item.view.*

class FriendListAdapter(val items: ArrayList<User>, val context: Context) : RecyclerView.Adapter<ViewHolderFriend>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFriend {
        return ViewHolderFriend(LayoutInflater.from(context).inflate(R.layout.friends_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderFriend, position: Int) {


        holder?.tvName.text = items.get(position).login
        if (items.get(position).isAccepted) {
            holder?.tvName.setOnClickListener {
                Toast.makeText(context, "name user click " + items[position].userID, Toast.LENGTH_LONG).show()

            }
        } else {
            holder?.tvName.setTextColor(Color.parseColor("#A0A0A0"))
        }
        val tf = Typeface.createFromAsset(context.assets,
                "fonts/fa-solid-900.ttf")

        holder?.tvIco.setTypeface(tf)
        holder?.tvIco.text = "\uf075"
        if (items.get(position).isAccepted) {
            holder?.tvIco.setOnClickListener {

                Toast.makeText(context, "ico click " + items[position].userID, Toast.LENGTH_LONG).show()
            }
        }else{
            holder?.tvIco.setTextColor(Color.parseColor("#A0A0A0"))
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolderFriend(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.TVNameFriend
    val tvIco = view.TVICoChatFriend
}


