package com.example.damia.aktywnimobileapp.Adapters

import android.content.Context
import android.graphics.Typeface
import android.provider.Contacts.PresenceColumns.INVISIBLE
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.friends_list_item.view.*
import kotlinx.android.synthetic.main.search_friend_item.view.*
import java.util.ArrayList
import java.util.HashMap


class AddFriendListAdapter(val items: ArrayList<User>, val context: Context): RecyclerView.Adapter<ViewHolderFriendAdd>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFriendAdd {
        return ViewHolderFriendAdd(LayoutInflater.from(context).inflate(R.layout.search_friend_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderFriendAdd, position: Int) {
        holder?.tvName.text=items.get(position).login

        holder?.tvName.setOnClickListener{
        }
        val tf = Typeface.createFromAsset(context.assets,
                "fonts/fa-solid-900.ttf")

        holder?.tvIco.setTypeface(tf)
        holder?.tvIco.text="\uf067"
        if(!items.get(position).isFriend) {
            holder?.tvIco.setOnClickListener {
                val toSend = HashMap<String, String>()
                toSend["friendID"] = items.get(position).userID.toString()
                try {
                    HTTPRequestAPI(this, "friend/addFriend", "", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context, EnumChoice.token)), "POST").execute()
                } catch (e: Exception) {
                }
                holder?.tvIco.visibility = View.INVISIBLE
                Toast.makeText(context, "ico click " + items[position].userID, Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            holder?.tvIco.visibility = View.INVISIBLE
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


