package com.example.newsapp.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public  SharedPreferenceConfig (Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);
    }

    public void LoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login_status", status);
        editor.commit();
    }
    public boolean read_login_status(){
        boolean status = false;
        status = sharedPreferences.getBoolean("login_status",false);
        return status;
    }

    public void SignInStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("signin_status", status);
        editor.commit();
    }

    public boolean read_signin_status(){
        boolean status = false;
        status = sharedPreferences.getBoolean("signin_status",false);
        return status;
    }
}
