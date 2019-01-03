package io.revze.kamusku.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import io.revze.kamusku.R;
import io.revze.kamusku.eventbus.ClearSearchEvent;
import io.revze.kamusku.eventbus.SearchEvent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBar actionBar;
    private EventBus eventBus = EventBus.getDefault();
    private LinearLayout layoutTitle;
    private TextView tvSearchTitle;
    private String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        layoutTitle = findViewById(R.id.layout_title);
        tvSearchTitle = findViewById(R.id.tv_search_title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment, new IndonesiaEnglishWordFragment());
        fragmentTransaction.commit();
        currentFragment = IndonesiaEnglishWordFragment.class.getSimpleName();

        actionBar.setSubtitle(getString(R.string.menu_id_to_en));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                eventBus.post(new SearchEvent(s));
                return false;
            }
        });
        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                layoutTitle.setVisibility(View.VISIBLE);
                if (currentFragment.equals(IndonesiaEnglishWordFragment.class.getSimpleName())) {
                    tvSearchTitle.setText(getString(R.string.menu_id_to_en));
                } else {
                    tvSearchTitle.setText(getString(R.string.menu_en_to_id));
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                layoutTitle.setVisibility(View.GONE);
                eventBus.post(new ClearSearchEvent());
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_id_to_en) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, new IndonesiaEnglishWordFragment());
            fragmentTransaction.commit();
            currentFragment = IndonesiaEnglishWordFragment.class.getSimpleName();

            actionBar.setSubtitle(getString(R.string.menu_id_to_en));
        } else if (id == R.id.nav_en_to_id) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, new EnglishIndonesiaWordFragment());
            fragmentTransaction.commit();
            currentFragment = EnglishIndonesiaWordFragment.class.getSimpleName();

            actionBar.setSubtitle(getString(R.string.menu_en_to_id));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
