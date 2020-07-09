package com.example.newsapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.adapter.AdapterForNewsOne;
import com.example.newsapp.contexttag.Tag;
import com.example.newsapp.model.News;
import com.example.newsapp.model.PassData;
import com.example.newsapp.model.eventbus.EventBusPojo;
import com.example.newsapp.servermodel.ServerManager;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsOneActivity extends AppCompatActivity {

    @BindView(R.id.recyclerForNewsOne)
    RecyclerView recyclerView;

    @BindView(R.id.mainLayout)
    LinearLayout linearLayout;

    private RecyclerView.Adapter mAdapter;
    private Intent startingIntent;

    private ArrayList<String> mDataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_one);
        ButterKnife.bind(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
         ServerManager serverManager = new ServerManager(NewsOneActivity.this);
        startingIntent = getIntent();
        int whatYouSent = startingIntent.getIntExtra(Tag.ADAPTERPOSITION,0);
        PassData passData = new PassData();
        passData.PassDataToActivity(whatYouSent, serverManager);
        Snackbar snackbar;
        snackbar = Snackbar.make(linearLayout, "Double click to view news", Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        snackbar.show();

        //I changed the code 
   }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusPojo(EventBusPojo eventBusPojo) {
        List<News> newsList = eventBusPojo.getNewsList();
        if (newsList != null){
            mAdapter = new AdapterForNewsOne(NewsOneActivity.this, newsList);
            recyclerView.setAdapter(mAdapter);
        }

    };

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
