package com.example.damia.aktywnimobileapp.PRESENTER;

import com.example.damia.aktywnimobileapp.API.CyptographyApi;
import com.example.damia.aktywnimobileapp.API.EnumChoice;
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI;
import com.example.damia.aktywnimobileapp.API.Valdiation;
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi;
import com.example.damia.aktywnimobileapp.RegisterModel;
import com.example.damia.aktywnimobileapp.VIEW.RegisterActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

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
     context.ShowTooltipPassword();
    }
    if(!Valdiation.isEmailValid(model.email))
    {
        context.ShowTooltipEmail();
    }
    if(Valdiation.isEmailValid(model.email) && Valdiation.isCorrectPassword(model.password))
    {
        HashMap toSend = new HashMap();
        toSend.put("Login", model.login);
        toSend.put("Email", model.email);
        toSend.put("Password", model.password);

        try {
            new HTTPRequestAPI(this, "account/register", "RegisterResult", toSend).execute();
        }catch (Exception e){}
    }
    }
    public void RegisterResult(String result) {
        try {
            JSONObject object = new JSONObject(result);
            if (object.getString("response").equals("True")) {
                try {
                    model.ResetData();
                    sharedPreferenceApi.INSTANCE.set(context, "", EnumChoice.registerModel);
                } catch (Exception ex) {
                }
                context.GoTologin();
            } else {
                context.ShowTooltipLogin();
            }
        } catch (Exception e) {
        }

    }
}
