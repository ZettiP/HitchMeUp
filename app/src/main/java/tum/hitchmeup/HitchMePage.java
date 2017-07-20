package tum.hitchmeup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import tum.Models.BaseApplication;
import tum.mJsonHttpResponseHandler;

/**
 * Created by Philipp on 7/5/2017.
 */
public class HitchMePage extends BaseBaseActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private Context context;
    private EditText start;
    private EditText ziel;
    public JSONArray hitchMatchings = new JSONArray();
    private String userN = null;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hitchmepage);
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        nvDrawer.getMenu().getItem(2).setChecked(true);
        context = this.getApplicationContext();
        start = (EditText) findViewById(R.id.StartEdit);
        ziel = (EditText) findViewById(R.id.ZielEdit);
    }

    @Override
    public void onStart() {
        super.onStart();


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

    public void onClick(final View v) {
        RequestParams updateParams = new RequestParams();
        updateParams.put("from", start.getText().toString());
        updateParams.put("to", ziel.getText().toString());

   //     Log.d(TAG, "from:" + fromET.getText().toString());
   //     Log.d(TAG, "to:" + toET.getText().toString());


        AsyncClient.post("api/hitchRequest", updateParams, new mJsonHttpResponseHandler(context) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {

          //      Log.d(TAG, response.toString());

                if (response != null) {

                    Toast.makeText(context, "Request created", Toast.LENGTH_SHORT).show();
                //    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                    hitchMatchings =response;

                    String userID = null;
                    try {
                        userID = response.getJSONObject(0).getString("user");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AsyncClient.get(("api/user/"+ userID), null, new mJsonHttpResponseHandler(context) {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                            try {
                                userN = response.getString("firstname");
                                //  Toast.makeText(context, userN, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            HitchMePage.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    buildMatchAlert().show();
                                }
                            });



                        }});


                } else  {
                    Toast.makeText(context, "An Error has occured while trying to create the request", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private AlertDialog buildMatchAlert(){
        builder = new AlertDialog.Builder(HitchMePage.this);
        //wenn Match gefunden
        final String username;
        String uName;
        if(userN!=null) {
                        builder.setMessage("Do you want a drive with " + userN)
                                .setTitle("Match!");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Nachricht an Server, dass akzeptiert wurde

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Nachricht an Server, dass abgelehnt wurde

                            }
                        });

        } else {
            builder.setMessage("Zurzeit liegt kein passendes Match vor. Du wirst benachrichtigt, wenn sich das ändert.")
                    .setTitle("Kein Match!");
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
        }
        AlertDialog dialog = builder.create();
        return dialog;
    }




}




