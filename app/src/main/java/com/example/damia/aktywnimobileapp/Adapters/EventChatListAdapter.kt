package com.example.damia.aktywnimobileapp.Adapters

import android.app.ActionBar
import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.damia.aktywnimobileapp.MODEL.EventListItem
import com.example.damia.aktywnimobileapp.R
import kotlinx.android.synthetic.main.event_list_item.view.*
import java.util.ArrayList

import android.widget.TextView
import com.example.damia.aktywnimobileapp.MODEL.ChatValue
import com.example.damia.aktywnimobileapp.MODEL.SportObject
import kotlinx.android.synthetic.main.event_chat_list_item.view.*
import kotlinx.android.synthetic.main.event_list_item.view.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.list_of_sports_item.view.*
import android.widget.LinearLayout
import com.example.damia.aktywnimobileapp.PRESENTER.EventChatPresenter


class EventChatListAdapter(val items: MutableList<ChatValue>, val context: Context): RecyclerView.Adapter<ViewHolderChat>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChat  {
        return ViewHolderChat (LayoutInflater.from(context).inflate(R.layout.event_chat_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderChat , position: Int) {
        holder.textchat.setText(items.get(position).chatmessage)
        holder.userName.setText(items.get(position).nameUser+"\n"+items.get(position).date)
        if(items.get(position).isMineName)
        {
            val llp = LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
            llp.setMargins(200, 0, 20, 10)
            holder.textchat.layoutParams=llp
            holder.userName.layoutParams=llp
        }
        else
        {
            val llp = LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
            llp.setMargins(20, 0, 200, 10)
            holder.textchat.layoutParams=llp
            holder.userName.layoutParams=llp

        }
    }



    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolderChat (view: View) : RecyclerView.ViewHolder(view) {
    var userName = view.event_chat_user_name
    var textchat = view.event_chat_text


}

