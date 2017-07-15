package tum.hitchmeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Philipp on 7/16/2017.
 */

public class LoginPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //application = (HMUApplication)getApplication();
        //application.appname = "Hui";
        setContentView(R.layout.login_page);
    }

    public void onClick(View view) {
        //LoginButton
        //maybe check if it really is
        Intent HitchPageIntent = new Intent(view.getContext(), MainPage.class);
        startActivity(HitchPageIntent);
    }
}
