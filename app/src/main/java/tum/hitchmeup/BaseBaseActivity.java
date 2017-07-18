package tum.hitchmeup;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import tum.SharedPreferencesHandler;


public class BaseBaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView nvDrawer;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set a ToolBar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = setupDrawerToggle();
        dlDrawer.setDrawerListener(drawerToggle);

        nvDrawer.getMenu().getItem(0).setChecked(true);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        menuItem.setChecked(true);
        switch(menuItem.getItemId()) {
            case R.id.nav_home: // home
                Intent MainPage = new Intent(getApplicationContext(), MainPage.class);
                startActivity(MainPage);
                break;
            case R.id.nav_settings: // settings
                Intent SettingsIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity(SettingsIntent);
                break;
            case R.id.nav_hitch_request:
                Intent HitchPageIntent = new Intent(getApplicationContext(), HitchMePage.class);
                startActivity(HitchPageIntent);
                break;
            case R.id.nav_navi_request:
                Intent NaviPageIntent = new Intent(getApplicationContext(), NaviPage.class);
                startActivity(NaviPageIntent);
                break;
            case R.id.nav_profile:
                Intent ProfileIntent = new Intent(getApplicationContext(), Profile.class);
                startActivity(ProfileIntent);
                break;
            case R.id.nav_logout:
                //do logout
                SharedPreferencesHandler.writeBoolean(getApplicationContext(), "rememberMe", false);
                Intent LogoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(LogoutIntent);
                break;
            default:
                Intent MainPage2 = new Intent(getApplicationContext(), MainPage.class);
                startActivity(MainPage2);
        }
        setTitle(menuItem.getTitle());
        dlDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, dlDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}