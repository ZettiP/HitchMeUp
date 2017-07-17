package tum.hitchmeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import static tum.hitchmeup.R.id.map;

/**
 * Created by Philipp on 7/5/2017.
 */
public class NaviPage extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    android.support.v4.app.Fragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navipage);

        mMapFragment = getSupportFragmentManager().findFragmentById(map);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.startNavigation: {
                Intent startHitchIntent = new Intent(v.getContext(), MainPage.class);
                //TODO: post Navigation to server via SocketIO/REST/or something else
                Toast t =   Toast.makeText(this,"HitchRequest has been sent", Toast.LENGTH_LONG);
                t.show();
                TextView start = (TextView) v.findViewById(R.id.StartEdit);
                TextView ziel = (TextView) v.findViewById(R.id.ZielEdit);

                mMapFragment.setMenuVisibility(true);
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
    }
}
