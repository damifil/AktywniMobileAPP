package com.example.damia.aktywnimobileapp.PRESENTER

import android.widget.Toast
import com.example.damia.aktywnimobileapp.API.*
import com.example.damia.aktywnimobileapp.MODEL.EditAccountModel
import com.example.damia.aktywnimobileapp.VIEW.EditAccountFragment
import kotlinx.android.synthetic.main.fragment_edit_account.*
import org.json.JSONObject

class EditAccountPresenter(fragment: EditAccountFragment) {
    val model = EditAccountModel()
    val fragment = fragment

    init {
        val toSend = HashMap<String, String>()
        try {
            HTTPRequestAPI(this, "user", "downloadResult", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "GET").execute()
        } catch (e: Exception) {
        }
    }

    fun downloadResult(response: String) {
        val jsonObject = JSONObject(response)

        model.description = jsonObject.getString("describe")
        model.email = jsonObject.getString("email")
        model.login = jsonObject.getString("login")
        model.role = jsonObject.getString("role")
        fragment.updateData()
    }

    fun changedata() {
        if (!model.description.equals(fragment.ETDescribe.text.toString())) {
            val toSend = HashMap<String, String>()
            toSend["Description"] = fragment.ETDescribe.text.toString()
            try {
                HTTPRequestAPI(this, "account/changeDescription", "result", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "PUT").execute()
            } catch (e: Exception) {
            }
        }
        if (!model.email.equals(fragment.ETEmail.text.toString())) {
            if (Valdiation.isEmailValid(fragment.ETEmail.text.toString())) {
                val toSend = HashMap<String, String>()
                toSend["Email"] = fragment.ETEmail.text.toString()
                try {
                    HTTPRequestAPI(this, "account/changeEmail", "result", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "PUT").execute()
                } catch (e: Exception) {
                }
            } else {
                Toast.makeText(fragment.context!!, "niepoprawny format adresu email", Toast.LENGTH_SHORT).show()
            }
        }
        if (!fragment.ETPassword.text.toString().equals("")) {
            var a = fragment.ETPasswordNew.text.toString()
            if (Valdiation.isCorrectPassword(fragment.ETPasswordNew.text.toString())) {
                val toSend = HashMap<String, String>()
                toSend["NewPassword"] = fragment.ETPasswordNew.text.toString()
                toSend["CurrentPassword"] = fragment.ETPassword.text.toString()
                try {
                    HTTPRequestAPI(this, "account/changePassword", "resultpass", toSend, CyptographyApi.decrypt(sharedPreferenceApi.getString(fragment.context!!, EnumChoice.token)), "PUT").execute()
                } catch (e: Exception) {
                }
                fragment.ETPassword.setText("")
            } else {
                Toast.makeText(fragment.context!!, "niepoprawny format hasła", Toast.LENGTH_SHORT).show()

            }
        } else {
            if (!fragment.ETPasswordNew.text.toString().equals(""))
                Toast.makeText(fragment.context, "wprowadz stare hasło", Toast.LENGTH_SHORT).show()
        }

    }


    fun result(result: String) {
        val jobj = JSONObject(result)
        if (!jobj.getBoolean("response")) {
            Toast.makeText(fragment.context, "wystpil problem podczas edycji danych", Toast.LENGTH_SHORT).show()
        }
    }

    fun resultpass(result: String) {
        val jobj = JSONObject(result)
        if (!jobj.getBoolean("response")) {
            Toast.makeText(fragment.context, jobj.getString("error"), Toast.LENGTH_SHORT).show()
        }
    }

}