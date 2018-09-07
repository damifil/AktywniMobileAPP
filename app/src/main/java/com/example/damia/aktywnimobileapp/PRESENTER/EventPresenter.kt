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
import java.util.HashMap

class EventPresenter(val activity: EventFragment) {
    var model= EventModel()
    fun downloadEvents() {
        val toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "event/my", "eventsListResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(activity.context!!, EnumChoice.token)), "GET").execute()
        } catch (e: Exception) {
        }
    }

    fun eventsListResult(result: String) {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray: JSONArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                Log.i("HHHH",sports.valueOf("id"+item.getString("disciplineId")).toString())
                val event: EventListItem = EventListItem(item.getString("name"), item.getString("description"), item.getString("date"),  sports.valueOf("id"+item.getString("disciplineId")).toString()  )
                event.eventID = item.getInt("eventId")
                model.eventList.add(event)
            }
        }
        setList()
    }

    fun setList()
    {

    }


}