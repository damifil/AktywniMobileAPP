package com.example.damia.aktywnimobileapp.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.*
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.PRESENTER.EventUsersPresenter
import com.example.damia.aktywnimobileapp.PRESENTER.FriendsPresenter
import com.example.damia.aktywnimobileapp.R
import com.example.damia.aktywnimobileapp.VIEW.EventChatFragment
import com.example.damia.aktywnimobileapp.VIEW.MainActivity
import com.example.damia.aktywnimobileapp.VIEW.UserProfileFragment
import kotlinx.android.synthetic.main.friends_list_item.view.*
import org.json.JSONObject
import java.util.HashMap

class UserEventAdapter(val items: MutableList<User>, val context: Context, val presenter: EventUsersPresenter) : RecyclerView.Adapter<ViewHolderUserEvent>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUserEvent {
        return ViewHolderUserEvent(LayoutInflater.from(context).inflate(R.layout.friends_list_item, parent, false))
    }

    var position: Int = 0
    var holder: ViewHolderUserEvent? = null
    fun resultRequest(result: String) {
        val jsonObject = JSONObject(result)
        if (jsonObject.getBoolean("response")) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
            holder!!.itemView.setVisibility(View.GONE)
            if (!items.any()) {
                //  presenter.updateInvitation()
            }
        } else {
            Toast.makeText(context, "wystąpił problem podczas akcji", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBindViewHolder(holder: ViewHolderUserEvent, position: Int) {
        this.holder = holder
        this.position = position
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


        if (presenter.model.userIsAdmin && !items.get(position).login.equals(presenter.model.adminLogin,true)) {
            holder?.tvIco.setTypeface(tf)

            holder?.tvIco.text = "\uf235"
            if (!items.get(position).isAccepted) {
                holder?.tvIco.setTextColor(Color.parseColor("#A0A0A0"))
                holder?.tvIco.text = ""
            }

            holder?.tvIco.setOnClickListener {
                val toSend = HashMap<String, String>()


              toSend["EventId"]=presenter.model.eventID.toString()
                toSend["UserId"]=items.get(position).userID.toString()


                try {
                    HTTPRequestAPI(this, "userEvent/remove", "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context, EnumChoice.token)), "DELETE").execute()
                } catch (e: Exception) {
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}


class ViewHolderUserEvent(view: View) : RecyclerView.ViewHolder(view) {
    val tvName = view.TVNameFriend
    val tvIco = view.TVICoChatFriend
    val tvIcoRemove = view.TVRemove
}