package com.geektech.lovecalculator5_2;

import android.app.Application;

import com.geektech.lovecalculator5_2.network.LoveApi;
import com.geektech.lovecalculator5_2.network.RetrofitService;

public class App extends Application {
    public static LoveApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitService retrofitService = new RetrofitService();
        api = retrofitService.getLoveApi();
    }
}
