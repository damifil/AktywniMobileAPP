package com.example.damia.aktywnimobileapp.API

import android.content.Context
import android.content.SharedPreferences
import com.example.damia.aktywnimobileapp.R

/**
 * Created by Damian on 28.11.2017.
 */

enum class EnumChoice(val value:String)
{
    // do przejrzenia co potrzebne
    ip("ipserwer"),password("password"),n("ttt"),
    login("login"),nameuser("name"),surname("surname"),
    isLogin("isLogin"),isAdmin("role"),choiceLogin("choiceLogin"),choiceLock("choiceLock"),
    publicKey("publicKey"),loginModel("loginModel"),friendList("friendList"),  registerModel("registerModel"),token("token"),expireData("expires"),EventAddPresenter("eventAddP");
}


object sharedPreferenceApi {
    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null



    fun getBoolean(context : Context, choise:EnumChoice): Boolean
    {
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        return sharedPref!!.getBoolean(choise.value, false)
    }

    fun getString(context : Context, choise: EnumChoice): String
    {
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        return sharedPref!!.getString(choise.value, "")
    }

    fun set(context : Context,  hashMapvalue: HashMap<EnumChoice,String>) {

        if(hashMapvalue==null){return}
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        for ((key, value) in hashMapvalue) {
            editor!!.putString(key.value, value)
        }
        editor!!.commit()
    }

    fun set(context : Context, value: Boolean, choise: EnumChoice)
    {
        if(value==null){return}
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        editor!!.putBoolean(choise.value, value)
        editor!!.commit()
    }

    fun set(context : Context, value: String, choise: EnumChoice)
    {
        if(value==null){return}
        sharedPref = context.getSharedPreferences(context.getString(R.string.SPName), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        editor!!.putString(choise.value, value)
        editor!!.apply()
    }

}