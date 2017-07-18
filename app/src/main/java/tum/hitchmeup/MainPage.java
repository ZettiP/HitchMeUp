package tum.hitchmeup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tum.Models.BaseApplication;
import tum.customLayouts.NewsListAdapter;

public class MainPage extends BaseBaseActivity {

    List<String> listViewItems;
    NewsListAdapter listAdapter;
    BaseApplication app;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_page);
        super.onCreate(savedInstanceState);
        //initialize NewsList
        ListView news = (ListView)findViewById(R.id.NewsListView);
        List<String> list = new ArrayList<String>();
        String newsListString = "";
        list = new ArrayList<String>(Arrays.asList(newsListString.split("#")));
        listAdapter = new NewsListAdapter(getApplicationContext(), R.layout.news_layout, list);
        news.setAdapter(listAdapter);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        app = (BaseApplication) getApplication();

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

    private void addListElement(String element)
    {
        listAdapter.add(element);
        listAdapter.notifyDataSetChanged();
    }

    private void addToNewsList(String content){
        String newsListString = pref.getString(String.valueOf(R.string.NewsList), "");
        if (newsListString != "")
            newsListString = newsListString + "#" + content;
        else
            newsListString = content;

        editor.putString(String.valueOf(R.string.NewsList), newsListString);
        editor.apply();
        addListElement(content);
        Log.d("NL", "Element '" + content + "' has been added to news List");


    }

}
