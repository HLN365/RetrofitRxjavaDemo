package com.hl.retrofitrxjavademo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hl.retrofitrxjavademo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView gank = (TextView) findViewById(R.id.gank);
        TextView movie = (TextView) findViewById(R.id.movie);

        gank.setOnClickListener(this);
        movie.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gank:
                startActivity(new Intent(this,GankActivity.class));
                break;
            case R.id.movie:
                startActivity(new Intent(this,MovieActivity.class));
                break;
        }
    }
}
