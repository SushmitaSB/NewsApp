package com.example.newsapp.model.eventbus;

import com.example.newsapp.model.News;

import java.util.List;

public class EventBusPojo {
    List<News> newsList;

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public EventBusPojo(List<News> newsList){
        this.newsList = newsList;
    }
}
