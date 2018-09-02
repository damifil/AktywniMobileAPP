package com.example.damia.aktywnimobileapp.PRESENTER;
import com.example.damia.aktywnimobileapp.API.CyptographyApi;
import com.example.damia.aktywnimobileapp.API.EnumChoice;
import com.example.damia.aktywnimobileapp.API.HTTPRequestAPI;
import com.example.damia.aktywnimobileapp.API.sharedPreferenceApi;
import com.example.damia.aktywnimobileapp.LoginModel;
import com.example.damia.aktywnimobileapp.VIEW.LoginActivity;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.util.HashMap;


public class LoginPresenter {

    LoginActivity context;
   public LoginModel model;
    public LoginPresenter(LoginActivity context)
    {
        this.context=context;
        LoadModel();
        if(model==null)
        {
            model= new LoginModel();
        }
 try {
     if (!CyptographyApi.decrypt(sharedPreferenceApi.INSTANCE.getString(context, EnumChoice.token)).equals("")) {

         context.goToHomeView();
     }
 }catch (Exception e){}


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
        HashMap toSend = new HashMap();
        toSend.put("UserLogin", model.login);
        toSend.put("password", model.password);
        try {
            new HTTPRequestAPI(this, "account/login", "loginResult", toSend,"").execute();
        }catch (Exception e){}
    }

    public void loginResult(String result)
    {

        try {

            JSONObject object = new JSONObject(result);
            JSONObject info= new JSONObject(object.getString("info"));
            sharedPreferenceApi.INSTANCE.set(context, CyptographyApi.encrypt(info.getString("token")), EnumChoice.token);
            sharedPreferenceApi.INSTANCE.set(context, info.getString("expires"), EnumChoice.expireData);
            sharedPreferenceApi.INSTANCE.set(context, info.getString("role"), EnumChoice.isAdmin);


        if(object.getString("response").equals("True"))
        {
            try {
                sharedPreferenceApi.INSTANCE.set(context, "", EnumChoice.loginModel);
            }catch (Exception ex){}
            model.ResetData();
            context.goToHomeView();
        }
        else
        {
            context.showTooltip();
        }
        }catch (Exception e){}
    }


}
