package com.example.damia.aktywnimobileapp.PRESENTER

import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.EventUsersModel
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.VIEW.EventUsersFragment
import org.json.JSONObject
import java.util.HashMap

class EventUsersPresenter(fragment:EventUsersFragment,eventID:Int, adminLogi:String)
{
    var model=EventUsersModel()
    var fragment:EventUsersFragment?=null
    init{
        model.eventID=eventID
        model.adminLogin=adminLogi
        this.fragment=fragment
        model.userLogin=sharedPreferenceApi.getString(fragment.context!!,EnumChoice.choiceLogin)
        model.userIsAdmin=model.adminLogin.equals(model.userLogin,true)
        val toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "userEvent/"+model.eventID, "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "GET").execute()
        } catch (e: Exception) {
        }
    }

    fun resultRequest(result:String)
    {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {

            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val user = User()
                user.login = item.getString("login")
                user.userID = item.getInt("userId")
                user.isFriend=true
                if (item.getBoolean("isAccepted")) {
                    user.isAccepted = true
                }

                    user.isAdminEvent=model.adminLogin.equals(user.login)


                model.friendsList.add(user)
            }
            model.friendsList.sortByDescending { it.isAccepted }


            fragment!!.setAdapter()


        } else {
            Toast.makeText(fragment!!.context, "Wystapil problem podczas pobierania lsity przyjaciol", Toast.LENGTH_LONG).show()
        }
    }

}