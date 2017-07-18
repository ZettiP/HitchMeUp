package tum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import tum.fr.tkeunebr.gravatar.Gravatar;
import tum.hitchmeup.AsyncClient;
import tum.hitchmeup.R;
import tum.mJsonHttpResponseHandler;


public class HomeFragment extends Fragment {

    String firstname;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        LayoutInflater dViewinflater = getActivity().getLayoutInflater();
        final View dView = dViewinflater.inflate(R.layout.dialoglayout, null);

        final TextView firstnameTV = (TextView) rootView.findViewById(R.id.firstname);
        final TextView emailTV = (TextView) rootView.findViewById(R.id.email);


        AsyncClient.get("api/user/profile", null, new mJsonHttpResponseHandler(getContext()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                    try {

                        firstname = response.getString("firstname");
                        email = response.getString("email");

                        CircleImageView mCircleImageView = (CircleImageView) rootView.findViewById(R.id.profile_image_search);
                        String gravatarUrl = Gravatar.init().with(email).build();

                        Picasso.with(getContext())
                                .load(gravatarUrl)
                                .resize(500, 500)
                                .centerCrop()
                                .into(mCircleImageView);

                        firstnameTV.setText(firstname);
                        emailTV.setText(email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "Toast: Sorry, couldn't Get your Information try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

}