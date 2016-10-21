package pomis.app.tuturustations.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.DatePicker;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pomis.app.tuturustations.R;
import pomis.app.tuturustations.data.RealmInstance;
import pomis.app.tuturustations.fragments.AboutFragment;
import pomis.app.tuturustations.fragments.TableFragment;
import pomis.app.tuturustations.network.StationsTask;
/*
 * Активность-оператор бокового меню. По совместительству, главная активность.
 */
public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment selectedFragment;
    Toolbar toolbar;

    public static String name;
    public static String type;

    @BindView(R.id.tv_when)
    TextView tvWhen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!PreferenceManager
                .getDefaultSharedPreferences(this)
                .getBoolean("done", false))
            new StationsTask(this).execute();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        openDefaultTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ButterKnife.bind(this);
    }

    private void openDefaultTab() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, new TableFragment(), "fragment_key");
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setSubtitle(String text) {
        toolbar.setSubtitle(text);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        clearContainer();

        int id = item.getItemId();
        selectedFragment = new Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.nav_table:
                selectedFragment = new TableFragment();
                break;

            case R.id.nav_about:
                selectedFragment = new AboutFragment();
                break;
        }
        transaction.add(R.id.fl_container, selectedFragment, "fragment_key");
        transaction.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void clearContainer() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentByTag("fragment_key") != null)
            transaction.detach(
                    getSupportFragmentManager().findFragmentByTag("fragment_key")
            );
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RealmInstance.closeAll();
    }
}
