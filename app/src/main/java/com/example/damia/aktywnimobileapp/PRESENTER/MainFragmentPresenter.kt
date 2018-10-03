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
    var eventString:String
    init {
        this.context2 = context
        this.model = MainFragmentModel()
        this.fromMain=fromMain
        this.eventString=event
    }

    fun result(result: String) {
        var root = JSONObject(result)

        var jsonArray = JSONArray(root.getString("info"))
        var eventList:MutableList<Event> = arrayListOf()
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
                eventToAdd.latitude = event.getString("latitude").toDouble()//("geographicalCoordinates").split(';')[0].toDouble()
                eventToAdd.longitude = event.getString("longitude").toDouble()//("geographicalCoordinates").split(';')[1].toDouble()
                eventToAdd.eventID = event.getInt("eventId")
                eventToAdd.objectID = event.getInt("objectId")

                try {
                    eventToAdd.dateCreated= dateFormat.parse(event.getString("date"))
                } catch (e: ParseException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

                //model.listOfEvents.add(eventToAdd)
                eventList.add(eventToAdd)
            } catch (e: Exception) {
            }
        }


        for(item in eventList)
        {
            if(!model.listOfEvents.any())
            {
                val listToAdd:MutableList<Event> = arrayListOf()
                listToAdd.add(item)
                model.listOfEvents.add(listToAdd)
            }
            else
            {
                var add=false
                for(list in model.listOfEvents)
                {
                    if(list.first().latitude!!.equals(item.latitude)&& list.first().longitude!!.equals(item.longitude))
                    {
                        list.add(item)
                        add=true
                    }
                }
                if(!add)
                {
                    val listToAdd:MutableList<Event> = arrayListOf()
                    listToAdd.add(item)
                    model.listOfEvents.add(listToAdd)
                }
            }
        }

        setMarkers()
    }

    fun setEvent(event:String) {
        this.eventString=event
        if(eventString.equals("")) {
            val toSend = HashMap<String, String>()
            try {
                HTTPRequestAPI(this, "event", "result", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "GET").execute()
            } catch (e: Exception) {
            }
        }
        else
        {
            val eventToAdd= Event()
            val jobject = JSONObject(event)

            eventToAdd.latitude=jobject.getDouble("latitude")
            eventToAdd.longitude=jobject.getDouble("longitude")
            if(eventToAdd.longitude!=100000.0) {
                eventToAdd.description = jobject.getString("description")
                eventToAdd.eventName=jobject.getString("eventName")
                //  event.latitude=
                //var ev:Event=Klaxon().parse<Event>(event)!!
                var lsit: MutableList<Event> = arrayListOf()
                lsit.add(eventToAdd)
                model.listOfEvents.add(lsit)
                setMarkers()
            }
        }
    }

    fun setMarkers() {
        var i=0
        for (list in model.listOfEvents) {
            if(list.size>1)
            {
                context2.setMarker(list.first().latitude!!, list.first().longitude!!, "Wiele wydarzeń", "klinij by wyswietlic liste wydarzen")
            }
            else
            {
                context2.setMarker(list.first().latitude!!, list.first().longitude!!, list.first().eventName, list.first().typeOfSport)
            }
            i++
        }
    }


}