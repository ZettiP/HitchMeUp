package tum.Map;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;

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

        helper.mMap.clear();
        ParserTask parserTask = new ParserTask(helper);

        // Invokes the thread for parsing the JSON data
        parserTask.execute(result);
        //TODO change zoom origin
        helper.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(helper.zoomValue,8));
    }
}
