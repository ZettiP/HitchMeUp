package tum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import tum.hitchmeup.AsyncClient;
import tum.hitchmeup.R;
import tum.mJsonHttpResponseHandler;

public class SettingsFragment extends Fragment {

    Button delAccount;
    Button editProfile;
    String email;
    String firstname;
    String surname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        final EditText firstnameET = (EditText) rootView.findViewById(R.id.firstname);
        final EditText surnameET = (EditText) rootView.findViewById(R.id.surname);
        final EditText emailET = (EditText) rootView.findViewById(R.id.email);
        final EditText passwordET = (EditText) rootView.findViewById(R.id.password);
        final ProgressBar load = (ProgressBar) rootView.findViewById(R.id.load_profile);
        final String pswdString = passwordET.getText().toString();


        AsyncClient.get("api/user/profile", null, new mJsonHttpResponseHandler(getContext()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                load.setVisibility(View.GONE);

                if (response != null) {
                    try {
                        firstname = response.getString("firstname");
                        surname = response.getString("surname");
                        email = response.getString("email");

                        firstnameET.setText(firstname);
                        surnameET.setText(surname);
                        emailET.setText(email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(getContext(), "Toast: Sorry, couldn't Get your Information try again.", Toast.LENGTH_SHORT).show();

                }
            }

        });

        editProfile = (Button) rootView.findViewById(R.id.button);
        editProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RequestParams updateParams = new RequestParams();
                updateParams.put("first", firstnameET.getText().toString());
                updateParams.put("surname", surnameET.getText().toString());
                updateParams.put("email", emailET.getText().toString());
                if (pswdString.length() != 0) {

                    updateParams.put("password", emailET.getText().toString());

                }

                AsyncClient.post("api/user/profile", updateParams, new mJsonHttpResponseHandler(getContext()) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        if (response != null) {

                            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();

                        } else  {
                            Toast.makeText(getContext(), "An Error has occured while trying to make changes to your profile", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        });

        return rootView;
    }
}