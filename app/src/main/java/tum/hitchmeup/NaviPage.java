package tum.hitchmeup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Philipp on 7/5/2017.
 */
public class NaviPage extends BaseBaseActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    android.support.v4.app.Fragment mMapFragment;

    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = (Context)getApplication();
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*View view = inflater.inflate(R.layout.activity_navipage, null);*/
        setContentView(R.layout.activity_navipage);
        super.onCreate(savedInstanceState);
        // Gets the MapView from the XML layout and creates it
        nvDrawer.getMenu().getItem(3).setChecked(true);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        /*mapView = (MapView) findViewById(R.id.MapViewId);
        //mMapFragment = getSupportFragmentManager().findFragmentById(map);
        //mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);*/

        return super.onCreateView(parent, name, context, attrs);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.startNavigation: {
                Log.d("MAP","onclick worked");
                if(mMap!= null)
                    Log.d("MAP","Map is there");


                Intent startHitchIntent = new Intent(v.getContext(), MainPage.class);
                //TODO: post Navigation to server via SocketIO/REST/or something else
                Toast t =   Toast.makeText(this,"HitchRequest has been sent", Toast.LENGTH_LONG);
                t.show();
                TextView start = (TextView) v.findViewById(R.id.StartEdit);
                TextView ziel = (TextView) v.findViewById(R.id.ZielEdit);

                //TODO: Add marker for start and ziel and show way between them

                //startActivity(startHitchIntent);
                break;
            }
            case R.id.LookForHitcher:{
                //TODO: ask server for places for Hitchhackers
            }
            default:
                Log.d("DEBUG","no Button found with this id");
                break;
            //.... etc
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("MAP","OnMapReady");
    }
}
