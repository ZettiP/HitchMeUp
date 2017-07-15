package tum.hitchmeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Philipp on 7/16/2017.
 */

public class LoginPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
    }

    public void onClick(View view) {
        //LoginButton
        //maybe check if it really is
        //TODO: Do LoginMagic here
        TextView user = (TextView)findViewById(R.id.userNameEdit);
        TextView password = (TextView)findViewById(R.id.pwEdit);


        //UserName = user.getText();
        //UserName = password.getText();

        Intent HitchPageIntent = new Intent(view.getContext(), MainPage.class);
        startActivity(HitchPageIntent);
    }
}
