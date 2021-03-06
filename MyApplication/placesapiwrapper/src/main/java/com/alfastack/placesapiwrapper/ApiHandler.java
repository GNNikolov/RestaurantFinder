package com.alfastack.placesapiwrapper;

import android.os.AsyncTask;

import androidx.annotation.WorkerThread;

import com.alfastack.placesapiwrapper.callbacks.ApiCallback;
import com.alfastack.placesapiwrapper.models.Restaurant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joro on 21/10/2019
 */
 class ApiHandler extends AsyncTask<String, Void, List<Restaurant>> {

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;
    private String status = null;
    private ApiCallback mCallback;

    ApiHandler(ApiCallback mCallback){
        this.mCallback = mCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mCallback.onPreExecute();
    }

    @Override
    protected List<Restaurant> doInBackground(String... urls) {
        final String stringURL = urls[0];
        String result = null;

        try {
            URL mURL = new URL(stringURL);
            HttpURLConnection mConnection = (HttpURLConnection) mURL.openConnection();
            mConnection.setRequestMethod(REQUEST_METHOD);
            mConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            mConnection.setReadTimeout(READ_TIMEOUT);
            mConnection.connect();

            StringBuilder stringBuilder = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(mConnection.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String inputLine = null;

            while ((inputLine = bufferReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            bufferReader.close();
            inputStreamReader.close();

            result = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result != null)
            return decodeJSON(result);
        else
            return null;
    }

    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {
        super.onPostExecute(restaurants);
        if (restaurants != null && status != null && status.equals("OK")){
            mCallback.onFetched(restaurants);
        }else {
            mCallback.onFetchFailed(status);
        }
    }

    @WorkerThread
    private List<Restaurant> decodeJSON(String input){
        List<Restaurant> data = new ArrayList<>();
        try {
            final JSONObject jsonObject = new JSONObject(input);
            status = jsonObject.getString("status");
            final JSONArray restaurants = jsonObject.getJSONArray("results");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            for (int i = 0 ; i < restaurants.length(); i++){
                if (restaurants.get(i) != null) {
                    Restaurant item = gson.fromJson(restaurants.get(i).toString(), Restaurant.class);
                    data.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}
