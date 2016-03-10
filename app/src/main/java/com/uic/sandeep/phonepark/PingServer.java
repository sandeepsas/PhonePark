package com.uic.sandeep.phonepark;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sandeep Sasidharan on 2/3/2016.
 */
public class PingServer extends AsyncTask<Void, Void, Void> {


    PingServer(){

    }
    StringBuffer chaine = new StringBuffer("");

    protected void onPreExecute(Void aVoid) {

    }

    protected Void doInBackground(Void... voids) {

        try {

            StringBuilder urlString = new StringBuilder();
            //urlString.append("http://73.247.220.84:8080/post");
            urlString.append (Constants.SYSTEM_IP+"/ping");

            URL url = new URL(urlString.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            int status = connection.getResponseCode();

            System.out.println("status = " + status);

            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void aVoid) {
        MainActivity.text_navigation.setText(chaine);
    }

}