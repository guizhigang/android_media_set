package com.example.guizhigang.multimediatestset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;


public class WelcomActivity extends AppCompatActivity {
    private int CAPTURE_ACTION_RESULT = 0;
    private String Tag = "WelcomActivity";
    @Bind(R.id.welcom_image)
    ImageView imageView;

    private String fileImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_welcom);
        Log.d(Tag, "onCreate");
        ButterKnife.bind(this);
        fileImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM/Camera/20151007_155244.jpg";
        Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        int screenWidth = p.x;
        int screenHeight = p.y;
        Log.d(Tag, "screenWidth " + screenWidth + " screenHeight" + screenHeight);

        BitmapFactory.Options options = new BitmapFactory.Options();
        //只加载图片的尺寸
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(fileImagePath, options);

        int sampleSize = BitmapHelper.computeSampleSize(options, -1, screenWidth * screenHeight);
        //真正解码
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(fileImagePath, options);
        imageView.setImageBitmap(bmp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bitmap bm = ((BitmapDrawable) (imageView.getDrawable())).getBitmap();
        if(bm != null && !bm.isRecycled()){
            imageView.setImageBitmap(null);
            bm.recycle();
            bm = null;
        }
    }
}
