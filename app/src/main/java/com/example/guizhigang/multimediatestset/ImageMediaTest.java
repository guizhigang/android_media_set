package com.example.guizhigang.multimediatestset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageMediaTest extends AppCompatActivity {
    private int CAPTURE_ACTION_RESULT = 0;
    private String Tag = "ImageMediaTest";
    @Bind(R.id.image_view)
    ImageView iamgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_media_test);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAPTURE_ACTION_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            final Bitmap bitmap = (Bitmap)bundle.get("data");
            iamgeView.setImageBitmap(bitmap);
            Log.d(Tag, "bitmap width:" + bitmap.getWidth() + " height:" + bitmap.getHeight());
            final Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    Log.d(Tag, "handleMessage msg.what:" + msg.what);
                    bitmap.recycle();
                    return true;
                }
            });
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.sendMessage(handler.obtainMessage(100));
                }
            },1000);
        }
    }
}
