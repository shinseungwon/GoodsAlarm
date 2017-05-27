package com.example.ssw90.goodsalarm;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ssw90 on 2016-11-21.
 */

public class ApplicationClass extends Application {
    static ArrayList<Product> products;
    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("onc","ee");

    }


}
