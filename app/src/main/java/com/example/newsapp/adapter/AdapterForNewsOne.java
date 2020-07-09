package com.example.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.newsapp.R;
import com.example.newsapp.contexttag.Tag;
import com.example.newsapp.model.CheckInternetConnection;
import com.example.newsapp.model.News;
import com.example.newsapp.views.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterForNewsOne extends RecyclerSwipeAdapter <AdapterForNewsOne.SimpleViewHolder> {

    private Context context;
    private List<News> arrayList;
    private CheckInternetConnection checkInternetConnection;
    public AdapterForNewsOne(Context context, List<News> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        checkInternetConnection = new CheckInternetConnection(context);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_news_one, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int i) {


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                if (checkInternetConnection.hasConnection()) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(Tag.WEBVIEW, arrayList.get(i).getUrl());
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternetConnection.hasConnection()) {
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    arrayList.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, arrayList.size());
                    mItemManger.closeAllItems();
                    Toast.makeText(view.getContext(), "Deleted " + viewHolder.textViewData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.imageViewSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, arrayList.get(i).getUrl());

                context.startActivity(Intent.createChooser(share, arrayList.get(i).getUrl()));
            }
        });
        viewHolder.textViewPos.setText(arrayList.get(i).getTitle());
        viewHolder.textViewAuthor.setText(arrayList.get(i).getAuthor());
        viewHolder.textViewData.setText(arrayList.get(i).getPublishedAt());
        viewHolder.textViewDescription.setText(arrayList.get(i).getDescription());

        try {

            Picasso.get()
                    .load(arrayList.get(i).getUrlToImage())
                    .resize(60,60)
                    .centerCrop()
                    .into(viewHolder.imageUrl);


        }catch (Exception ex){

            ex.getMessage();
            ex.getStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return  R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView textViewPos;
        TextView textViewData;
        TextView textViewDescription;
        TextView textViewAuthor;
        ImageView imageViewSwipe;
        ImageView imageUrl;
        ImageView buttonDelete;
        ImageView share;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout =  itemView.findViewById(R.id.swipe);
            textViewPos =  itemView.findViewById(R.id.position);
            textViewData = itemView.findViewById(R.id.text_data);
            buttonDelete = itemView.findViewById(R.id.delete);
            textViewAuthor = itemView.findViewById(R.id.authorId);
            textViewDescription = itemView.findViewById(R.id.descriptionId);
            imageViewSwipe = itemView.findViewById(R.id.swipeArrowId);
            imageUrl = itemView.findViewById(R.id.newsImageId);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
                    Toast.makeText(view.getContext(), "onItemSelected: " + textViewData.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
