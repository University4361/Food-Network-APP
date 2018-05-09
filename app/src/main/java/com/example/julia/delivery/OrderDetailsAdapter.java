package com.example.julia.delivery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julia.delivery.objects.Product;

import java.util.List;


class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderViewHolder> {

    private List<Product> products;
    private boolean isNeedToChange;
    private OnChangeProductsListener listener;

    OrderDetailsAdapter(@NonNull List<Product> products, @NonNull OnChangeProductsListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_details_item, parent, false);
        return new OrderViewHolder(v, products);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.name.setText(products.get(position).getName());
        int amount = products.get(position).getAmount();
        holder.amount.setText(String.valueOf(amount));
        holder.price.setText(String.valueOf(products.get(position).getPrice() * amount));
        holder.plus.setVisibility(isNeedToChange ? View.VISIBLE : View.GONE);
        holder.minus.setVisibility(isNeedToChange ? View.VISIBLE : View.GONE);
        holder.bind(position, listener);
    }

    void setNeedToChange(boolean isNeedToChange) {
        this.isNeedToChange = isNeedToChange;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView amount;
        TextView price;
        ImageView plus;
        ImageView minus;
        List<Product> products;

        OrderViewHolder(View itemView, List<Product> products) {
            super(itemView);
            name = itemView.findViewById(R.id.name_value);
            amount = itemView.findViewById(R.id.amount_value);
            price = itemView.findViewById(R.id.price_value);
            plus = itemView.findViewById(R.id.plus_image);
            minus = itemView.findViewById(R.id.minus_image);
            this.products = products;
        }

        void bind(final int position, final OnChangeProductsListener listener) {
            plus.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(position,true);
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if(products.get(position).getAmount() != 0) {
                        listener.onClick(position, false);
                    }
                }
            });
        }
    }
}
