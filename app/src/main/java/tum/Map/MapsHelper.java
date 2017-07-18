package tum.Map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Created by Philipp on 7/12/2017.
 */
public class MapsHelper {
    GoogleMap mMap;
    private Context appContext;

    LatLng ZoomOrigin;
    double ZoomValue = 8;

    public MapsHelper(GoogleMap map, Context appContext) {
        this.mMap = map;
        this.appContext = appContext;
    }

    public String getDirectionsUrl(String origin, String dest){
        mMap.clear();
        LatLng o = getLatLong(origin);
        LatLng d = getLatLong(dest);
        ZoomOrigin = new LatLng((o.latitude+d.latitude)/2,(o.longitude+d.longitude)/2);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(o);
        builder.include(d);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 48));
        mMap.addMarker(new MarkerOptions().position(o).title("Start"));
        mMap.addMarker(new MarkerOptions().position(d).title("Ziel"));
        return getDirectionsUrl(o,d);
    }

    public LatLng getLatLong(String place)
    {
        double latitude = 48;
        double longitude = 10;

        Geocoder geoCoder = new Geocoder(appContext, Locale.getDefault());
        try {
            List<Address> address = geoCoder.getFromLocationName(place, 2);
            latitude = address.get(0).getLatitude();
            longitude = address.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LatLng(latitude,longitude);
    }

    public String getDirectionsUrl(LatLng origin, LatLng dest) {


        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }


    /**
     * A method to download json data from url
     */
    public String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        Log.d("DEBUG", "request Rout for connection string " + strUrl);
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}

