/*
package com.uic.sandeep.phonepark;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

*/
/**
 * Created by Sandeep on 1/20/2016.
 *//*

public class PostStatus {


    private void sendData(ArrayList<NameValuePair> data)
    {
        // 1) Connect via HTTP. 2) Encode data. 3) Send data.
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new
                    HttpPost("http://www.blah.com/AddAccelerationData.php");
            httppost.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = httpclient.execute(httppost);
            Log.i("postData", response.getStatusLine().toString());
            //Could do something better with response.
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error:  "+e.toString());
        }
    }

    private void sendAccelerationData(String userIDArg, String dateArg, String timeArg,
                                      String timeStamp, String accelX, String accelY, String accelZ)
    {
        fileName = "AddAccelerationData.php";

        //Add data to be send.
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
        nameValuePairs.add(new BasicNameValuePair("userID", userIDArg));
        nameValuePairs.add(new BasicNameValuePair("date",dateArg));
        nameValuePairs.add(new BasicNameValuePair("time",timeArg));
        nameValuePairs.add(new BasicNameValuePair("timeStamp",timeStamp));

        nameValuePairs.add(new BasicNameValuePair("accelX",accelX));
        nameValuePairs.add(new BasicNameValuePair("accelY",accelY));
        nameValuePairs.add(new BasicNameValuePair("accelZ",accelZ));

        this.sendData(nameValuePairs);
    }
    public static class JSONParser {

        static InputStream is = null;
        static JSONObject jObj = null;
        static String json = "";

        // constructor
        public JSONParser() {
        }
        public String getJSONFromUrl(String url) {

            // Making HTTP request
            try {

                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestProperty("Content-length", "1000");
                connection.connect();
                InputStream is = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                json = sb.toString();
                is.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e) {
                System.out.println("Buffer Error -> Error converting result " + e.toString());
            }
            return json;

        }
}
*/
