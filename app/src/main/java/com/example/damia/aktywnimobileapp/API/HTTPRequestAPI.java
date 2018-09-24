package com.example.damia.aktywnimobileapp.API;



import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;


public class HTTPRequestAPI extends
        AsyncTask<Void, Void, String> {

    String urlString;
    HashMap DataToSend;
    String methodName;
    Object presenter;
    String token;
    String requestType;

    public HTTPRequestAPI(Object presenter,String url,String methodName,HashMap DataToSend,String token, String requestType) {

        this.urlString="http://35.242.196.239/api/"+url.replaceAll(" ", "%20");
        this.DataToSend=DataToSend;
        this.methodName=methodName;
        this.presenter=presenter;
        this.token=token;
        this.requestType=requestType;
    }


    public HTTPRequestAPI(Object presenter,String url,String methodName,HashMap DataToSend,String token) {
        this.urlString="http://35.242.196.239/api/"+url.replaceAll(" ", "%20");
        this.DataToSend=DataToSend;
        this.methodName=methodName;
        this.presenter=presenter;
        this.token=token;
        this.requestType="POST";
    }

    @Override
    protected String doInBackground(Void... params) {
        String response = "";
        try {
            response = performPostCall(urlString, DataToSend);
        } catch (Exception e) {
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {

        Log.i("AAAAresp","resp"+response);
        if(!methodName.equals(""))
        {java.lang.reflect.Method method;
        try {
            method = presenter.getClass().getMethod(methodName, String.class);
            method.invoke(presenter, response);
        }catch(Exception ex){
        }
        }
    }


    public String performPostCall(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");////
            conn.setRequestMethod(requestType);
            conn.setRequestProperty("Content-Type","application/json;charset=utf-8");
            conn.setRequestProperty("Accept","application/json");
            if(token!="")
            {
                conn.setRequestProperty("Authorization", "Bearer "+token);
            }
            conn.setDoInput(true);


            Log.i("requestAAAA",url.toURI().toString());
            Log.i("requestAAAA",token);
            Log.i("requestAAAA",requestType);
            Log.i("requestAAAA",getPostDataString(postDataParams));



            if(!requestType.equals("GET"))
            {
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
                os.close();
            }


            int responseCode = conn.getResponseCode();
            Log.i("AAAAs",Integer.toString(responseCode));
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        String result=new JSONObject(params).toString();
        return result.toString();
    }
}