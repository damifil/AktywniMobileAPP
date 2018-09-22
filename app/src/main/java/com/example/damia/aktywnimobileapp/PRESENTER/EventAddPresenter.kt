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
import kotlinx.android.synthetic.main.fragment_event_addk.*
import org.json.JSONObject
import java.util.HashMap


class EventAddPresenter(context: EventAddkFragment) {
    var model: EventAddModel
    val context2: EventAddkFragment

    init {
        this.context2 = context
        this.model = EventAddModel()
    }

    fun initEventChange()
    {
        val toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "event/"+model.eventId, "initEventChangeRequestResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)),"GET").execute()
        } catch (e: Exception) {
        }
    }

    fun initEventChangeRequestResult(result:String)
    {
        val jsonObject= JSONObject(result)
        if(jsonObject.getBoolean("response"))
        {
            val item= jsonObject.getJSONObject("info")
            model.date=item.getString("date")
            model.description=item.getString("description")
            model.eventName=item.getString("name")
            model.longitude= item.getString("longitude").toDouble()//("geographicalCoordinates").split(';')[0].toDouble()
            model.latitude=item.getString("latitude").toDouble()//getString("geographicalCoordinates").split(';')[1].toDouble()
            context2.partItemClicked(model.Sports[item.getInt("disciplineId")-2])
            context2.updateData()
        }
    }

    fun request() {
        var toSend = HashMap<String, String>()
        if(model.eventId<=0) {

            toSend["Name"] = model.eventName
            toSend["Date"] = model.date
            toSend["DisciplineId"] = context2.partItem.idEvent.toString()
            toSend["Description"] = model.description
            toSend["longitude"]=model.longitude.toString()
            toSend["latitude"]=model.latitude.toString()
            toSend["IsPrivate"]=context2.checkBoxPrivateEvent.isChecked.toString()

          //  toSend["GeographicalCoordinates"] = model.longitude.toString() + ';' + model.latitude

            if(sharedPreferenceApi.getString(context2.context!!, EnumChoice.isAdmin).equals("uzytkownik")) {
                try {
                    HTTPRequestAPI(this, "event/add", "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token))).execute()
                } catch (e: Exception) {
                }
            }
            else
            {
                val isPrivate:Boolean=context2.checkBoxPrivateEvent.isChecked
                //dodanie czy jest prywatny
                //zmiana http?
                try {
                    HTTPRequestAPI(this, "event/add", "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token))).execute()
                } catch (e: Exception) {
                }
            }
        }
        else
        {
            toSend["NewName"]=model.eventName
            try {
                HTTPRequestAPI(this, "event/changeName/"+model.eventId, "changeResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)),"PUT").execute()
            } catch (e: Exception) {
            }

            toSend = HashMap<String, String>()

            toSend["NewDescription"]=model.description
            try {
                HTTPRequestAPI(this, "changeDescritpion/"+model.eventId, "changeResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)),"PUT").execute()
            } catch (e: Exception) {
            }

            toSend = HashMap<String, String>()

            toSend["NewDate"]=model.date
            try {
                HTTPRequestAPI(this, "changeDate/"+model.eventId, "changeResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)),"PUT").execute()
            } catch (e: Exception) {
            }
            toSend = HashMap<String, String>()
            toSend["NewGeographicalCoordinates"]=model.longitude.toString() + ';' + model.latitude
            try {
                HTTPRequestAPI(this, "changeGeographicalCoordinates/"+model.eventId, "changeResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)),"PUT").execute()
            } catch (e: Exception) {
            }
        }
    }

    fun changeResult(result:String)
    {
        val jObj=JSONObject(result)
        if(!jObj.getBoolean("response"))
        {
            Toast.makeText(context2.context,jObj.getString("error"),Toast.LENGTH_SHORT).show()
        }
    }


    fun resultRequest(result: String) {
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