package com.example.damia.aktywnimobileapp.PRESENTER

import android.text.format.Time
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.ChatValue
import com.example.damia.aktywnimobileapp.MODEL.EventChatModel
import com.example.damia.aktywnimobileapp.VIEW.EventChatFragment
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*





class EventChatPresenter(context: EventChatFragment) {
    var model: EventChatModel
    val context2: EventChatFragment

    init {
        this.context2 = context
        this.model = EventChatModel()
    }

    fun downloadLatestMessage() {
        if (model.userId.equals("")) {
            val toSend = HashMap<String, String>()
            toSend["EventId"] = model.eventIdOrUserName.toString()
            try {
                HTTPRequestAPI(this, "messageEvent/latest", "chatMessageLatestRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
            } catch (e: Exception) {
            }
        } else {
            val toSend = HashMap<String, String>()
            toSend["FriendId"] = model.userId
            try {
                HTTPRequestAPI(this, "messageUser/latest", "chatMessageLatestRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
            } catch (e: Exception) {
            }
        }
    }

    fun updateChat() {
        if (model.userId.equals("")) {
            val toSend = HashMap<String, String>()
            toSend["EventId"] = model.eventIdOrUserName.toString()
            try {
                HTTPRequestAPI(this, "messageEvent/unread", "unreadMessage", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
            } catch (e: Exception) {
            }
        } else {
            val toSend = HashMap<String, String>()
            toSend["FriendId"] = model.userId
            try {
                HTTPRequestAPI(this, "messageUser/unread", "unreadMessage", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
            } catch (e: Exception) {
            }
        }
    }

    fun unreadMessage(result: String) {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val chatValue = ChatValue()
                chatValue.chatmessage = item.getString("content")
                if (model.eventIdOrUserName.equals(item.getString("userFromId"))) {
                    chatValue.nameUser = item.getString("login")
                } else {
                    chatValue.nameUser = sharedPreferenceApi.getString(context2.context!!, EnumChoice.choiceLogin)
                }
                chatValue.isMineName = chatValue.nameUser.equals(sharedPreferenceApi.getString(context2.context!!, EnumChoice.choiceLogin), true)
                chatValue.messageId = item.getInt("messageId")
                chatValue.date = item.getString("date").replace('T', ' ', true)
                model.chatList.add(chatValue)
            }
            model.chatList.sortedWith ( compareBy({ it.date }, { it.messageId })   )
            context2.Notify()
        }
    }

    fun chatMessageLatestRequest(result: String) {
        val root = JSONObject(result)
        if(model.userId.equals(""))
        {
            context2.setTitle(model.eventName)
        }
        else
        {
            context2.setTitle("Czat z użytkownikiem "+model.eventIdOrUserName)
        }
        if (root.getString("response").equals("True")) {
            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val chatValue = ChatValue()
                chatValue.chatmessage = item.getString("content")
                if(model.eventIdOrUserName.equals(item.getString("userFromId")))
                {
                    chatValue.nameUser =    item.getString("login")
                }
                else
                {
                    chatValue.nameUser =  sharedPreferenceApi.getString(context2.context!!, EnumChoice.choiceLogin)
                }
                chatValue.isMineName = chatValue.nameUser.equals(sharedPreferenceApi.getString(context2.context!!, EnumChoice.choiceLogin), true)
                chatValue.messageId = item.getInt("messageId")
                chatValue.date = item.getString("date").replace('T', ' ', true)
                model.chatList.add(chatValue)
            }


            context2.Notify()
        } else {
            Toast.makeText(context2.context, "wsytapił problem podczas pobierania", Toast.LENGTH_LONG).show()
        }
    }

    fun downloadBeforeMessage() {
        if (model.userId.equals("")) {
            if (model.chatList.any()) {
                val toSend = HashMap<String, String>()
                toSend["EventId"] = model.eventIdOrUserName.toString()
                toSend["LatestMessageId"] = model.chatList.minBy { it.messageId }!!.messageId.toString()
                try {
                    HTTPRequestAPI(this, "messageEvent/history", "chatMessagepreviousRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
                } catch (e: Exception) {
                }
            }
        } else {
            val toSend = HashMap<String, String>()
            toSend["FriendId"] = model.userId
            toSend["LatestMessageId"] = model.chatList.minBy { it.messageId }!!.messageId.toString()
            try {
                HTTPRequestAPI(this, "messageUser/history", "chatMessagepreviousRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "PUT").execute()
            } catch (e: Exception) {
            }
        }
    }


    fun chatMessagepreviousRequest(result: String) {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val chatValue = ChatValue()
                chatValue.chatmessage = item.getString("content")
                if(model.eventIdOrUserName.equals(item.getString("userFromId")))
                {
                    chatValue.nameUser =    item.getString("login")
                }
                else
                {
                    chatValue.nameUser =  sharedPreferenceApi.getString(context2.context!!, EnumChoice.choiceLogin)
                }
                chatValue.isMineName = chatValue.nameUser.equals(sharedPreferenceApi.getString(context2.context!!, EnumChoice.choiceLogin), true)
                chatValue.messageId = item.getInt("messageId")
                chatValue.date = item.getString("date").replace('T', ' ', true)
                model.chatList.add(chatValue)
            }
            val cmp = compareBy<ChatValue> { Date(it.date) }

            model.chatList.sortWith(cmp)
            context2.Notify()
        } else {
            Toast.makeText(context2.context, "wsytapił problem podczas pobierania", Toast.LENGTH_LONG).show()
        }
    }

    fun request() {
        if (false) {
            context2.Notify()
        }
    }

    fun sendMessage(message: String) {
        var toSend = HashMap<String, String>()
        val chatvalue = ChatValue()
        chatvalue.chatmessage=message
        chatvalue.isMineName=true
        val currentTime = Calendar.getInstance().time

        val format1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatted = format1.format(currentTime)

        chatvalue.date= formatted
        model.chatList.add(chatvalue)
        context2.Notify()
        if (model.userId.equals("")) {
            toSend["EventId"] = model.eventIdOrUserName.toString()
            toSend["Content"] = message

            try {
                HTTPRequestAPI(this, "messageEvent/send", "sendMessageResponse", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "POST").execute()
            } catch (e: Exception) {
            }
        } else {
            toSend["UserToId"] = model.userId
            toSend["Content"] = message
            try {
                HTTPRequestAPI(this, "messageUser/send", "sendMessageResponse", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "POST").execute()
            } catch (e: Exception) {
            }
        }
    }


    fun sendMessageResponse(result: String) {
        val root = JSONObject(result)
        if (!root.getString("response").equals("True")) {
            Toast.makeText(context2.context, "wystąpił problem podczas wysyłania wiadomości", Toast.LENGTH_LONG).show()
        }
    }

}