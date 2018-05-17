package com.example.julia.delivery.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lavrov on 18.05.2018.
 */

public class OrderDetails extends OrderPreview {
    @SerializedName("orderProducts")
    @Expose
    private List<Product> orderProducts;

    public List<Product> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<Product> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
