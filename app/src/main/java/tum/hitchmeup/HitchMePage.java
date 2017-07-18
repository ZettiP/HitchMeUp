package tum.hitchmeup;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_hitchmepage);
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        nvDrawer.getMenu().getItem(2).setChecked(true);
        context = this.getApplicationContext();
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
        switch (v.getId()) {
            case  R.id.startHitch: {
                final Intent startHitchIntent = new Intent(v.getContext(), MainPage.class);
                //TODO: post HitchRequest via SocketIO/REST/or something else

                EditText start = (EditText) findViewById(R.id.StartEdit);
                EditText ziel = (EditText) findViewById(R.id.ZielEdit);
                RequestParams params = new RequestParams();
                params.put("from", start.getText().toString());
                params.put("to", ziel.getText().toString());


                AsyncClient.post("/api/hitchRequest", params, new mJsonHttpResponseHandler(this) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if (response.getInt(context.getString(R.string.server_response)) == 1) {

                                Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                                //   Intent i = new Intent(context, BaseActivity.class);
                                startActivity(startHitchIntent);
                                finish();
                            } else if (response.getInt(context.getString(R.string.server_response)) == 0) {
                                System.out.println("Request failed!");

                                Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                                v.setEnabled(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


                Toast t =   Toast.makeText(this,"HitchRequest has been sent", Toast.LENGTH_LONG);
                t.show();
                //              TextView start = (TextView) v.findViewById(R.id.StartEdit);
                //            TextView ziel = (TextView) v.findViewById(R.id.ZielEdit);

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
