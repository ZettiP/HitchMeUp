package tum.hitchmeup;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tum.Models.BaseApplication;
import tum.customLayouts.NewsListAdapter;

public class MainPage extends BaseBaseActivity {

    List<String> listViewItems;
    NewsListAdapter listAdapter;
    BaseApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_page);
        super.onCreate(savedInstanceState);
        Log.d("MainPage", "onCreate");

        //initialize NewsList
        ListView news = (ListView)findViewById(R.id.NewsListView);
        Log.d("MainPage News", news.toString());

        app = (BaseApplication) getApplication();

        List<String> list = new ArrayList<String>();
        String newsListString = app.getNewsString();
        if(newsListString == "")
        {
            listAdapter = new NewsListAdapter(getApplicationContext(), R.layout.news_layout,  new ArrayList<String>());
            TextView view = (TextView) findViewById(R.id.NoNewsText);
            view.setTextKeepState("Currently no News");
        }
        else
        {
            list = new ArrayList<String>(Arrays.asList(newsListString.split("#")));
            listAdapter = new NewsListAdapter(getApplicationContext(), R.layout.news_layout, list);
            TextView view = (TextView) findViewById(R.id.NoNewsText);
            view.setTextKeepState("");
        }

        news.setAdapter(listAdapter);



        nvDrawer.getMenu().getItem(0).setChecked(true);
    }



    public void onClick(View v) {
        /*switch (v.getId()) {
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
        }*/
    }



}
