package com.example.damia.aktywnimobileapp.PRESENTER

import com.example.damia.aktywnimobileapp.API.CyptographyApi
import com.example.damia.aktywnimobileapp.API.EnumChoice
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi
import com.example.damia.aktywnimobileapp.MODEL.EventListItem
import com.example.damia.aktywnimobileapp.MODEL.HistoryModel
import com.example.damia.aktywnimobileapp.VIEW.HistoryFragment
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap
import kotlin.concurrent.fixedRateTimer

class HistoryPresenter(fragment: HistoryFragment) {
    val fragment: HistoryFragment
    val model= HistoryModel()
    init {
        this.fragment = fragment
        val toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "userEvent/myHistory", "resultRequest", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "GET").execute()
        } catch (e: Exception) {
        }
    }

    fun resultRequest(response: String) {
        val root = JSONObject(response)
        if (root.getBoolean("response")) {
            val jsonArray: JSONArray = root.getJSONArray("info")
            if (jsonArray.length() > 0)
                for (i in 0..jsonArray.length() - 1) {
                    val arrayItem = jsonArray.getJSONObject(i)

                var item= EventListItem(arrayItem.getString("eventName"),
                        "",arrayItem.getString("date").replace("T"," "),"")
                    item.isEvent=false
                    item.latitude=arrayItem.getDouble("latitude")
                    item.longitude=arrayItem.getDouble("longitude")
                    item.description=arrayItem.getString("description")
                    item.discipline=arrayItem.getInt("disciplineID")
                    model.historyList.add(item)
                }
        }
        fragment.setAdapter()
    }

}