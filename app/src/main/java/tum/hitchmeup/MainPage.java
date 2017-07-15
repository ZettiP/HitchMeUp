package tum.hitchmeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import tum.customLayouts.NewsListAdapter;

public class MainPage extends AppCompatActivity {

    List<String> listViewItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ListView news = (ListView)findViewById(R.id.NewsListView);

        String[] list = new String[]{"Hallo","Ich","bims"};
        news.setAdapter(new NewsListAdapter(getApplicationContext(), R.layout.news_layout, list));
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
