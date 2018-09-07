package com.example.damia.aktywnimobileapp.PRESENTER

import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.FriendsModel
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.R
import com.example.damia.aktywnimobileapp.VIEW.CurentEventFragment
import com.example.damia.aktywnimobileapp.VIEW.FreindsFragment
import org.json.JSONObject
import java.util.HashMap

class FriendsPresenter(activity:FreindsFragment)
{
    var activity=activity
    var model:FriendsModel= FriendsModel()
    fun init()
    {
        /*val us=User()
        us.login="login 1"
        us.userID=10
        val us2=User()
        us2.login="login 2"
        us2.userID=12
        model.friendsList.add(us)
        model.friendsList.add(us2)*/

        val toSend = HashMap<String, String>()



        try {
            HTTPRequestAPI(this, "friend", "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(activity.context!!, EnumChoice.token)),"GET").execute()
        } catch (e: Exception) {
        }
       // activity.setAdapter()
    }


    fun resultRequest(result:String)
    {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {

            val jsonArray=root.getJSONArray("info")
            for(i in 0..jsonArray.length()-1)
            {
                val item = jsonArray.getJSONObject(i)
                if(item.getBoolean("isAccepted"))
                {
                    val user=User()
                    user.login=item.getString("login")
                    user.userID=item.getInt("userid")
                    user.userRating=item.getString("rate")
                    model.friendsList.add(user)
                }
            }
            activity.setAdapter()

        } else {
        Toast.makeText(activity.context,"Wystapil problem podczas pobierania lsity przyjaciol",Toast.LENGTH_LONG).show()
        }
    }


}