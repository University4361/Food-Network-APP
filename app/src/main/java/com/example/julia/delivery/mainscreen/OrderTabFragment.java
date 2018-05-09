package com.example.julia.delivery.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.julia.delivery.MainActivity;
import com.example.julia.delivery.OrderFragment;
import com.example.julia.delivery.R;
import com.example.julia.delivery.objects.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderTabFragment extends Fragment {
    @BindView(R.id.orders_recycler_view)RecyclerView mRecyclerView;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_tab, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Order order = new Order();
            order.setDate("28.02.2018");
            order.setAddress("Богатырский проспект, дом 2");
            orders.add(order);
        }
        OrderAdapter adapter = new OrderAdapter(orders, new OnCustomClickListener() {
            @Override
            public void onClick(int position) {
                mainActivity.switchToOrderFragment();
            }
        });
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    public static OrderTabFragment createNewInstance(@NonNull MainActivity activity) {
        OrderTabFragment orderTabFragment = new OrderTabFragment();
        orderTabFragment.mainActivity = activity;
        return orderTabFragment;
    }
}
