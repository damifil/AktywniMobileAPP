package com.example.damia.aktywnimobileapp.PRESENTER

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.CurentEventModel
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.R
import com.example.damia.aktywnimobileapp.VIEW.CurentEventFragment
import com.example.damia.aktywnimobileapp.VIEW.EventAddkFragment
import com.example.damia.aktywnimobileapp.VIEW.MainFragment
import kotlinx.android.synthetic.main.fragment_curent_event.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class CurentEventPresenter(context: CurentEventFragment, eventName: String, adminLogin:String) {
    var model: CurentEventModel
    val context2: CurentEventFragment

    init {
        this.context2 = context
        this.model = CurentEventModel()
        model.adminLogin=adminLogin

        val toSend = HashMap<String, String>()
        toSend["Name"]=eventName
        try {
            HTTPRequestAPI(this, "event/name/", "initResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "POST").execute()
        } catch (e: Exception) {
        }
    }

    fun initResult(result: String) {
        var root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jobject = JSONObject(root.getString("info"))
            model.describe.set("Opis:\n"+jobject.getString("description"))
            model.date.set("Data wydarzenia:\n"+jobject.getString("date").replace('T', ' '))
            model.name.set("Wydarzenie:\n"+jobject.getString("name"))
            model.discipline = jobject.getInt("disciplineId")
            model.eventID = jobject.getInt("eventId")
            model.latitude=jobject.getDouble("latitude")
            model.longitude=jobject.getDouble("longitude")
            val toSend = HashMap<String, String>()
            try {
                HTTPRequestAPI(this, "userEvent/" + model.eventID, "usersList", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "GET").execute()
            } catch (e: Exception) {
            }
        } else {
            Toast.makeText(context2.context, "Wystapil problem podczas pobierania wydarzenia", Toast.LENGTH_LONG).show()
        }
    }


    fun usersList(result: String) {
        var root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            var userArray = root.getJSONArray("info")
            for (i in 0..(userArray.length() - 1)) {
                val user = userArray.getJSONObject(i)
                val userObj: User = User()
                userObj.eventID = model.eventID
                userObj.isAccepted = user.getBoolean("isAccepted")
                userObj.login = user.getString("login")
                model.userList.add(userObj)
                if (userObj.login.equals(sharedPreferenceApi.getString(context2.context!!, EnumChoice.choiceLogin),true)) {
                    if(userObj.isAccepted)
                    {
                        if(userObj.login.equals(model.adminLogin))
                            model.userStatus=2
                        else
                            model.userStatus=1
                    }
                    else
                    {
                        model.userStatus=0
                        context2.BTChat.visibility= View.INVISIBLE
                        context2.BTUsers.visibility=View.INVISIBLE
                    }

                }
            }
            if (model.userStatus!=0)
            {
                context2.BTChat.visibility= View.VISIBLE
                context2.BTUsers.visibility=View.VISIBLE
            }
            context2.setIco(model.userStatus)
        } else {
            Toast.makeText(context2.context, "Wystapil problem podczas pobierania uzytkownikow wydarzenia", Toast.LENGTH_LONG).show()
        }
    }

    fun joinEvent()
    {
        val toSend = HashMap<String, String>()
        toSend["EventId"]=model.eventID.toString()
        try {
            HTTPRequestAPI(this, "userEvent/join", "joinResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "POST").execute()
        } catch (e: Exception) {
        }
    }

    fun joinResult(result: String)
    {
        val root=JSONObject(result)
        if (root.getString("response").equals("True"))
        {
            context2.BTChat.visibility= View.VISIBLE
            context2.BTUsers.visibility=View.VISIBLE
            model.userStatus=1
            context2.setIco(model.userStatus)
        }
        else
        {
        Toast.makeText(context2.context,root.getString("error"),Toast.LENGTH_LONG).show()

        }
    }

    fun exceptEvent()
    {
        val toSend = HashMap<String, String>()
        toSend["EventId"]=model.eventID.toString()
        try {
            HTTPRequestAPI(this, "userEvent/except", "exceptResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "POST").execute()
        } catch (e: Exception) {
        }
    }
    fun exceptResult(result: String)
    {
        val root=JSONObject(result)
        if (root.getString("response").equals("True"))
        {
            Toast.makeText(context2.context,"Opuściłeś wydarzenie",Toast.LENGTH_LONG).show()
            val newFragment = MainFragment.newInstance(true)
            val transaction = context2.fragmentManager!!.beginTransaction()
            transaction.replace(R.id.body, newFragment)
            transaction.commit()
        }
        else
        {
            Toast.makeText(context2.context,root.getString("error"),Toast.LENGTH_LONG).show()
        }
    }

    fun deleteEvent()
    {
        val toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "event/remove/"+model.eventID, "deleteEventResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)), "DELETE").execute()
        } catch (e: Exception) {
        }

    }

    fun deleteEventResult(result:String)
    {
        val root=JSONObject(result)
        if (root.getString("response").equals("True"))
        {
            Toast.makeText(context2.context,"Usunąłeś wydarzenie",Toast.LENGTH_LONG).show()
            val newFragment = MainFragment.newInstance(true)
            val transaction = context2.fragmentManager!!.beginTransaction()
            transaction.replace(R.id.body, newFragment)
            transaction.commit()
        }
        else
        {
            Toast.makeText(context2.context,root.getString("error"),Toast.LENGTH_LONG).show()
        }
    }

    fun changeEvent()
    {
        val newFragment = EventAddkFragment.newInstance(model.latitude,model.longitude,model.eventID)
        val transaction = context2.fragmentManager!!.beginTransaction()
        transaction.replace(R.id.body, newFragment)
        transaction.commit()
    }
}