package com.example.julia.delivery;

import android.app.Application;

import com.example.julia.delivery.api.FoodNetworkAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lavrov on 16.05.2018.
 */

public class App extends Application {
    private static FoodNetworkAPI foodNetworkAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://newfnapi.azurewebsites.net") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create(gson)) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        foodNetworkAPI = retrofit.create(FoodNetworkAPI.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static FoodNetworkAPI getApi() {
        return foodNetworkAPI;
    }
}
