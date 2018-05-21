package com.example.julia.delivery.mainscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.julia.delivery.MainActivity;
import com.example.julia.delivery.R;
import com.example.julia.delivery.objects.OrderPreview;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainFragment extends Fragment {

    private MainActivity mainActivity;
    private PagerAdapter adapter;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.orders)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.map)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = view.findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), mainActivity);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    public static MainFragment createNewInstance(@NonNull MainActivity activity) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.mainActivity = activity;
        return mainFragment;
    }

    public void UpdatePreview(OrderPreview preview) {
        ((OrderTabFragment) adapter.instantiateItem(viewPager, 0)).UpdatePreview(preview);
    }

    public void SetupAddresses(List<OrderPreview> previews) {
        ((MapTabFragment) adapter.instantiateItem(viewPager, 1)).SetupAddresses(previews);
    }
}
