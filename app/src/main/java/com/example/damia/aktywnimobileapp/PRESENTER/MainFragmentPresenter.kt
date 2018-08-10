package com.example.damia.aktywnimobileapp.PRESENTER

import android.content.Context
import android.util.Log
import android.view.View
import com.example.damia.aktywnimobileapp.MODEL.Event
import com.example.damia.aktywnimobileapp.MODEL.MainFragmentModel
import com.example.damia.aktywnimobileapp.VIEW.MainFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import android.app.Activity
import com.example.damia.aktywnimobileapp.R
import com.example.damia.aktywnimobileapp.VIEW.MainActivity
import kotlinx.android.synthetic.main.activity_main.view.*
import android.view.LayoutInflater
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainFragmentPresenter(context: MainFragment) {
    val model: MainFragmentModel
    val context2:MainFragment
    init {
       this.context2 =context
        this.model = MainFragmentModel()
        // zassanie z serwera eventow
    //przykładowe dane
        var date: Date?=null
        val format = SimpleDateFormat("yyyy-MM-dd")
        try {
           date  = format.parse("2018-08-20")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        var event=Event()
        event.description="opis wydarzeenia"
        event.eventName="event1"
        event.eventValue=0
        event.typeOfSport="piłka nożna"
        event.latitude=30.0
        event.longitude=20.0
        event.date= date
        model.listOfEvents.add(event)

        var event2=Event()
        event2.description="opis wydarzeenia"
        event2.eventName="event1"
        event2.eventValue=1
        event2.typeOfSport="piłka nożna"
        event2.latitude=30.0
        event2.longitude=22.0
        event2.date= date
        model.listOfEvents.add(event2)

        var event3=Event()
        event3.description="opis wydarzeenia"
        event3.eventName="event1"
        event3.eventValue=2
        event3.typeOfSport="piłka nożna"
        event3.latitude=27.0
        event3.longitude=21.0
        event3.date= date
        model.listOfEvents.add(event3)
        ///
    }

    fun setEvent() {
        setMarkers()
    }

    fun setMarkers() {

        for (item in model.listOfEvents)
        {
            context2.setMarker(item.latitude!!,item.longitude!!,item.eventName,item.typeOfSport)
        }
    }



}