package com.example.damia.aktywnimobileapp.PRESENTER

import android.util.Log
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.EventListItem
import com.example.damia.aktywnimobileapp.MODEL.EventModel
import com.example.damia.aktywnimobileapp.MODEL.sports
import com.example.damia.aktywnimobileapp.VIEW.EventFragment
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class EventPresenter(val activity: EventFragment) {
    var model = EventModel()
    fun downloadEvents() {
        val toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "event/my", "eventsListResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(activity.context!!, EnumChoice.token)), "GET").execute()
        } catch (e: Exception) {
        }

         try {
             HTTPRequestAPI(this, "userEvent/myInvitations", "eventsInvitationsListResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(activity.context!!, EnumChoice.token)), "GET").execute()
         } catch (e: Exception) {
         }


    }

    fun eventsListResult(result: String) {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray: JSONArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val event: EventListItem = EventListItem(item.getString("name"), item.getString("description"), item.getString("date").replace('T', ' '), sports.values()[item.getInt("disciplineId") - 2].ico)
                event.eventID = item.getInt("eventId")
                event.adminLogin = item.getString("adminLogin")

                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date: Date = df.parse(event.data)
                val currentTime = Calendar.getInstance().time

                if (!currentTime.after(date)) {
                    model.eventList.add(event)
                }
            }
        }
        setList()
    }

    fun eventsInvitationsListResult(result: String) {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray: JSONArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val event: EventListItem = EventListItem(item.getString("eventName"), " ", item.getString("date").replace('T', ' '), "")
                event.eventID = item.getInt("eventId")


                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date: Date = df.parse(event.data)
                val currentTime = Calendar.getInstance().time
                model.eventInvitationList.add(event)
            }
        }
        setList2()

    }


    fun setList() {
        activity.setAdapter()
    }


    fun setList2() {
        activity.setAdapter2()
    }

}