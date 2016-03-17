package com.uic.sandeep.phonepark;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

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
class DisplayRouteAsyncTask extends AsyncTask<Void, Void, List<LatLng>> {

    public static final String LOG_TAG=DisplayRouteAsyncTask.class.getCanonicalName();

    StringBuffer chaine = new StringBuffer("");
    //List<ParkingBlock> pb_list = new ArrayList<ParkingBlock>();
    List<LatLng> pt_list = new ArrayList<LatLng>();
    Location currentLocation;
    Location loc;
    DisplayRouteAsyncTask(Location location){
        this.loc = location;
    }


    protected void onPreExecute(Void aVoid) {
        MainActivity.text_navigation.setText("Connecting to server ...");
    }
    @Override
    protected List<LatLng> doInBackground(Void... voids) {

        HttpURLConnection connection = null;

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
            connection = (HttpURLConnection) url.openConnection();
            System.out.println("URL"+urlString.toString());

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
        }finally {
            connection.disconnect();
        }
        return this.pt_list;
    }

    @Override
    protected void onPostExecute(List<LatLng> blocks) {
        super.onPostExecute(blocks);



        //CurrentLocationListener currentLocationListener = new CurrentLocationListener(blocks);

        List<LatLng> pblocks_5 = new ArrayList<LatLng>();
        int no_park_blocks = blocks.size();

        if(no_park_blocks<=6){
            MainActivity.showParkableMap(blocks);
            Log.e(LOG_TAG,"Display Activity 1 "+blocks.toString());
            return;
        }else{
            MainActivity.showParkableMap(blocks.subList(0, 6));
            /*List<LatLng> picked = new ArrayList<LatLng>(blocks.subList(0,4));
            blocks.removeAll(picked);*/
            blocks = new ArrayList<LatLng>(blocks.subList(4,blocks.size()));

            Log.e(LOG_TAG,"Display Activity 2 "+blocks.toString());

        /*    if(blocks.size()>0) {

                CurrentLocationListener currentLocationListener = CurrentLocationListener.getInstance();
                currentLocationListener.setBlocks(blocks);

                LocationRequest currentLocationRequest = new LocationRequest();
                currentLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(1000);
                if (!MainActivity.isParked) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(
                            MainActivity.mGoogleApiClient, currentLocationRequest, currentLocationListener);
                }
            }*/
        }

    }



}

/*
 class CurrentLocationListener implements LocationListener {
     public static final String LOG_TAG=CurrentLocationListener.class.getCanonicalName();

    List<LatLng> pBlocks = new ArrayList<LatLng>();
    int ctr = 0;
    static CurrentLocationListener ccLocationListener = null;

    private CurrentLocationListener(){}

    public void setBlocks(List<LatLng> blocks){
        pBlocks = blocks;
    }
       */
/*private CurrentLocationListener(List<LatLng> blocks){
            this.pBlocks = blocks;

        }*//*


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
            Location endLoc = new Location("");
            endLoc.setLongitude(pBlocks.get(0).longitude);
            endLoc.setLatitude(pBlocks.get(0).latitude);


            float distance = endLoc.distanceTo(location);
            // System.out.println("\n \n \n \n \n \n \n \n \n" + distance + "\n \n \n \n \n \n \n \n \n");
            MainActivity.text_parking_info.setText("Dist = "+(int)distance+"m - time = "+ctr+" s");
            ctr++;
            if (distance < 50) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()) )// Sets the center of the map to Mountain View
                        .bearing(location.getBearing()).zoom(17).build();
                MainActivity.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



                if (pBlocks.size() <= 6) {
                    MainActivity.showParkableMap(pBlocks.subList(0, pBlocks.size()));
                    Log.e(LOG_TAG,"Display Activity 3 "+ pBlocks.toString());
                    LocationServices.FusedLocationApi.removeLocationUpdates(MainActivity.mGoogleApiClient, this);
                    //Announce to restart Search
                    MainActivity.mSpeech.speak("Displaying Last set of parking blocks within your reach. Please restart your search", TextToSpeech.QUEUE_ADD, null);
                } else {
                    MainActivity.showParkableMap(pBlocks.subList(0, 6));
                    */
/*List<LatLng> picked = new ArrayList<LatLng>(pBlocks.subList(0,4));
                    pBlocks.removeAll(picked);*//*

                    Log.e(LOG_TAG, "Display Activity 4 "+ pBlocks.toString());
                    pBlocks = new ArrayList<LatLng>(pBlocks.subList(4,pBlocks.size()));

                    Log.e(LOG_TAG, "Display Activity 5 "+ pBlocks.toString());
                }

            }
        }else{
            Log.e(LOG_TAG,"Display Activity 6 "+ pBlocks.toString());
            LocationServices.FusedLocationApi.removeLocationUpdates(MainActivity.mGoogleApiClient, this);
        }

    }

}*/
