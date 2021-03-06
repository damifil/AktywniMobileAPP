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

    public HTTPRequestAPI(Object presenter,String url,String methodName,HashMap DataToSend) {

        this.urlString="http://192.168.137.1:5000/"+url;
        this.DataToSend=DataToSend;
        this.methodName=methodName;
        this.presenter=presenter;
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

        java.lang.reflect.Method method;
        try {
            method = presenter.getClass().getMethod(methodName, String.class);
            method.invoke(presenter, response);
        }catch(Exception ex){
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
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json;charset=utf-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
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