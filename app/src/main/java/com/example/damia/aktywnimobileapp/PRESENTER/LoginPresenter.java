package com.example.damia.aktywnimobileapp.PRESENTER;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.damia.aktywnimobileapp.API.EnumChoice;
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi;
import com.example.damia.aktywnimobileapp.MODEL.LoginModel;
import com.google.gson.Gson;


public class LoginPresenter {

    Context context;
    LoginModel model;
    public LoginPresenter(Context context)
    {
        this.context=context;
        Gson gson = new Gson();
        String json =   sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.loginModel);
        model = gson.fromJson(json, LoginModel.class);
        if(model==null)
        {
            model= new LoginModel();
        }
    }

    public void SaveModel()
    {
        Gson gson = new Gson();
        String json = gson.toJson(model);
       sharedPreferenceApi.INSTANCE.set(context,json,EnumChoice.loginModel);
    }

    public void LoadModel()
    {
        Gson gson = new Gson();
        String json =   sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.loginModel);
        model = gson.fromJson(json, LoginModel.class);
    }

    public void login()
    {
        model.LoginAction();
    }
}
