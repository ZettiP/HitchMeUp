package tum.Map;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

// Fetches data from url passed
public class DownloadTask extends AsyncTask<String, Void, String> {


    MapsHelper helper;
    public DownloadTask(MapsHelper mapsHelper) {
        helper = mapsHelper;
    }

    // Downloading data in non-ui thread
    @Override
    protected String doInBackground(String... url) {

        // For storing data from web service
        String data = "";

        try{
            // Fetching the data from web service
            data = helper.downloadUrl(url[0]);
        }catch(Exception e){
            Log.d("Background Task",e.toString());
        }
        return data;
    }

    // Executes in UI thread, after the execution of
    // doInBackground()
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        ParserTask parserTask = new ParserTask(helper);

        // Invokes the thread for parsing the JSON data
        parserTask.execute(result);

        helper.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.187762, 11.563206),8));
    }
}
