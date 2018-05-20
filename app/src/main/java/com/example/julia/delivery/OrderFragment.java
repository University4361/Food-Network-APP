package com.example.julia.delivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julia.delivery.api.models.BoolResponse;
import com.example.julia.delivery.api.models.GetOrderRequest;
import com.example.julia.delivery.api.models.UpdateOrderStatusRequest;
import com.example.julia.delivery.api.models.VerifyOrderRequest;
import com.example.julia.delivery.mainscreen.OrderAdapter;
import com.example.julia.delivery.objects.Order;
import com.example.julia.delivery.objects.OrderDetails;
import com.example.julia.delivery.objects.OrderPreview;
import com.example.julia.delivery.objects.OrderStatus;
import com.example.julia.delivery.objects.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    @BindView(R.id.order_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.change_order_image)
    ImageView mChangeImage;
    @BindView(R.id.price_value)
    TextView mTotalPrice;
    @BindView(R.id.date_time_value)
    TextView mDateTime;
    @BindView(R.id.name_value)
    TextView mName;
    @BindView(R.id.id_value)
    TextView mOrderNumber;
    @BindView(R.id.status_value)
    TextView mOrderStatus;
    @BindView(R.id.phone_value)
    TextView mPhone;
    @BindView(R.id.address_value)
    TextView mAddressValue;
    @BindView(R.id.order_button)
    Button mOrderButton;
    private OrderDetailsAdapter adapter;
    private boolean isNeedToChange;
    private List<Product> products;
    OrderDetails order;
    String token;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        int prewId = getArguments().getInt("prewId");

        mRecyclerView.setLayoutManager(llm);

        mRecyclerView.setNestedScrollingEnabled(false);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String defaultValue = null;
        token = sharedPref.getString(getString(R.string.token), defaultValue);

        GetOrderRequest getOrderRequest = new GetOrderRequest();

        getOrderRequest.setOrderID(prewId);
        getOrderRequest.setToken(token);

        App.getApi().getOrder(getOrderRequest).enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, final Response<OrderDetails> response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {

                            order = response.body();

                            if (order == null)
                                return;

                            updateOrderStatus(order.getOrderStatus());

                            android.text.format.DateFormat df = new android.text.format.DateFormat();

                            mDateTime.setText(df.format("dd.MM.yyyy HH:mm", order.getDeliveryTime()));
                            mName.setText(order.getCustomer().getName());
                            mOrderNumber.setText("№" + String.valueOf(order.getId()));
                            mOrderStatus.setText(order.getOrderStatus().toString());
                            mPhone.setText(order.getCustomer().getPhoneNumber());
                            mAddressValue.setText(order.getAddress().getStreet());

                            products = order.getOrderProducts();

                            mTotalPrice.setText(String.valueOf(order.getPrice()));

                            adapter = new OrderDetailsAdapter(products, new OnChangeProductsListener() {
                                @Override
                                public void onClick(int position, boolean isAdd) {
                                    products.get(position).setQuantity(isAdd ? products.get(position).getQuantity() + 1 : products.get(position).getQuantity() - 1);
                                    float newPrice = isAdd ? order.getPrice() + products.get(position).getPrice() : order.getPrice()  - products.get(position).getPrice();
                                    mTotalPrice.setText(String.valueOf(newPrice));
                                    order.setPrice(newPrice);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            mRecyclerView.setAdapter(adapter);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {

            }
        });


        return view;
    }

    @OnClick(R.id.change_order_image)
    void changeOrder() {
        isNeedToChange = !isNeedToChange;

        if (!isNeedToChange) {
            VerifyOrderRequest verifyOrderRequest = new VerifyOrderRequest();

            verifyOrderRequest.setOrder(order);
            verifyOrderRequest.setToken(token);

            App.getApi().verifyOrder(verifyOrderRequest).enqueue(new Callback<OrderDetails>() {
                @Override
                public void onResponse(Call<OrderDetails> call, final Response<OrderDetails> response) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );

                            if (response.isSuccessful()) {

                                order = response.body();

                                android.text.format.DateFormat df = new android.text.format.DateFormat();

                                mDateTime.setText(df.format("dd.MM.yyyy HH:mm", order.getDeliveryTime()));
                                mName.setText(order.getCustomer().getName());
                                mOrderNumber.setText("№" + String.valueOf(order.getId()));
                                mOrderStatus.setText(order.getOrderStatus().toString());
                                mPhone.setText(order.getCustomer().getPhoneNumber());
                                mAddressValue.setText(order.getAddress().getStreet());

                                products = order.getOrderProducts();

                                mTotalPrice.setText(String.valueOf(order.getPrice()));

                                ((OrderDetailsAdapter)mRecyclerView.getAdapter()).UpdateItems(products);

                                adapter.setNeedToChange(isNeedToChange);
                                mChangeImage.setImageDrawable(isNeedToChange ? ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.ic_check) : ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.ic_delete_sweep));
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Call<OrderDetails> call, Throwable t) {

                }
            });
        } else {
            adapter.setNeedToChange(isNeedToChange);
            adapter.notifyDataSetChanged();
            mChangeImage.setImageDrawable(isNeedToChange ? ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.ic_check) : ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.ic_delete_sweep));
        }
    }

    @OnClick(R.id.order_button)
    void orderButtonClick() {
        final OrderStatus newStatus = OrderStatus.fromId(order.getOrderStatus().id() + 1);

        if (newStatus.id() == 3)
            return;

        String title;
        String message;
        String buttonYes = getResources().getString(R.string.yes);
        String buttonNo = getResources().getString(R.string.no);

        title = getResources().getString(newStatus == OrderStatus.InProcess ? R.string.start : R.string.finish);
        message = getResources().getString(newStatus == OrderStatus.InProcess ? R.string.start_message : R.string.finish_message);

        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

                UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();

                updateOrderStatusRequest.setNewStatus(newStatus);
                updateOrderStatusRequest.setOrderID(order.getId());
                updateOrderStatusRequest.setToken(token);

                App.getApi().updateOrderStatus(updateOrderStatusRequest).enqueue(new Callback<BoolResponse>() {
                    @Override
                    public void onResponse(Call<BoolResponse> call, final Response<BoolResponse> response) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful()) {

                                    order.setOrderStatus(newStatus);
                                    mOrderStatus.setText(order.getOrderStatus().toString());

                                    updateOrderStatus(newStatus);

                                    ((MainActivity)getActivity()).updateStatusPreview(order);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<BoolResponse> call, Throwable t) {

                    }
                });
            }
        });
        ad.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });
        ad.setCancelable(true);
        ad.show();
    }

    private void updateOrderStatus(OrderStatus orderStatus) {
        switch (orderStatus)
        {
            case Completed:
                mOrderButton.setText(R.string.done);

                mOrderButton.setBackgroundColor(getResources().getColor(R.color.colorSecondaryText));
                mOrderButton.setClickable(false);
                mChangeImage.setClickable(false);
                break;
            case InProcess:
                mOrderButton.setText(getResources().getString(R.string.finish));
                break;
        }
    }
}
