package com.example.damia.aktywnimobileapp.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.R
import android.view.View
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.*
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.PRESENTER.FriendsPresenter
import com.example.damia.aktywnimobileapp.VIEW.*

import kotlinx.android.synthetic.main.friends_list_item.view.*
import org.json.JSONObject
import java.util.HashMap

class FriendListAdapter(val items: MutableList<User>, val context: Context,val presenter:FriendsPresenter) : RecyclerView.Adapter<ViewHolderFriend>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFriend {
        return ViewHolderFriend(LayoutInflater.from(context).inflate(R.layout.friends_list_item, parent, false))
    }
    var position:Int=0
    var holder:ViewHolderFriend?=null
    fun resultRequest(result:String)
    {
        val jsonObject=JSONObject(result)
        if(jsonObject.getBoolean("response")) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
            holder!!.itemView.setVisibility(View.GONE)
            if(!items.any())
            {
                presenter.updateInvitation()
            }
        }
        else
        {
            Toast.makeText(context,"wystąpił problem podczas akcji",Toast.LENGTH_LONG).show()
        }
    }

    override fun onBindViewHolder(holder: ViewHolderFriend, position: Int) {
        this.holder=holder
        this.position=position
        holder?.tvName.text = items.get(position).login
        holder?.tvName.setOnClickListener {

            val newFragment = UserProfileFragment.newInstance(items[position].userID.toString(), "")
            (context as MainActivity).replaceFragment(newFragment, R.id.body)
        }
        if (!items.get(position).isAccepted && !items.get(position).isFromInvite) {
            holder?.tvName.setTextColor(Color.parseColor("#A0A0A0"))
        }
        val tf = Typeface.createFromAsset(context.assets,
                "fonts/fa-solid-900.ttf")

        holder?.tvIco.setTypeface(tf)
        if (items.get(position).isFromInvite) {
            holder?.tvIco.text = "\uf4fc"
            holder?.tvIco.setOnClickListener {

                val toSend = HashMap<String, String>()
                try {
                    HTTPRequestAPI(this, "friend/"+items.get(position).userID, "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context, EnumChoice.token)), "PUT").execute()
                } catch (e: Exception) {
                }
              //dodajemy
            }
            holder.tvIcoRemove.visibility=View.VISIBLE
            holder.tvIcoRemove.setTypeface(tf)
            holder.tvIcoRemove.text="\uf235"
            holder.tvIcoRemove.setOnClickListener{
                val toSend = HashMap<String, String>()
                try {
                    HTTPRequestAPI(this, "friend/"+items.get(position).userID, "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context, EnumChoice.token)), "DELETE").execute()
                } catch (e: Exception) {
                }
            }
        } else {
            holder?.tvIco.text = "\uf075"
            if(items.get(position).isAccepted)
            {
                holder?.tvIco.setOnClickListener {
                    val newFragment = EventChatFragment.newInstance(items.get(position).login, items.get(position).userID.toString(),"")
                    (context as MainActivity).replaceFragment(newFragment, R.id.body)
                }
            }
            else
            {
                holder?.tvIco.visibility=View.INVISIBLE
                holder?.tvIco.setTextColor(Color.parseColor("#A0A0A0"))
                holder?.tvIco.text = ""
            }
        }



    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolderFriend(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.TVNameFriend
    val tvIco = view.TVICoChatFriend
    val tvIcoRemove=view.TVRemove
}


