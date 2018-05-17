package com.example.julia.delivery.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Lavrov on 18.05.2018.
 */

public class OrderPreview {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("deliveryTime")
    @Expose
    private Date deliveryTime ;

    @SerializedName("price")
    @Expose
    private float price ;

    @SerializedName("orderStatus")
    @Expose
    private int orderStatus;

    @SerializedName("orderPaymentStatus")
    @Expose
    private int orderPaymentStatus;

    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("customer")
    @Expose
    private Customer customer ;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PaymentStatus getOrderPaymentStatus() {
        return PaymentStatus.fromId(orderPaymentStatus);
    }

    public void setOrderPaymentStatus(PaymentStatus orderPaymentStatus) {
        this.orderPaymentStatus = orderPaymentStatus.id();
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.fromId(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus.id();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
