package tum.hitchmeup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;
import tum.Map.DownloadTask;
import tum.Map.MapsHelper;
import tum.Models.BaseApplication;
import tum.mJsonHttpResponseHandler;

/**
 * Created by Philipp on 7/5/2017.
 */
public class NaviPage extends BaseBaseActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private GoogleApiClient client;
    private int timeLimit;
    private JSONArray naviMatchings = new JSONArray();
    private Context context;


    //android.support.v4.app.Fragment mMapFragment;

    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        /*View view = inflater.inflate(R.layout.activity_navipage, null);*/
        setContentView(R.layout.activity_navipage);
        super.onCreate(savedInstanceState);
        // Gets the MapView from the XML layout and creates it
        nvDrawer.getMenu().getItem(3).setChecked(true);

        mapView = (MapView) findViewById(R.id.MapViewId);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        context = this.getApplicationContext();

        final SeekBar seek = (SeekBar) findViewById(R.id.timepicker);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch (SeekBar seekBar){
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar){
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
                // TODO Auto-generated method stub

                TextView t1 = (TextView) findViewById(R.id.timepickerlabel);
                timeLimit = (int)(progress * 0.6);
                t1.setText(timeLimit + " min");
            }
        });
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        /*
        //mMapFragment = getSupportFragmentManager().findFragmentById(map);
        //

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);*/


        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startNavigation: {
                Log.d("MAP", "onclick worked");
                if (mMap != null)
                    Log.d("MAP", "Map is there");
                hideSoftKeyboard(this);
                StartNavigation();

                EditText start = (EditText) findViewById(R.id.StartEdit);
                EditText ziel = (EditText) findViewById(R.id.ZielEdit);

                RequestParams updateParams = new RequestParams();
                updateParams.put("from", start.getText().toString());
                updateParams.put("to", ziel.getText().toString());
                updateParams.put("maxDetour", "30");

                //     Log.d(TAG, "from:" + fromET.getText().toString());
                //     Log.d(TAG, "to:" + toET.getText().toString());


                AsyncClient.post("api/naviRequest", updateParams, new mJsonHttpResponseHandler(context) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        //      Log.d(TAG, response.toString());

                        if (response != null) {

                            Toast.makeText(context, "Request created", Toast.LENGTH_SHORT).show();
                       //     Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                            naviMatchings = response;



                        } else {
                            Toast.makeText(context, "An Error has occured while trying to create the request", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

            //Posting NaviRqeuest
            break;

            case R.id.LookForHitcher: {
                //TODO: ask server for places for Hitchhackers
            }
            default:
                Log.d("DEBUG", "no Button found with this id");
                break;
            //.... etc
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MAP", "OnMapReady");
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48, 12), 8));
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    //click event with all necessary information
    //shows route afterwards
    public void StartNavigation()
    {
        EditText start = (EditText)findViewById(R.id.StartEdit);
        EditText ziel = (EditText)findViewById(R.id.ZielEdit);

        MapsHelper helper = new MapsHelper(mMap,getApplicationContext());
        String url = helper.getDirectionsUrl(start.getText().toString(), ziel.getText().toString());
        DownloadTask downloadTask = new DownloadTask(helper);
        downloadTask.execute(url);

        BaseApplication app = (BaseApplication)getApplication();
        app.addToNewsList("Navigation to " + ziel.getText().toString() + " has been started");

        final Context context = getApplicationContext();

       /* RequestParams params = new RequestParams();
        params.put("from", start.getText().toString());
        params.put("to", ziel.getText().toString());

        AsyncClient.post("/api/naviRequest", params, new mJsonHttpResponseHandler(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt(context.getString(R.string.server_response)) == 1) {

                        Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                        //   Intent i = new Intent(context, BaseActivity.class);

                        Log.d("FINALLY","hitchrequest sent and response received");
                        //startActivity(startHitchIntent);
                        finish();
                    } else if (response.getInt(context.getString(R.string.server_response)) == 0) {
                        System.out.println("Request failed!");

                        Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                        //v.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}
