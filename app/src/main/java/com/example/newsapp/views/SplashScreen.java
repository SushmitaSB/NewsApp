package com.example.newsapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends AppCompatActivity {


    @BindView(R.id.nameId)
    TextView textView;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ButterKnife.bind(this);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent newIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(newIntent);
                finish();
            }
        },3000);

        textView.setText("A good newspaper is a nation talking to itself.");
        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale);
        a.reset();
        textView.clearAnimation();
        textView.startAnimation(a);
    }
}
