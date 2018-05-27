package com.example.julia.delivery.mainscreen;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.julia.delivery.R;
import com.example.julia.delivery.objects.Order;
import com.example.julia.delivery.objects.OrderPreview;
import com.example.julia.delivery.objects.OrderStatus;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderPreview> orders;
    private OnCustomClickListener listener;
    private Context context;

    OrderAdapter(@NonNull List<OrderPreview> orders, @NonNull OnCustomClickListener listener, Context context){
        SortAndSetupItems(orders);
        this.listener = listener;
        this.context = context;
    }

    public void UpdateAndSetupItems(OrderPreview preview) {
        int i = 0;

        for (i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == preview.getId()) {
                break;
            }
        }

        orders.remove(i);
        orders.add(i, preview);

        SortAndSetupItems(orders);
    }

    public OrderPreview getItem(int position)
    {
        return orders.get(position);
    }


    public void SortAndSetupItems(List<OrderPreview> orders)
    {
        List<OrderPreview> result = new ArrayList<OrderPreview>();

        for (OrderPreview preview: orders) {
            if (preview.getOrderStatus() == OrderStatus.InProcess){
                result.add(preview);
            }
        }

        for (OrderPreview preview: orders) {
            if (preview.getOrderStatus() == OrderStatus.New){
                result.add(preview);
            }
        }

        for (OrderPreview preview: orders) {
            if (preview.getOrderStatus() == OrderStatus.Completed){
                result.add(preview);
            }
        }

        for (OrderPreview preview: orders) {
            if (preview.getOrderStatus() == OrderStatus.Canceled){
                result.add(preview);
            }
        }

        this.orders = result;
        notifyDataSetChanged();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_item, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

        android.text.format.DateFormat df = new android.text.format.DateFormat();

        String address = orders.get(position).getAddress().getCity() + ", " + orders.get(position).getAddress().getStreet() + ", " + orders.get(position).getAddress().getHomeNumber();

        holder.date.setText("Дата: " + df.format("dd.MM.yyyy", orders.get(position).getDeliveryTime()));
        holder.time.setText("Время: " +  df.format("HH:mm", orders.get(position).getDeliveryTime()));
        holder.address.setText(address);

        switch (orders.get(position).getOrderStatus()){
            case New:
                holder.orderLayout.setBackgroundResource(R.drawable.view_rectangle_gray);
                holder.imageView.setImageBitmap(null);
                break;
            case Completed:
                holder.orderLayout.setBackgroundResource(R.drawable.view_rectangle_primary_light);
                holder.imageView.setImageResource(R.drawable.ic_check_circle_outline);
                holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                break;
            case InProcess:
                holder.orderLayout.setBackgroundResource(R.drawable.view_rectangle_green);
                holder.imageView.setImageBitmap(null);
                break;
            case Canceled:
                holder.orderLayout.setBackgroundResource(R.drawable.view_rectangle_red);
                holder.imageView.setImageResource(R.drawable.ic_close);
                holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorRed), PorterDuff.Mode.SRC_IN);
                break;
        }

        holder.bind(position, listener);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout orderLayout;
        TextView date;
        TextView time;
        TextView address;
        ImageView imageView;

        OrderViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_value);
            time = itemView.findViewById(R.id.time_value);
            address = itemView.findViewById(R.id.address_value);
            orderLayout = itemView.findViewById(R.id.order_layout);
            imageView = itemView.findViewById(R.id.status_value);
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
