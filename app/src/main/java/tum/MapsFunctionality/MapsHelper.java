package tum.MapsFunctionality;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Philipp on 7/12/2017.
 */
public class MapsHelper {

    //creates url to draw line beteen destination and origin
    public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString( destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=YOUR_API_KEY");
        return urlString.toString();
    }
    
    public String message;
    public String response;

    public void Execute(String url, RequestQueue queue)
    {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //TODO: Do something with the response
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                Log.d("debug", "Received an error");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
