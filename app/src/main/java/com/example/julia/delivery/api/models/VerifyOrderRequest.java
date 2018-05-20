package com.example.julia.delivery.api.models;

import com.example.julia.delivery.objects.OrderDetails;

/**
 * Created by Lavrov on 19.05.2018.
 */

public class VerifyOrderRequest extends BaseRequest {
    private OrderDetails order;

    public OrderDetails getOrder() {
        return order;
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }
}
