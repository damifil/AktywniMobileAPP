package com.example.damia.aktywnimobileapp.PRESENTER

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.User
import com.example.damia.aktywnimobileapp.MODEL.UserProfilModel
import com.example.damia.aktywnimobileapp.R
import com.example.damia.aktywnimobileapp.VIEW.AddFriendFragment
import com.example.damia.aktywnimobileapp.VIEW.LoginActivity
import com.example.damia.aktywnimobileapp.VIEW.UserProfileFragment
import kotlinx.android.synthetic.main.fragment_user_profile.*
import org.json.JSONObject
import java.util.HashMap
import android.support.v4.content.ContextCompat.startActivity
import com.example.damia.aktywnimobileapp.VIEW.EditAccountFragment


class UserProfilPresenter(val fragment: UserProfileFragment) {

    val model = UserProfilModel()

    fun downloadData() {
        val toSend = HashMap<String, String>()
        if (model.userID < 0) {
            try {
                HTTPRequestAPI(this, "user", "downloadResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "GET").execute()
            } catch (e: Exception) {
            }
        } else {
            fragment.button6.visibility=View.INVISIBLE
            try {
                HTTPRequestAPI(this, "user/profile/" + model.userID, "downloadResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "GET").execute()
            } catch (e: Exception) {
            }
        }
    }

    fun downloadFriend() {
        val toSend = HashMap<String, String>()

        try {
            HTTPRequestAPI(this, "friend", "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "GET").execute()
        } catch (e: Exception) {
        }
    }


    fun downloadResult(result: String) {
        val jsonObject = JSONObject(result)
        model.profilName = jsonObject.getString("login")
        model.userDescribe = jsonObject.getString("description")
        model.userRating = jsonObject.getString("rate").toDouble()
        setData()
        downloadFriend()
    }

    fun setData() {
        fragment.textView7.text = model.profilName
        fragment.textView8.text = "Ocena użytkownika: " + model.userRating.toString()
        fragment.textView4.text = "Opis:\n" + model.userDescribe
    }


    fun resultRequest(result: String) {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {

            val jsonArray = root.getJSONArray("info")
            for (i in 0..jsonArray.length() - 1) {
                val item = jsonArray.getJSONObject(i)
                val user = User()
                user.login = item.getString("login")
                user.userID = item.getInt("userId")
                user.userRating = item.getString("rate")
                if (item.getBoolean("isAccepted")) {
                    user.isAccepted = true
                }
                model.friendList.add(user)
            }
            model.friendList.sortBy { it.isAccepted }

        }
        model.isFriend = model.friendList.any { it.userID == model.userID }
        model.isAcceptedFriend = model.friendList.any { it.userID == model.userID && it.isAccepted }

        updateIco()

    }

    fun clickIco() {
        val toSend = HashMap<String, String>()
        if (model.profilName.equals(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.choiceLogin), true)) {

            val newFragment = EditAccountFragment.newInstance("","")
            val transaction = fragment.fragmentManager!!.beginTransaction()
            transaction.replace(R.id.body, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            if (model.isFriend) {
                try {
                    HTTPRequestAPI(this, "friend/" + model.userID, "deleteAddUserResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "DELETE").execute()
                } catch (e: Exception) {
                }
            } else {
                try {
                    HTTPRequestAPI(this, "friend/" + model.userID, "deleteAddUserResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "PUT").execute()
                } catch (e: Exception) {
                }
            }
        }
    }

    fun deleteAddUserResult(result: String) {
        val root = JSONObject(result)
        if (root.getString("response").equals("True")) {
            model.isFriend = !model.isFriend
            updateIco()
        } else {
            Toast.makeText(fragment.context, "wystąpił problem podczas wykonywania operacji", Toast.LENGTH_LONG).show()
        }
    }


    fun deleteAccount(result: String) {
        if (JSONObject(result).getBoolean("response")) {
            sharedPreferenceApi.set(fragment.context!!, "", EnumChoice.token)
            val intent = Intent(fragment.activity, LoginActivity::class.java)
            fragment.startActivity(intent)
        }
    }

    fun updateIco() {
        if (model.profilName.equals(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.choiceLogin), true)) {
            fragment.icoAddDelete.text = "\uf4ff"
            val toSend = HashMap<String, String>()
            fragment.button3.setOnClickListener {
                try {
                    HTTPRequestAPI(this, "account/delete", "deleteAccount", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "DELETE").execute()
                } catch (e: Exception) {
                }
            }
        } else {
            fragment.button3.visibility = View.INVISIBLE
            if (model.isFriend) {
                fragment.icoAddDelete.text = "\uf503"
            } else {
                if (model.isAcceptedFriend)
                    fragment.icoAddDelete.text = "\uf234"
                else {
                    fragment.icoAddDelete.visibility = View.INVISIBLE
                }
            }
        }
    }


}
