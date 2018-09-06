package com.example.damia.aktywnimobileapp.PRESENTER

import android.content.Context
import android.util.Log
import android.view.View
import com.beust.klaxon.Klaxon
import com.example.damia.aktywnimobileapp.MODEL.Event
import com.example.damia.aktywnimobileapp.MODEL.MainFragmentModel
import com.example.damia.aktywnimobileapp.VIEW.MainFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.Gson




class MainFragmentPresenter(context: MainFragment, fromMain: Boolean,event:String) {
    val model: MainFragmentModel
    val context2: MainFragment
    val fromMain:Boolean
    val event:String
    init {
        this.context2 = context
        this.model = MainFragmentModel()
        this.fromMain=fromMain
        this.event=event
    }

    fun result(result: String) {
        var root = JSONObject(result)

        var jsonArray = JSONArray(root.getString("info"))
        for (i in 0..(jsonArray.length() - 1)) {
            try {
                val event = jsonArray.getJSONObject(i)
                val eventToAdd = Event()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss aaa")
                try {
                    eventToAdd.date = dateFormat.parse(event.getString("date"))
                } catch (e: ParseException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                eventToAdd.typeOfSport = event.getString("disciplineId")
                eventToAdd.eventName = event.getString("name")
                eventToAdd.description = event.getString("description")
                eventToAdd.longitude = event.getString("geographicalCoordinates").split(';')[0].toDouble()
                eventToAdd.latitude = event.getString("geographicalCoordinates").split(';')[1].toDouble()
                eventToAdd.eventID = event.getInt("eventId")
                eventToAdd.objectID = event.getInt("objectId")

                try {
                    eventToAdd.dateCreated= dateFormat.parse(event.getString("date"))
                } catch (e: ParseException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

                model.listOfEvents.add(eventToAdd)
            } catch (e: Exception) {
            }
        }
        setMarkers()
    }

    fun setEvent() {
        if(event.equals("")) {
            val toSend = HashMap<String, String>()
            try {
                HTTPRequestAPI(this, "event", "result", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "GET").execute()
            } catch (e: Exception) {
            }
        }
        else
        {
        var ev:Event=Klaxon().parse<Event>(event)!!
            model.listOfEvents.add(ev)
            setMarkers()
        }
    }

    fun setMarkers() {
        for (item in model.listOfEvents) {
            context2.setMarker(item.latitude!!, item.longitude!!, item.eventName, item.typeOfSport)
        }
    }


}