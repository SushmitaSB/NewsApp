package com.example.newsapp.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConnection {
    private Context context;
    private boolean isConnected = false;
    public CheckInternetConnection(Context context){
        this.context = context;
    }

    public  boolean hasConnection(){

        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        if (null != info){
            if (info.getType() == manager.TYPE_WIFI){
                isConnected = true;
            }else if(info.getType() == manager.TYPE_MOBILE){
                isConnected = true;
            }
        }else {
            isConnected = false;
        }


        return isConnected;
    }
}
