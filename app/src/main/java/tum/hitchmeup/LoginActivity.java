package tum.hitchmeup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import tum.SharedPreferencesHandler;
import tum.mJsonHttpResponseHandler;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;
    Button registerButton;
    Context context;
    CheckBox cb_remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Login");

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginBtn);
        registerButton = (Button) findViewById(R.id.registerBtn);
        cb_remember_me = (CheckBox) findViewById(R.id.cb_remember);
        context = this.getApplicationContext();

        email.setText(SharedPreferencesHandler.getString(context, "email"));
        password.setText(SharedPreferencesHandler.getString(context, "password"));
    }

    public void login(final View v) {

        RequestParams params = new RequestParams();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        params.put("token",  FirebaseInstanceId.getInstance().getToken());

        AsyncClient.post("auth/login", params, new mJsonHttpResponseHandler(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt(context.getString(R.string.server_response)) == 1) {

                        if (cb_remember_me.isChecked()) {

                            SharedPreferencesHandler.writeString(context, "email", email.getText().toString());
                            SharedPreferencesHandler.writeString(context, "password", password.getText().toString());
                            SharedPreferencesHandler.writeString(context, "token", FirebaseInstanceId.getInstance().getToken());

                            SharedPreferencesHandler.writeBoolean(context, "rememberMe", true);

                        }
                        System.out.println("Login successfull!");

                        Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, MainPage.class);
                        startActivity(i);
                        finish();
                    } else if (response.getInt(context.getString(R.string.server_response)) == 0) {
                        System.out.println("Login failed!");

                        Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                        v.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void register(View v) {

        startActivity(new Intent(this, RegisterActivity.class));
    }

}
