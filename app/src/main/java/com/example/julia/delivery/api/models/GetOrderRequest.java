package com.example.julia.delivery.api.models;

/**
 * Created by Lavrov on 18.05.2018.
 */

public class GetOrderRequest extends BaseRequest {
    private int orderID;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}
