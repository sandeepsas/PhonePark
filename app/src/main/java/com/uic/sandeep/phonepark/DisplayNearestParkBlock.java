package com.uic.sandeep.phonepark;

/**
 * Created by Sandeep on 3/15/2016.
 */

import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.uic.sandeep.phonepark.blocksmap.ParkingBlock;

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
class DisplayNearestParkBlock extends AsyncTask<Void, Void, List<ParkingBlock>> {
    StringBuffer chaine = new StringBuffer("");

    List<ParkingBlock> nearestParkingBlocks =  new ArrayList<ParkingBlock>();
    //List<ParkingBlock> pb_list = new ArrayList<ParkingBlock>();
    List<LatLng> pt_list = new ArrayList<LatLng>();
    Location loc;
    DisplayNearestParkBlock(Location location){
        this.loc = location;
    }


    protected void onPreExecute(Void aVoid) {
        MainActivity.text_navigation.setText("Connecting to server ...");
    }
    @Override
    protected List<ParkingBlock> doInBackground(Void... voids) {

        try {
///*	http://73.247.220.84:8080/hello?UserID=a108eec35f0daf33&Latitude=41.8693826&Longitude=-87.6630133&TimeStamp=Current*/
            StringBuilder urlString = new StringBuilder();
            urlString.append(Constants.SYSTEM_IP+"/nn");

            urlString.append("?UserID=");
            urlString.append(MainActivity.userID);
            urlString.append("&Latitude=");
            urlString.append(loc.getLatitude());
            urlString.append("&Longitude=");
            urlString.append(loc.getLongitude());

            URL url = new URL(urlString.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("URL"+urlString.toString());

            connection.connect();

            InputStream inputStream = connection.getInputStream();


            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                String[] line_split = line.split(",");
                for(int i=0;i<line_split.length-1;i=i+6){

                    double lat1 =  Double.parseDouble(line_split[i]);
                    double lon1 =  Double.parseDouble(line_split[i+1]);

                    double lat2 =  Double.parseDouble(line_split[i+2]);
                    double lon2 =  Double.parseDouble(line_split[i+3]);

                    double probability =  Double.parseDouble(line_split[i+4]);

                    String streetID =  line_split[i+5];

                    ParkingBlock near_block = new ParkingBlock(streetID,
                            new LatLng(0.0,0.0),
                            new LatLng(lat1,lon1),
                            new LatLng(lat2,lon2),
                            probability);
                    nearestParkingBlocks.add(near_block);
                }

                chaine.append(line);
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.nearestParkingBlocks;
    }

    @Override
    protected void onPostExecute(List<ParkingBlock> blocks) {
        super.onPostExecute(blocks);
        MainActivity.showNearestAvailabilityMap(blocks);
        new SearchParkAsyncTask(loc).execute();
    }


}


