package com.example.julia.delivery;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.julia.delivery.mainscreen.HistoryFragment;
import com.example.julia.delivery.mainscreen.MainFragment;
import com.example.julia.delivery.mainscreen.MapTabFragment;
import com.example.julia.delivery.objects.Courier;
import com.example.julia.delivery.objects.OrderPreview;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    public MainFragment mainFragment;
    public HistoryFragment historyFragment;
    View headerView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultValue = null;
        String user = sharedPref.getString(getString(R.string.user), defaultValue);

        Courier courier = new Gson().fromJson(user, Courier.class);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);

        ImageView imageView = ((ImageView)headerView.findViewById(R.id.imageView));

        imageView.setClipToOutline(true);

        new DownloadImageTask(imageView)
                .execute(courier.getPhotoUrl());

        ((TextView)headerView.findViewById(R.id.courierName)).setText(courier.getName());
        ((TextView)headerView.findViewById(R.id.ratingTV)).setText(String.valueOf(courier.getRate()));
        ((RatingBar)headerView.findViewById(R.id.ratingBar)).setRating(courier.getRate());

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportActionBar() != null) {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    } else {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        toggle.syncState();
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                drawer.openDrawer(Gravity.START);
                            }
                        });
                    }
                }
            }
        });


        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.setCheckedItem(R.id.nav_orders);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_orders) {
            mainFragment = MainFragment.createNewInstance(this);

            if (mainFragment != null) {
                fragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit();
            }
        }

        if (id == R.id.nav_history) {
            historyFragment = HistoryFragment.createNewInstance(this);

            if (historyFragment != null) {
                fragmentManager.beginTransaction().replace(R.id.container, historyFragment).commit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchToOrderFragment(OrderPreview preview) {
        Bundle bundle = new Bundle();
        bundle.putInt("prewId", preview.getId());

        fragment = new OrderFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
    }

    public void  updateStatusPreview(OrderPreview preview)
    {
        mainFragment.UpdatePreview(preview);
    }

    public void SetupAddresses(List<OrderPreview> previews) {
        mainFragment.SetupAddresses(previews);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
