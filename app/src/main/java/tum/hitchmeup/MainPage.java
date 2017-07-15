package tum.hitchmeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainPage extends AppCompatActivity {

    //HMUApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //application = (HMUApplication)getApplication();
        //application.appname = "Hui";
        setContentView(R.layout.activity_main_page);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.MainPageNext: {
                Intent HitchPageIntent = new Intent(v.getContext(), HitchMePage.class);
                startActivity(HitchPageIntent);
                break;
            }
            case  R.id.NaviPageNext: {
                Intent NaviPageIntent = new Intent(v.getContext(), NaviPage.class);
                startActivity(NaviPageIntent);
                break;
            }
            case  R.id.Profile: {
                Intent ProfileIntent = new Intent(v.getContext(), Profile.class);
                startActivity(ProfileIntent);
                break;
            }
            case  R.id.Settings: {
                Intent SettingsIntent = new Intent(v.getContext(), Settings.class);
                startActivity(SettingsIntent);
                break;
            }

            default:
                Log.d("DEBUG","no Button found with this id");
                break;
            //.... etc
        }
    }
}
