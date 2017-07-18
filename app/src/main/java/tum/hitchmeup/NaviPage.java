package tum.hitchmeup;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import tum.Map.DownloadTask;
import tum.Map.MapsHelper;

/**
 * Created by Philipp on 7/5/2017.
 */
public class NaviPage extends BaseBaseActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private GoogleApiClient client;

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
                StartNavigation();
                break;
            }
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

    }

}
