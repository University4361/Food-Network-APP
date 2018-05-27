package com.example.julia.delivery.mainscreen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.julia.delivery.App;
import com.example.julia.delivery.MainActivity;
import com.example.julia.delivery.R;
import com.example.julia.delivery.api.models.GetOrdersHistoryRequest;
import com.example.julia.delivery.api.models.GetOrdersRequest;
import com.example.julia.delivery.objects.OrderHistory;
import com.example.julia.delivery.objects.OrderPreview;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lavrov on 20.05.2018.
 */

public class HistoryFragment extends Fragment {

    private MainActivity mainActivity;
    private HistoryAdapter adapter;

    @BindView(R.id.history_recycler_view)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        String defaultValue = null;
        String token = sharedPref.getString(getString(R.string.token), defaultValue);

        GetOrdersHistoryRequest getOrdersHistoryRequest = new GetOrdersHistoryRequest();

        getOrdersHistoryRequest.setToken(token);
        getOrdersHistoryRequest.setFromDate(new GregorianCalendar(2018, 4, 19).getTime());
        getOrdersHistoryRequest.setToDate(new GregorianCalendar(2018, 4, 25).getTime());

        App.getApi().getOrdersHistory(getOrdersHistoryRequest).enqueue(new Callback<List<OrderHistory>>() {
            @Override
            public void onResponse(Call<List<OrderHistory>> call, final Response<List<OrderHistory>> response) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            final List<OrderHistory> orders = response.body();

                            adapter = new HistoryAdapter(orders, mainActivity);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<OrderHistory>> call, Throwable t) {

            }
        });

        return view;
    }

    public static HistoryFragment createNewInstance(@NonNull MainActivity activity){
        HistoryFragment historyFragment = new HistoryFragment();
        historyFragment.mainActivity = activity;
        return historyFragment;
    }
}
