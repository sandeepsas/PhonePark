package com.uic.sandeep.phonepark;

import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep on 1/23/2016.
 */
/* Inner class to get response */
class SearchParkAsyncTask extends AsyncTask<Void, Void, List<LatLng>> {
    StringBuffer chaine = new StringBuffer("");
    //List<ParkingBlock> pb_list = new ArrayList<ParkingBlock>();
    List<LatLng> pt_list = new ArrayList<LatLng>();
    Location loc;
    SearchParkAsyncTask(Location location){
        this.loc = location;
    }


    protected void onPreExecute(Void aVoid) {
        MainActivity.text_navigation.setText("Connecting to server ...");
    }
    @Override
    protected List<LatLng> doInBackground(Void... voids) {

        try {
///*	http://73.247.220.84:8080/hello?UserID=a108eec35f0daf33&Latitude=41.8693826&Longitude=-87.6630133&TimeStamp=Current*/
            StringBuilder urlString = new StringBuilder();
            urlString.append(Constants.SYSTEM_IP+"/hello");

            urlString.append("?UserID=");
            urlString.append(MainActivity.userID);
            urlString.append("&Latitude=");
            urlString.append(loc.getLatitude());
            urlString.append("&Longitude=");
            urlString.append(loc.getLongitude());

            URL url = new URL(urlString.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("URL"+urlString.toString());

/*        connection.setRequestProperty("User-Agent", "");
        connection.setRequestMethod("POST");
        connection.setDoInput(true)*/;
        connection.connect();

        InputStream inputStream = connection.getInputStream();


        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while ((line = rd.readLine()) != null) {
            String[] line_split = line.split(",");
            for(int i=0;i<line_split.length-1;i=i+2){

                double lat1 =  Double.parseDouble(line_split[i]);
                double lon1 =  Double.parseDouble(line_split[i+1]);

                LatLng pt = new LatLng(lat1,lon1);
                pt_list.add(pt);
            }

            chaine.append(line);
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.pt_list;
    }

    @Override
    protected void onPostExecute(List<LatLng> blocks) {
        super.onPostExecute(blocks);
        MainActivity.showParkableMap(blocks);
        //Check if the user is within the limits
        CurrentLocationListener currentLocationListener = CurrentLocationListener.getInstance();
        currentLocationListener.setBlocks(blocks);

        LocationRequest currentLocationRequest = new LocationRequest();
        currentLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(1000);
        if (!MainActivity.isParked) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    MainActivity.mGoogleApiClient, currentLocationRequest, currentLocationListener);
        }
    }


}
class CurrentLocationListener implements LocationListener {
    public static final String LOG_TAG=CurrentLocationListener.class.getCanonicalName();

    List<LatLng> pBlocks = new ArrayList<LatLng>();
    int ctr = 0;
    static CurrentLocationListener ccLocationListener = null;

    private CurrentLocationListener(){}

    public void setBlocks(List<LatLng> blocks){
        pBlocks = blocks;
    }


    public static CurrentLocationListener getInstance(){

        if(ccLocationListener == null){
            ccLocationListener = new CurrentLocationListener();
            return ccLocationListener;
        }
        else
            return ccLocationListener;

    }

    @Override
    public void onLocationChanged(Location location) {

        if(!MainActivity.isParked) {

            //Check all the block mids
            boolean flag = false;
            float distance = 0;
            int nDist = 0;
            for(int i=0;i<pBlocks.size();i++){
                Location endLoc = new Location("");
                endLoc.setLongitude(pBlocks.get(i).longitude);
                endLoc.setLatitude(pBlocks.get(i).latitude);
                distance = endLoc.distanceTo(location);
                if(distance>100){
                    flag = flag||false;
                }else{
                    flag = flag||true;
                    nDist =(int)distance;
                }
            }
            MainActivity.text_parking_info.setText("Dist = "+nDist+"m - time = "+ctr+" s status = "+flag);
            ctr++;
            if(!flag){
                //Query
                new SearchParkAsyncTask(location).execute();
                LocationServices.FusedLocationApi.removeLocationUpdates(MainActivity.mGoogleApiClient, this);
            }

        }else{
            LocationServices.FusedLocationApi.removeLocationUpdates(MainActivity.mGoogleApiClient, this);
        }

    }

}

