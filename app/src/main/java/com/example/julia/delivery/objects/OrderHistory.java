package com.example.julia.delivery.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Lavrov on 20.05.2018.
 */

public class OrderHistory {
    @SerializedName("historyDate")
    @Expose
    private Date historyDate;

    @SerializedName("numberOfCompletedOrders")
    @Expose
    private int numberOfCompletedOrders;

    @SerializedName("numberOfCanceledOrders")
    @Expose
    private int numberOfCanceledOrders;

    @SerializedName("dateProfit")
    @Expose
    private double dateProfit ;

    @SerializedName("distance")
    @Expose
    private double distance;

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    public int getNumberOfCompletedOrders() {
        return numberOfCompletedOrders;
    }

    public void setNumberOfCompletedOrders(int numberOfCompletedOrders) {
        this.numberOfCompletedOrders = numberOfCompletedOrders;
    }

    public int getNumberOfCanceledOrders() {
        return numberOfCanceledOrders;
    }

    public void setNumberOfCanceledOrders(int numberOfCanceledOrders) {
        this.numberOfCanceledOrders = numberOfCanceledOrders;
    }

    public double getDateProfit() {
        return dateProfit;
    }

    public void setDateProfit(double dateProfit) {
        this.dateProfit = dateProfit;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
