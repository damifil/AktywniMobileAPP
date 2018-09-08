package com.example.damia.aktywnimobileapp.PRESENTER

import android.util.Log
import android.widget.Toast
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.AddFriendModel
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.VIEW.AddFriendFragment
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap
import kotlin.concurrent.fixedRateTimer

class AddFriendPresenter(fragment: AddFriendFragment) {
    var model = AddFriendModel()
    val fragment = fragment
    fun init() {

        var arr= JSONArray(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.friendList))
        for(i in 0..arr.length()-1)
        {
            val item=arr.getJSONObject(i)
            val user=User()
            user.login=item.getString("login")
            user.userID=item.getInt("userID")
          model.friendList.add(user)
        }

    }

    fun search(login: String) {
        if (login.length > 2) {
            model.userList.clear()
            val toSend = HashMap<String, String>()
            toSend["FragmentLogin"] = login
            try {
                HTTPRequestAPI(this, "user/all", "searchResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "POST").execute()
            } catch (e: Exception) {
            }
        } else {

        }
    }

    fun searchResult(result: String) {
        Log.i("AAAA", "w result")
        Log.i("AAAA", result)
        var root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val user = User()
                user.login = item.getString("login")
                user.userID = item.getInt("userId")
                if (model.friendList.any { it.userID == user.userID }) {
                    user.isFriend = true
                }
                else
                {
                    model.userList.add(user)
                }
            }
            fragment.setAdapter()
        } else {
            Toast.makeText(fragment.context, "wystapil problem z serwerem", Toast.LENGTH_LONG).show()
        }
    }


}