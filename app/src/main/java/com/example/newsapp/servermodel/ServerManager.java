package com.example.newsapp.servermodel;

import android.content.Context;
import android.widget.Toast;


import com.example.newsapp.model.ApiClient;
import com.example.newsapp.model.DateTimeManager;
import com.example.newsapp.model.News;
import com.example.newsapp.model.RetrofitApi;
import com.example.newsapp.model.UpdateHeadLine;
import com.example.newsapp.model.UpdateNews;
import com.example.newsapp.model.UpdateNewsPojo;
import com.example.newsapp.model.UpdateTechCrunch;
import com.example.newsapp.model.UpdateWallStreetJournal;
import com.example.newsapp.model.eventbus.EventBusPojo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerManager {
    public static final String TAG = "ServerManager";
    private Context mContext;
    private RetrofitApi retrofitApi;
    private UpdateNews updateNews = null;

    public ServerManager(Context mContext){
        this.mContext = mContext;
        retrofitApi = ApiClient.getClient().create(RetrofitApi.class);
    }
    public void fetchDataFromServer(){
        DateTimeManager dateTimeManager = new DateTimeManager(mContext);
        String date = dateTimeManager.getPrevMonthDate();
        Call<UpdateNews> call = retrofitApi.getNews("bitcoin", date,"publishedAt","35de1cffa06a48548d318541b675c79a");
        call.enqueue(new Callback<UpdateNews>() {
            @Override
            public void onResponse(Call<UpdateNews> call, Response<UpdateNews> response) {
                updateNews = response.body();
                if (updateNews != null){
                    List<News> newsList = updateNews.getArticles();
                    EventBusPojo eventBusPojo = new EventBusPojo(newsList);
                    EventBus.getDefault().post(eventBusPojo);
                }
            }

            @Override
            public void onFailure(Call<UpdateNews> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void fetchDataforUpdateHeadline(){
        Call<UpdateHeadLine> call = retrofitApi.getHeadLines("us", "business","35de1cffa06a48548d318541b675c79a");
        call.enqueue(new Callback<UpdateHeadLine>() {
            @Override
            public void onResponse(Call<UpdateHeadLine> call, Response<UpdateHeadLine> response) {
                UpdateHeadLine updateHeadLine = response.body();
                if (updateHeadLine != null){
                    List<News> newsList = updateHeadLine.getArticles();
                    EventBusPojo eventBusPojo = new EventBusPojo(newsList);
                    EventBus.getDefault().post(eventBusPojo);
                }


            }

            @Override
            public void onFailure(Call<UpdateHeadLine> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchDataforUpdateNewsPojo(){
        DateTimeManager dateTimeManager = new DateTimeManager(mContext);
        String prevDate = dateTimeManager.getPrevdatDate();
        Call<UpdateNewsPojo> call = retrofitApi.getNewsPojo("apple", prevDate,prevDate,"popularity","35de1cffa06a48548d318541b675c79a");
        call.enqueue(new Callback<UpdateNewsPojo>() {
            @Override
            public void onResponse(Call<UpdateNewsPojo> call, Response<UpdateNewsPojo> response) {
                UpdateNewsPojo  updateNewsPojo = response.body();
                if (updateNewsPojo != null){
                    List<News> newsList = updateNewsPojo.getArticles();
                    EventBusPojo eventBusPojo = new EventBusPojo(newsList);
                    EventBus.getDefault().post(eventBusPojo);
                }
            }

            @Override
            public void onFailure(Call<UpdateNewsPojo> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchDataForUpdateTechCrunch(){
        Call<UpdateTechCrunch> call = retrofitApi.getTechCrunch("techcrunch", "35de1cffa06a48548d318541b675c79a");
        call.enqueue(new Callback<UpdateTechCrunch>() {
            @Override
            public void onResponse(Call<UpdateTechCrunch> call, Response<UpdateTechCrunch> response) {
                UpdateTechCrunch  updateTechCrunch = response.body();
                if (updateTechCrunch != null){
                    List<News> newsList = updateTechCrunch.getArticles();
                    EventBusPojo eventBusPojo = new EventBusPojo(newsList);
                    EventBus.getDefault().post(eventBusPojo);
                }

            }

            @Override
            public void onFailure(Call<UpdateTechCrunch> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fetchDataForUpdateWallStreetJournal(){
        Call<UpdateWallStreetJournal> call = retrofitApi.getWallStreetJournal("wsj.com", "35de1cffa06a48548d318541b675c79a");
        call.enqueue(new Callback<UpdateWallStreetJournal>() {
            @Override
            public void onResponse(Call<UpdateWallStreetJournal> call, Response<UpdateWallStreetJournal> response) {
                UpdateWallStreetJournal  updateWallStreetJournal = response.body();
                if (updateWallStreetJournal != null){
                    List<News> newsList = updateWallStreetJournal.getArticles();
                    EventBusPojo eventBusPojo = new EventBusPojo(newsList);
                    EventBus.getDefault().post(eventBusPojo);
                }

            }

            @Override
            public void onFailure(Call<UpdateWallStreetJournal> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
