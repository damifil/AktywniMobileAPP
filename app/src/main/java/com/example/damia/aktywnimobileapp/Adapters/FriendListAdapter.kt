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
import com.example.damia.aktywnimobileapp.API.*
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.VIEW.CurentEventFragment
import com.example.damia.aktywnimobileapp.VIEW.MainActivity
import com.example.damia.aktywnimobileapp.VIEW.UserProfileFragment

import kotlinx.android.synthetic.main.friends_list_item.view.*
import org.json.JSONObject
import java.util.HashMap

class FriendListAdapter(val items: ArrayList<User>, val context: Context) : RecyclerView.Adapter<ViewHolderFriend>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFriend {
        return ViewHolderFriend(LayoutInflater.from(context).inflate(R.layout.friends_list_item, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolderFriend, position: Int) {


        holder?.tvName.text = items.get(position).login
        holder?.tvName.setOnClickListener {

            val newFragment = UserProfileFragment.newInstance(items[position].userID.toString(), "")
            (context as MainActivity).replaceFragment(newFragment, R.id.body)
        }
        if (!items.get(position).isAccepted) {
            holder?.tvName.setTextColor(Color.parseColor("#A0A0A0"))
        }
        val tf = Typeface.createFromAsset(context.assets,
                "fonts/fa-solid-900.ttf")

        holder?.tvIco.setTypeface(tf)
        holder?.tvIco.text = "\uf075"
        if (items.get(position).isAccepted) {
            holder?.tvIco.setOnClickListener {

               // val newFragment = UserProfileFragment.newInstance(items[position].userID.toString(), "")
              //  (context as MainActivity).replaceFragment(newFragment, R.id.body)
                Toast.makeText(context, "ico click " + items[position].userID, Toast.LENGTH_LONG).show()
            }
        } else {
            holder?.tvIco.setTextColor(Color.parseColor("#A0A0A0"))
            holder?.tvIco.text = ""

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


