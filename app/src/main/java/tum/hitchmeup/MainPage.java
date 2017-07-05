package tum.hitchmeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.MainPageNext: {
                Intent nexPageIntent = new Intent(v.getContext(), HitchMePage.class);
                startActivity(nexPageIntent);
                break;
            }
            default:
                Log.d("DEBUG","no Button found with this id");
                break;
            //.... etc
        }
    }

}
