package com.example.damia.aktywnimobileapp.PRESENTER;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.damia.aktywnimobileapp.API.CyptographyApi;
import com.example.damia.aktywnimobileapp.API.EnumChoice;
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI;
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi;
import com.example.damia.aktywnimobileapp.MODEL.LoginModel;
import com.google.gson.Gson;

import java.util.HashMap;


public class LoginPresenter {

    Context context;
   public LoginModel model;
    public LoginPresenter(Context context)
    {
        this.context=context;
        LoadModel();
        if(model==null)
        {
            model= new LoginModel();
        }



    }

    public void SaveModel()
    {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        try {
            sharedPreferenceApi.INSTANCE.set(context, CyptographyApi.encrypt(json), EnumChoice.loginModel);
        }catch (Exception ex){}

    }

    public void LoadModel()
    {
        Gson gson = new Gson();
        String json="";
        try {
         json = CyptographyApi.decrypt(sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.loginModel));
        }catch (Exception ex){}
        model = gson.fromJson(json, LoginModel.class);
    }

    public void login()
    {
        Log.i("HHHHHH",model.login);
        HashMap toSend = new HashMap();
        toSend.put("UserLogin", model.login);
        toSend.put("password", model.password);
        try {
            new HTTPRequestAPI(this, "http://localhost:5000/account/login", "loginResult", toSend).execute();
        }catch (Exception e){}
    }

    public void loginResult(String result)
    {


    }


}
