package com.example.damia.aktywnimobileapp.PRESENTER;

import android.content.Context;

import com.example.damia.aktywnimobileapp.API.CyptographyApi;
import com.example.damia.aktywnimobileapp.API.EnumChoice;
import com.example.damia.aktywnimobileapp.API.Valdiation;
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi;
import com.example.damia.aktywnimobileapp.MODEL.LoginModel;
import com.example.damia.aktywnimobileapp.MODEL.RegisterModel;
import com.example.damia.aktywnimobileapp.VIEW.RegisterActivity;
import com.google.gson.Gson;

public class RegisterPresenter {

    RegisterActivity context;
    public RegisterModel model;

    public RegisterPresenter(RegisterActivity context)
    {
        this.context=context;
        LoadModel();
        if(model==null)
        {
            model= new RegisterModel();
        }
    }

    public void SaveModel()
    {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        try {
            sharedPreferenceApi.INSTANCE.set(context, CyptographyApi.encrypt(json), EnumChoice.registerModel);
        }catch (Exception ex){}

    }

    public void LoadModel()
    {
        Gson gson = new Gson();
        String json="";
        try {
            json = CyptographyApi.decrypt(sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.registerModel));
        }catch (Exception ex){}
        model = gson.fromJson(json, RegisterModel.class);
    }

    public void RegisterAction()
    {
    if(!Valdiation.isCorrectPassword(model.password))
    {
     context.showTooltipPassword();
    }
    if(!Valdiation.isEmailValid(model.email))
    {
        context.showTooltipEmail();
    }
    if(Valdiation.isEmailValid(model.email) && Valdiation.isCorrectPassword(model.password))
    {
        ////// tutaj akcja z wysłaniem i
    }

    }


    public void registerResulty(String result)
    {

    }

}
