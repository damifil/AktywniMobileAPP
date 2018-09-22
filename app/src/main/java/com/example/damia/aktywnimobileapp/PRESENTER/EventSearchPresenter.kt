package com.example.damia.aktywnimobileapp.PRESENTER

import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.EventListItem
import com.example.damia.aktywnimobileapp.MODEL.EventSearchModel
import com.example.damia.aktywnimobileapp.MODEL.sports
import com.example.damia.aktywnimobileapp.VIEW.EventSearchFragment
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EventSearchPresenter(fragment:EventSearchFragment)
{
    val model=EventSearchModel()
    val fragment=fragment
    fun search(name:String,eventID:Int)
    {
        model.eventList.clear()
        val toSend = HashMap<String, String>()
        if(!name.equals(""))
        {
            if(eventID>-1)
            {
                toSend["Name"]=name
                toSend["DisciplineId"]=eventID.toString()

                try {
                    HTTPRequestAPI(this, "event/searchInDiscipline", "eventsListResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "POST").execute()
                } catch (e: Exception) {
                }
            }
            else
            {
                toSend["Name"]=name

                try {
                    HTTPRequestAPI(this, "event/search", "eventsListResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "POST").execute()
                } catch (e: Exception) {
                }

            }
        }
        else if(eventID>-1)
        {
            toSend["DisciplineId"]=eventID.toString()
            toSend["Name"]=""
            try {
                HTTPRequestAPI(this, "event/searchInDiscipline", "eventsListResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "POST").execute()
            } catch (e: Exception) {
            }



        }
        else
        {
            //nothing
        }




    }
    fun eventsListResult(result:String)
    {
        val root= JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray: JSONArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val event: EventListItem = EventListItem(item.getString("name"), item.getString("description"), item.getString("date").replace('T',' '), sports.values()[item.getInt("disciplineId")-2].ico   )
                event.eventID = item.getInt("eventId")
                event.adminLogin=item.getString("adminLogin")

                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date: Date = df.parse(event.data)
                val currentTime = Calendar.getInstance().time

                if(!currentTime.after(date)) {
                    model.eventList.add(event)
                }
            }
        }
       fragment.setAdapter()
    }


}