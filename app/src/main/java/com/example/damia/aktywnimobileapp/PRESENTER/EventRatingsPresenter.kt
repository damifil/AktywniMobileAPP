package com.example.damia.aktywnimobileapp.PRESENTER

import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.Comment
import com.example.damia.aktywnimobileapp.MODEL.EventRatingsModel
import com.example.damia.aktywnimobileapp.VIEW.EventRatingsFragment
import org.json.JSONObject
import java.util.HashMap

class EventRatingsPresenter(var fragment : EventRatingsFragment )
{
val model= EventRatingsModel()


    fun getListOfComments(eventID:String)
    {
        model.eventID=eventID
        val toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "userComment/eventWithUncomment/"+model.eventID, "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "GET").execute()
        } catch (e: Exception) {
        }

    }
    fun resultRequest(response:String)
    {
        val root= JSONObject(response)
        if(root.getBoolean("response"))
        {
           val array =  root.getJSONArray("info")
            for(i in 0..array.length()-1)
            {
                val item =array.getJSONObject(i)
                val comment= Comment()
                comment.Rate=item.getInt("rate")
                comment.describe=item.getString("describe")
                comment.eventID=model.eventID.toInt()
                comment.isAdded= !comment.describe.equals("null")
                comment.userIDRated = item.getInt("userIdRated")
                if(!comment.isAdded) {
                    comment.describe="Wszystko O.K."
                    comment.Rate=10
                }

                comment.login=item.getString("userLoginRated")
                model.comentList.add(comment)
            }
        }
        fragment.setAdapter()
    }

    fun setRatings()
    {
        for(coment in model.comentList)
        {
            val toSend = HashMap<String, String>()
            toSend["UserIdRated"]=coment.userIDRated.toString()
            toSend["EventId"]=coment.eventID.toString()
            toSend["Rate"]=coment.Rate.toString()
            toSend["Describe "]=coment.describe
           if(coment.isAdded){
               try {
                   HTTPRequestAPI(this, "userComment/update", "addComent", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "PUT").execute()
               } catch (e: Exception) {
               }
           }
            else
           {
               try {
                   HTTPRequestAPI(this, "userComment/add", "addComent", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "POST").execute()
               } catch (e: Exception) {
               }
           }
           coment.isAdded=true
        }
    }

}
