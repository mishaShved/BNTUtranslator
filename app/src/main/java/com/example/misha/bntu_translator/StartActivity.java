package com.example.misha.bntu_translator;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import updatedb.ThreadUpdaterDB;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        Intent intent = new Intent(this, TranslateActivity.class);
        startActivity(intent);
        finish();
    }
}
