package com.example.damia.aktywnimobileapp.Adapters

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.friends_list_item.view.*
import kotlinx.android.synthetic.main.search_friend_item.view.*
import java.util.ArrayList




class AddFriendListAdapter(val items: ArrayList<User>, val context: Context): RecyclerView.Adapter<ViewHolderFriendAdd>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFriendAdd {
        return ViewHolderFriendAdd(LayoutInflater.from(context).inflate(R.layout.search_friend_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderFriendAdd, position: Int) {
        holder?.tvName.text=items.get(position).login

        holder?.tvName.setOnClickListener{
            Toast.makeText(context,"name user click "+ items[position].userID, Toast.LENGTH_LONG).show()

        }
        val tf = Typeface.createFromAsset(context.assets,
                "fonts/fa-solid-900.ttf")

        holder?.tvIco.setTypeface(tf)
        holder?.tvIco.text="\uf067"

        holder?.tvIco.setOnClickListener {

            Toast.makeText(context,"ico click "+ items[position].userID, Toast.LENGTH_LONG).show()
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolderFriendAdd (view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.TVNameFriendAdd
    val tvIco= view.TVICoAddFriend
}


