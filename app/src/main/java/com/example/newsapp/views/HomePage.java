package com.example.newsapp.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.newsapp.R;
import com.example.newsapp.adapter.AdapterForHomePage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePage extends AppCompatActivity {

    @BindView(R.id.recyclerId)
    RecyclerView recyclerView;
    private ArrayList<String> arrayList;
    private AdapterForHomePage mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();
        arrayList.add("All articles about Bitcoin from the last month, sorted by recent first");
        arrayList.add("Top business headlines in the US right now");
        arrayList.add("All articles mentioning Apple from yesterday, sorted by popular publishers first");
        arrayList.add("Top headlines from TechCrunch right now");
        arrayList.add("All articles published by the Wall Street Journal in the last 6 months, sorted by recent first");


        mAdapter = new AdapterForHomePage(this, arrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
