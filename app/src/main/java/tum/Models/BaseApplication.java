package tum.Models;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import tum.hitchmeup.R;

/**
 * Created by Philipp on 7/17/2017.
 */

public class BaseApplication extends Application{

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
    }

    public String getNewsString()
    {
        return pref.getString(String.valueOf(R.string.NewsList), "");
    }

    public void addToNewsList(String content){
        String newsListString = pref.getString(String.valueOf(R.string.NewsList), "");
        if (newsListString != "")
            newsListString = newsListString + "#" + content;
        else
            newsListString = content;

        editor.putString(String.valueOf(R.string.NewsList), newsListString);
        editor.apply();
        Log.d("NL", "Element '" + content + "' has been added to news List");
    }

    public void clearNewsList()
    {
        editor.putString(String.valueOf(R.string.NewsList), "");
        editor.apply();
    }
}
