package tum.Connections;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class SinglReqQue {
    public static final String URL ="http://hmu-backend.herokuapp.com";
    public static final String REGISTER_URL = URL + "/auth/register";
    public static final String LOGIN_URL = URL + "/auth/login";
    private static SinglReqQue mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    public static JSONObject userJSON;

    private SinglReqQue(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized SinglReqQue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SinglReqQue(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }


}
