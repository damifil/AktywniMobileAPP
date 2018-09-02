package com.example.damia.aktywnimobileapp.PRESENTER

import android.util.Log
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.CurentEventModel
import com.example.damia.aktywnimobileapp.VIEW.CurentEventFragment
import org.json.JSONObject
import java.util.HashMap

class CurentEventPresenter(context:CurentEventFragment, eventName:String)
{
    var model: CurentEventModel
    val context2: CurentEventFragment

    init {
        this.context2 = context
        this.model = CurentEventModel()
        val toSend = HashMap<String,String>()
        try {
            HTTPRequestAPI(this, "event/name/"+eventName, "initResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(context2.context!!, EnumChoice.token)),"GET").execute()
        } catch (e: Exception) {
        }
    }

   fun initResult(result:String)
   {
       var root=JSONObject(result)
       if(root.getString("response").equals("True"))
       {
           val jobject=JSONObject(root.getString("info"))
           model.describe.set(jobject.getString("description"))
           model.date.set(jobject.getString("date").replace('T',' '))
           model.name.set(jobject.getString("name"))
           model.discipline=jobject.getInt("disciplineId")
       }
       else
       {
        Toast.makeText(context2.context,"Wystapil problem podczas pobierania wydarzenia",Toast.LENGTH_LONG).show()
       }
   }
}