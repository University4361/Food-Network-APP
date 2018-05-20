package com.example.julia.delivery.api.models;

import com.example.julia.delivery.objects.OrderStatus;

/**
 * Created by Lavrov on 19.05.2018.
 */

public class UpdateOrderStatusRequest extends BaseRequest {

    private int orderID;
    private OrderStatus newStatus;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public OrderStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(OrderStatus newStatus) {
        this.newStatus = newStatus;
    }
}
