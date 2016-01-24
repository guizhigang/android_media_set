package com.example.guizhigang.multimediatestset;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @OnClick(R.id.id_image_media_ACTION_IMAGE_CAPTURE)
    public void onClickImageMedia(View v){
        Intent intent = new Intent(this,ImageMediaTest.class);
        startActivity(intent);
    }

    @OnClick(R.id.id_big_image_media_ACTION_IMAGE_CAPTURE)
    public void onClickBigImageMedia(View v){
        Intent intent = new Intent(this,ImageBigMediaTest.class);
        startActivity(intent);
    }

    @OnClick(R.id.id_show_welcom)
    public void onClickShowWelcom(View v){
        Intent intent = new Intent(this,WelcomActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.capture_image_camera)
    public void onClickCaptureImageCamera(View v){
        Intent intent = new Intent(this,CaptureImageCamera.class);
        startActivity(intent);
    }

    @OnClick(R.id.capture_video_media_recoder)
    public void onClickCaptureVideoCamera(View v){
        Intent intent = new Intent(this,CaptureVideoCamera.class);
        startActivity(intent);
    }
}
