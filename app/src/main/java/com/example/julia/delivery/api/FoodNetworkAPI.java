package com.example.julia.delivery.api;

import com.example.julia.delivery.api.models.AuthRequest;
import com.example.julia.delivery.api.models.AuthResponse;
import com.example.julia.delivery.api.models.BoolResponse;
import com.example.julia.delivery.api.models.GetOrderRequest;
import com.example.julia.delivery.api.models.GetOrdersHistoryRequest;
import com.example.julia.delivery.api.models.GetOrdersRequest;
import com.example.julia.delivery.api.models.UpdateOrderStatusRequest;
import com.example.julia.delivery.api.models.VerifyOrderRequest;
import com.example.julia.delivery.objects.OrderDetails;
import com.example.julia.delivery.objects.OrderHistory;
import com.example.julia.delivery.objects.OrderPreview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Lavrov on 16.05.2018.
 */

public interface FoodNetworkAPI {
    @POST("api/Login")
    Call<AuthResponse> loginCourier(@Body AuthRequest request);
    @POST("api/Orders")
    Call<List<OrderPreview>> getOrders(@Body GetOrdersRequest request);
    @POST("api/Orders/GetOrder")
    Call<OrderDetails> getOrder(@Body GetOrderRequest request);
    @POST("api/Orders/VerifyOrder")
    Call<OrderDetails> verifyOrder(@Body VerifyOrderRequest request);
    @POST("api/Orders/UpdateStatus")
    Call<BoolResponse> updateOrderStatus(@Body UpdateOrderStatusRequest request);
    @POST("api/Orders/GetOrdersHistory")
    Call<List<OrderHistory>> getOrdersHistory(@Body GetOrdersHistoryRequest request);
}
