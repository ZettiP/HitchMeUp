package tum.hitchmeup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Philipp on 7/5/2017.
 */
public class HitchMePage extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private int timeLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitchmepage);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    @Override
    public void onStart() {
        super.onStart();

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
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HitchMePage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://tum.hitchmeup/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.startHitch: {
                Intent startHitchIntent = new Intent(v.getContext(), MainPage.class);
                //TODO: post HitchRequest via SocketIO/REST/or something else
                Toast t =   Toast.makeText(this,"HitchRequest has been sent", Toast.LENGTH_LONG);
                t.show();
                TextView start = (TextView) v.findViewById(R.id.StartEdit);
                TextView ziel = (TextView) v.findViewById(R.id.ZielEdit);

                startActivity(startHitchIntent);
                break;
            }
            default:
                Log.d("DEBUG","no Button found with this id");
                break;
            //.... etc
        }
    }


}
