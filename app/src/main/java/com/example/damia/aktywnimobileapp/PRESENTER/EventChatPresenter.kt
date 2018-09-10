package com.example.damia.aktywnimobileapp.PRESENTER

import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.ChatValue
import com.example.damia.aktywnimobileapp.MODEL.EventAddModel
import com.example.damia.aktywnimobileapp.MODEL.EventChatModel
import com.example.damia.aktywnimobileapp.VIEW.EventAddkFragment
import com.example.damia.aktywnimobileapp.VIEW.EventChatFragment
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class EventChatPresenter(context: EventChatFragment) {
    var model: EventChatModel
    val context2: EventChatFragment

    init {
        this.context2 = context
        this.model = EventChatModel()
    }

    fun downloadLatestMessage() {
        val toSend = HashMap<String, String>()
        toSend["EventId"] = model.eventId.toString()
        try {
            HTTPRequestAPI(this, "messageEvent/latest", "chatMessageLatestRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
        } catch (e: Exception) {
        }
    }

    fun updateChat()
    {
        val toSend = HashMap<String, String>()
        toSend["EventId"] = model.eventId.toString()
        try {
            HTTPRequestAPI(this, "messageEvent/unread", "unreadMessage", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
        } catch (e: Exception) {
        }
    }

    fun unreadMessage(result:String)
    {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val chatValue = ChatValue()
                chatValue.chatmessage = item.getString("content")
                chatValue.nameUser = item.getString("login")
                chatValue.isMineName = chatValue.nameUser.equals(sharedPreferenceApi.getString(context2.context!!, EnumChoice.choiceLogin),true)
                chatValue.messageId = item.getInt("messageId")
                chatValue.date=item.getString("date").replace('T',' ',true).dropLast(3)
                model.chatList.add(chatValue)
            }
            model.chatList.sortBy { it.messageId }
            context2.Notify()
        }
    }

    fun chatMessageLatestRequest(result: String) {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val chatValue = ChatValue()
                chatValue.chatmessage = item.getString("content")
                chatValue.nameUser = item.getString("login")
                chatValue.isMineName = chatValue.nameUser.equals(sharedPreferenceApi.getString(context2.context!!,EnumChoice.choiceLogin),true)
                chatValue.messageId=item.getInt("messageId")
                chatValue.date=item.getString("date").replace('T',' ',true).dropLast(3)
                model.chatList.add(chatValue)
            }
            context2.Notify()
        } else {
        Toast.makeText(context2.context,"wsytapił problem podczas pobierania",Toast.LENGTH_LONG).show()
        }
    }

    fun downloadBeforeMessage()
    {
        if (model.chatList.any()) {
            val toSend = HashMap<String, String>()
            toSend["EventId"] = model.eventId.toString()
            toSend["LatestMessageId"] = model.chatList.minBy { it.messageId }!!.messageId.toString()
            try {
                HTTPRequestAPI(this, "messageEvent/history", "chatMessagepreviousRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
            } catch (e: Exception) {
            }
        }
    }


    fun chatMessagepreviousRequest(result:String)
    {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val chatValue = ChatValue()
                chatValue.chatmessage = item.getString("content")
                chatValue.nameUser = item.getString("login")
                chatValue.isMineName = chatValue.nameUser.equals(sharedPreferenceApi.getString(context2.context!!,EnumChoice.choiceLogin),true)
                chatValue.messageId=item.getInt("messageId")
                chatValue.date=item.getString("date").replace('T',' ',true).dropLast(3)
                model.chatList.add(chatValue)
            }
            model.chatList.sortBy { it.messageId }
            context2.Notify()
        } else {
            Toast.makeText(context2.context,"wsytapił problem podczas pobierania",Toast.LENGTH_LONG).show()
        }
    }

    fun request() {
        if (false) {
            context2.Notify()
        }
    }

    fun sendMessage(message: String) {

        val toSend = HashMap<String, String>()
        toSend["EventId"] = model.eventId.toString()
        toSend["Content"]=message
        try {
            HTTPRequestAPI(this, "messageEvent/send", "sendMessageResponse", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "POST").execute()
        } catch (e: Exception) {
        }
     /*   val chatValue=ChatValue()
        try {
            chatValue.messageId = model.chatList.maxBy { it.messageId }!!.messageId + 1
        }catch (ex:Exception)
        {
            chatValue.messageId = 1
        }
     //   chatValue.nameUser=sharedPreferenceApi.getString(context2.context!!,EnumChoice.choiceLogin)
        chatValue.isMineName=true
        chatValue.chatmessage=message
        model.chatList.add(chatValue)
        context2.Notify()*/
    }

    fun sendMessageResponse(result:String)
    {
        val root = JSONObject(result)
        if (!root.getString("response").equals("True")) {
        Toast.makeText(context2.context,"wystąpił problem podczas wysyłania wiadomości", Toast.LENGTH_LONG).show()
        }
    }

}