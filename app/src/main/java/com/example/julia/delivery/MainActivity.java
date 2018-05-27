package com.example.julia.delivery;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.julia.delivery.objects.OrderStatus;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    public MainFragment mainFragment;
    public HistoryFragment historyFragment;
    View headerView;
    NavigationView navigationView;
    List<OrderPreview> allPreviews;
    OrderStatus currentFilterStatus;

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

        if (id == R.id.log_out) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.token), null);
            editor.putString(getString(R.string.user), null);
            editor.apply();

            Intent intent = new Intent(this, RegistrationActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_open:
                currentFilterStatus = OrderStatus.InProcess;
                SetupAddresses(allPreviews, OrderStatus.InProcess);
                return true;
            case R.id.show_new:
                currentFilterStatus = OrderStatus.New;
                SetupAddresses(allPreviews, OrderStatus.New);
                return true;
            case R.id.show_all:
                SetupAddresses(allPreviews);
                return true;
            case R.id.show_closed:
                currentFilterStatus = OrderStatus.Completed;
                SetupAddresses(allPreviews, OrderStatus.Completed);
                return true;
            case R.id.show_canceled:
                currentFilterStatus = OrderStatus.Canceled;
                SetupAddresses(allPreviews, OrderStatus.Canceled);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void  updateStatusPreview(OrderPreview preview) {
        for (OrderPreview orderPreview :
                allPreviews) {
            if (orderPreview.getId() == preview.getId()) {
                orderPreview.setOrderStatus(preview.getOrderStatus());
            }
        }

        if (currentFilterStatus == OrderStatus.None)
            SetupAddresses(allPreviews);
        else
            SetupAddresses(allPreviews, currentFilterStatus);
    }

    public void SetupAddresses(List<OrderPreview> previews) {
        currentFilterStatus = OrderStatus.None;
        allPreviews = previews;
        mainFragment.SetupAddresses(previews);
    }

    public void SetupAddresses(List<OrderPreview> previews, OrderStatus status) {
        List<OrderPreview> newPreviews = new ArrayList<>();

        for (OrderPreview preview :
                previews) {
            if (preview.getOrderStatus() == status) {
                newPreviews.add(preview);
            }
        }

        mainFragment.SetupAddresses(newPreviews);
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
