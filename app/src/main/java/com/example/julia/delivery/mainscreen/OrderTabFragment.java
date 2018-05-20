package com.example.julia.delivery.mainscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.julia.delivery.App;
import com.example.julia.delivery.MainActivity;
import com.example.julia.delivery.OrderFragment;
import com.example.julia.delivery.R;
import com.example.julia.delivery.api.models.AuthResponse;
import com.example.julia.delivery.api.models.GetOrdersRequest;
import com.example.julia.delivery.objects.Order;
import com.example.julia.delivery.objects.OrderPreview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTabFragment extends Fragment {
    @BindView(R.id.orders_recycler_view)RecyclerView mRecyclerView;
    private MainActivity mainActivity;
    private OrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_tab, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        String defaultValue = null;
        String token = sharedPref.getString(getString(R.string.token), defaultValue);

        GetOrdersRequest getOrdersRequest = new GetOrdersRequest();

        getOrdersRequest.setToken(token);

        App.getApi().getOrders(getOrdersRequest).enqueue(new Callback<List<OrderPreview>>() {
            @Override
            public void onResponse(Call<List<OrderPreview>> call, final Response<List<OrderPreview>> response) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            final List<OrderPreview> orders = response.body();

                            adapter = new OrderAdapter(orders, new OnCustomClickListener() {
                                @Override
                                public void onClick(int position) {
                                    mainActivity.switchToOrderFragment(adapter.getItem(position));
                                }
                            }, mainActivity);
                            mRecyclerView.setAdapter(adapter);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<OrderPreview>> call, Throwable t) {

            }
        });

        return view;
    }

    public static OrderTabFragment createNewInstance(@NonNull MainActivity activity) {
        OrderTabFragment orderTabFragment = new OrderTabFragment();
        orderTabFragment.mainActivity = activity;
        return orderTabFragment;
    }

    public void UpdatePreview(OrderPreview preview)
    {
        adapter.UpdateAndSetupItems(preview);
        adapter.notifyDataSetChanged();
    }
}
