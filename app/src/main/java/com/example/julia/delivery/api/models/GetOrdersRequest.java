package com.example.julia.delivery.api.models;

import java.util.Date;

/**
 * Created by Lavrov on 18.05.2018.
 */

public class GetOrdersRequest extends BaseRequest {
    private Date ordersDate;

    public Date getOrdersDate() {
        return ordersDate;
    }

    public void setOrdersDate(Date ordersDate) {
        this.ordersDate = ordersDate;
    }
}
