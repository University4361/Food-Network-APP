package com.example.julia.delivery.mainscreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.julia.delivery.R;
import com.example.julia.delivery.objects.Order;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private OnCustomClickListener listener;
    OrderAdapter(@NonNull List<Order> orders, @NonNull OnCustomClickListener listener){
        this.orders = orders;
        this.listener = listener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_item, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.dateTime.setText(orders.get(position).getDate());
        holder.address.setText(orders.get(position).getAddress());
        holder.bind(position, listener);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout orderLayout;
        TextView dateTime;
        TextView address;

        OrderViewHolder(View itemView) {
            super(itemView);
            dateTime = itemView.findViewById(R.id.date_time_value);
            address = itemView.findViewById(R.id.address_value);
            orderLayout = itemView.findViewById(R.id.order_layout);
        }

        void bind(final int position, final OnCustomClickListener listener) {
            orderLayout.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(position);
                }
            });
        }
    }
}
