package hu.kata.leszko.tsp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import hu.kata.leszko.tsp.ui.TravellingSalesmanView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    TravellingSalesmanView travellingSalesmanView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        travellingSalesmanView = new TravellingSalesmanView(this);
        RelativeLayout travelingMainLayout = findViewById(R.id.travelling_main_view);
        travelingMainLayout.addView(travellingSalesmanView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int selectedMenuId = sharedPref.getInt(getString(R.string.solver_key), R.id.nav_bandb);
        navigationView.setCheckedItem(selectedMenuId);
        travellingSalesmanView.setTSPAlgorithm(selectedMenuId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        travellingSalesmanView.clearCities();
        travellingSalesmanView.invalidate();
        Toast.makeText(this, "Városok törölve", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        SharedPreferences.Editor editor =getPreferences(Context.MODE_PRIVATE).edit();
        editor.putInt(getString(R.string.solver_key), menuItem.getItemId());
        editor.commit();

        travellingSalesmanView.setTSPAlgorithm(menuItem.getItemId());

        switch ((menuItem.getItemId())){
            case R.id.nav_bandb:
                Toast.makeText(this, getResources().getString(R.string.menu_bandb), Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_bruteforce:
                Toast.makeText(this, getResources().getString(R.string.menu_bruteforce), Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_greedy:
                Toast.makeText(this, getResources().getString(R.string.menu_greedy), Toast.LENGTH_SHORT).show();
                break;
        }

        travellingSalesmanView.invalidate();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
