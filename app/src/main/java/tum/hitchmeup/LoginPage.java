package tum.hitchmeup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import tum.Connections.SinglReqQue;


/**
 * Created by Philipp on 7/16/2017.
 */

public class LoginPage extends AppCompatActivity {


    private EditText first_name_regist;
    private EditText last_name_regist;
    private EditText email_regist;
    private EditText pswd_regist;
    private EditText email_login;
    private EditText pswd_login;


    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        first_name_regist = (EditText) findViewById(R.id.firstnameRegister);
        last_name_regist = (EditText) findViewById(R.id.lastnameRegister);
        email_regist = (EditText) findViewById(R.id.emailRegister);
        pswd_regist = (EditText) findViewById(R.id.passwordRegister);
        email_login = (EditText) findViewById(R.id.emailLogin);
        pswd_login = (EditText) findViewById(R.id.passwordLogin);

        queue = SinglReqQue.getInstance(this.getApplicationContext()).
                getRequestQueue();
        queue.start();
    }

    public void registerOnClick(View view) {
        final JSONObject user = new JSONObject();
        try {
            user.put("firstname", first_name_regist.getText().toString());
            user.put("surname", last_name_regist.getText().toString());
            user.put("password", pswd_regist.getText().toString());
            user.put("email", email_regist.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, SinglReqQue.REGISTER_URL, user, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                            serverResponseAllert(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                final int mStatusCode = response.statusCode;
                        if(mStatusCode==200){
                            serverResponseAllert("Erfolgreich registriert!");
                        }
                return super.parseNetworkResponse(response);
            }
        };

        SinglReqQue.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    public void loginOnClick(final View view) {
        final JSONObject user = new JSONObject();
        try {
            user.put("email", email_login.getText().toString());
            user.put("password", pswd_login.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, SinglReqQue.LOGIN_URL, user, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        SinglReqQue.userJSON = response;
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                serverResponseAllert(res);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }){

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                final int mStatusCode = response.statusCode;
                if(mStatusCode==200){
                    Intent HitchPageIntent = new Intent(view.getContext(), MainPage.class);
                    startActivity(HitchPageIntent);
                }
                return super.parseNetworkResponse(response);
            }
        };
        
        SinglReqQue.getInstance(this).addToRequestQueue(jsObjRequest);
    }


    private void serverResponseAllert(final String msg) {
        LoginPage.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                builder.setMessage(msg)
                        .setTitle("Server Antwort");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });
                builder.create().show();
            }});
    }
}

/*
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SinglReqQue.URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, SinglReqQue.URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    //    mTxtDisplay.setText("Response: " + response.toString());
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

*/