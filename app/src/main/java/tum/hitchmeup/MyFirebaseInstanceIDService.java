package tum.hitchmeup;

/**
 * Created by serfk on 18.07.2017.
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.loopj.android.http.RequestParams;
import tum.hitchmeup.AsyncClient;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import tum.mJsonHttpResponseHandler;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        RequestParams updateParams = new RequestParams();
        updateParams.put("token", token);

        AsyncClient.post("api/user/profile", updateParams, new mJsonHttpResponseHandler(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "updated token in DB");
            }
        });
    }
}