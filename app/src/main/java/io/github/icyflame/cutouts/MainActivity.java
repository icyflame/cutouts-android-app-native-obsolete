package io.github.icyflame.cutouts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UserLoginDialog.userLoginResults {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sidebar_login: {
                Log.d(TAG, "onClick: Starting the dialog!");
                UserLoginDialog.newInstance().show(getSupportFragmentManager(), "user.login");
            }
        }
        return false;
    }

    @Override
    public void userLoginDone(boolean result, JsonObject authResponse, JsonArray articlesList) {
        if (result) {
            // Login was a success
            // Have to load the List fragment inside the main content framelayout
            ArticlesListFragment listArticles = ArticlesListFragment.newInstance(articlesList);
            ((FrameLayout) findViewById(R.id.main_container)).removeAllViews();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, listArticles)
                    .commit();
        } else {
            // Login didn't happen properly!
            // Show a dialog to the user and do nothing after that
        }
    }
}
