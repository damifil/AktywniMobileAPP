package com.example.damia.aktywnimobileapp.PRESENTER

import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.EventAddModel
import com.example.damia.aktywnimobileapp.R
import com.example.damia.aktywnimobileapp.VIEW.CurentEventFragment
import com.example.damia.aktywnimobileapp.VIEW.EventAddkFragment
import com.example.damia.aktywnimobileapp.VIEW.MainActivity
import org.json.JSONObject
import java.util.HashMap


class EventAddPresenter(context: EventAddkFragment) {
    var model: EventAddModel
    val context2: EventAddkFragment

    init {
        this.context2 = context
        this.model = EventAddModel()
    }

    fun request() {
        val toSend = HashMap<String, String>()
        toSend["Name"] = model.eventName
        toSend["Date"] = model.date
        toSend["DisciplineId"] = context2.partItem.idEvent.toString()
        toSend["Description"] = model.description
        toSend["GeographicalCoordinates"] = model.longitude.toString() + ';' + model.latitude


        try {
            HTTPRequestAPI(this, "event/add", "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token))).execute()
        } catch (e: Exception) {
        }

    }

    fun resultRequest(result: String) {
        Log.i("aaaaaddresponse",result)
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
               val newFragment = CurentEventFragment.newInstance(model.eventName,"")
                  model.resetData()
                  sharedPreferenceApi.set(context2.context!!, Klaxon().toJsonString(model),EnumChoice.EventAddPresenter)
                  val transaction = context2.fragmentManager!!.beginTransaction()
                  transaction.replace(R.id.body, newFragment)
                  transaction.addToBackStack(null)
                  transaction.commit()






        } else {

            Toast.makeText(context2.context,root.getString("error"),Toast.LENGTH_SHORT).show()
        }
    }

}