package com.example.julia.delivery.mainscreen;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.julia.delivery.R;
import com.example.julia.delivery.objects.OrderHistory;
import com.example.julia.delivery.objects.OrderPreview;
import com.example.julia.delivery.objects.OrderStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lavrov on 20.05.2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<OrderHistory> orders;
    private Context context;

    HistoryAdapter(@NonNull List<OrderHistory> orders, Context context){
        this.orders = orders;
        this.context = context;
    }

    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history_item, parent, false);
        return new HistoryAdapter.HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.HistoryViewHolder holder, int position) {

        android.text.format.DateFormat df = new android.text.format.DateFormat();

        OrderHistory currentItem = orders.get(position);

        holder.date.setText("Дата: " + df.format("dd.MM.yyyy", currentItem.getHistoryDate()));
        holder.completed.setText("Выполнено заказов: " +  String.valueOf(currentItem.getNumberOfCompletedOrders()));
        holder.canceled.setText("Отменено заказов: " +  String.valueOf(currentItem.getNumberOfCanceledOrders()));
        holder.distance.setText("Расстояние: " +  String.valueOf(currentItem.getDistance()));
        holder.profit.setText("Заработано: " + String.valueOf(currentItem.getDateProfit()));

        holder.bind(position, new HistoryOnClickListener() {
            @Override
            public void onClick(int position, View view) {

                LinearLayout parent = (LinearLayout) view.getParent();

                RelativeLayout infoLayout = parent.findViewById(R.id.info_layout);

                if (infoLayout.getVisibility() == View.VISIBLE) {
                    infoLayout.setVisibility(View.GONE);
                }
                else{
                    infoLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout dateLayout;
        TextView date;
        TextView completed;
        TextView canceled;
        TextView distance;
        TextView profit;

        HistoryViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.order_date);
            completed = itemView.findViewById(R.id.completed_value);
            canceled = itemView.findViewById(R.id.canceled_value);
            dateLayout = itemView.findViewById(R.id.date_layout);
            distance = itemView.findViewById(R.id.distance_value);
            profit = itemView.findViewById(R.id.profit_value);
        }

        void bind(final int position, final HistoryOnClickListener listener) {
            dateLayout.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(position, v);
                }
            });
        }
    }


    interface HistoryOnClickListener {
        void onClick(int position, View view);
    }
}
