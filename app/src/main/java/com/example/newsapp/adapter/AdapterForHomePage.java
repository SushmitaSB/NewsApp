package com.example.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.newsapp.R;
import com.example.newsapp.contexttag.Tag;
import com.example.newsapp.model.CheckInternetConnection;
import com.example.newsapp.views.NewsOneActivity;

import java.util.ArrayList;

public class AdapterForHomePage extends RecyclerView.Adapter <AdapterForHomePage.MyViewHolder>{

    Context context;
    ArrayList<String> arrayList;
    private CheckInternetConnection checkInternetConnection;

    public AdapterForHomePage(Context context, ArrayList<String> arrayList){
        this.arrayList = arrayList;
        this.context = context;
        checkInternetConnection = new CheckInternetConnection(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_layout_for_home_page_recycler, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {

        viewHolder.textView.setText(arrayList.get(i));

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternetConnection.hasConnection()) {
                    Intent intent = new Intent(context, NewsOneActivity.class);
                    intent.putExtra(Tag.ADAPTERPOSITION, i);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textViewId);
        }

    }
}
