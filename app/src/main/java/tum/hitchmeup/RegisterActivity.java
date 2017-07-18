package tum.hitchmeup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import tum.mJsonHttpResponseHandler;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText firstname;
    EditText surname;
    Button registerButton;
    Context context;
    CheckBox cb_remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.email);
        firstname = (EditText) findViewById(R.id.firstname);
        surname = (EditText) findViewById(R.id.surname);
        password = (EditText) findViewById(R.id.password);

        registerButton = (Button) findViewById(R.id.registerBtn);
        context = this.getApplicationContext();
    }

    public void register(final View v) {
        RequestParams params = new RequestParams();
        params.put("email", email.getText().toString());
        params.put("password", password.getText().toString());
        params.put("firstname", firstname.getText().toString());
        params.put("surname", surname.getText().toString());

        AsyncClient.post("auth/register", params, new mJsonHttpResponseHandler(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(context, "Registration successfull", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

