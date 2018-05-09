package com.example.julia.delivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julia.delivery.objects.Order;
import com.example.julia.delivery.objects.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderFragment extends Fragment {

    @BindView(R.id.order_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.change_order_image)
    ImageView mChangeImage;
    @BindView(R.id.price_value)
    TextView mTotalPrice;
    @BindView(R.id.order_button)
    Button mOrderButton;
    private OrderDetailsAdapter adapter;
    private boolean isNeedToChange;
    private List<Product> products;
    Order order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);
        order = new Order();
        products = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Product product = new Product();
            product.setName("Пицца Цезарь");
            product.setAmount(1);
            product.setPrice(100);
            products.add(product);
        }
        order.setProducts(products);
        order.setPrice(1000.0);
        adapter = new OrderDetailsAdapter(products, new OnChangeProductsListener() {
            @Override
            public void onClick(int position, boolean isAdd) {
                products.get(position).setAmount(isAdd ? products.get(position).getAmount() + 1 : products.get(position).getAmount() - 1);
                Double newPrice = isAdd ? order.getPrice() + products.get(position).getPrice() : order.getPrice() - products.get(position).getPrice();
                mTotalPrice.setText(String.valueOf(newPrice));
                order.setPrice(newPrice);
                adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    @OnClick(R.id.change_order_image)
    void changeOrder() {
        isNeedToChange = !isNeedToChange;
        adapter.setNeedToChange(isNeedToChange);
        adapter.notifyDataSetChanged();
        mChangeImage.setImageDrawable(isNeedToChange ? ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.ic_check) : ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.ic_delete_sweep));
    }

    @OnClick(R.id.order_button)
    void orderButtonClick() {
        String title;
        String message;
        String buttonYes = getResources().getString(R.string.yes);
        String buttonNo = getResources().getString(R.string.no);
        String orderButtonText = mOrderButton.getText().toString();
        final boolean isStartOrder = orderButtonText.equals(getResources().getString(R.string.start));

        title = getResources().getString(isStartOrder ? R.string.start : R.string.finish);
        message = getResources().getString(isStartOrder ? R.string.start_message : R.string.finish_message);

        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                mOrderButton.setText(getResources().getString(isStartOrder ? R.string.finish : R.string.done));
                if(!isStartOrder){
                    mOrderButton.setBackgroundColor(getResources().getColor(R.color.colorSecondaryText));
                    mOrderButton.setClickable(false);
                    mOrderButton.setFocusable(false);
                    mChangeImage.setClickable(false);
                    mChangeImage.setFocusable(false);
                }
            }
        });
        ad.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });
        ad.setCancelable(true);
        ad.show();
    }
}
