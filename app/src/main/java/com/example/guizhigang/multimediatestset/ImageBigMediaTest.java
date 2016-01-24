package com.example.guizhigang.multimediatestset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
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


public class ImageBigMediaTest extends AppCompatActivity {
    private int CAPTURE_ACTION_RESULT = 0;
    private String Tag = "ImageBigMediaTest";
    @Bind(R.id.image_view_default)
    ImageView imageView;

//    @Bind(R.id.image_view_2)
//    ImageView imageView_2;
//
//    @Bind(R.id.image_view_4)
//    ImageView imageView_4;
//
//    @Bind(R.id.image_view_8)
//    ImageView imageView_8;

    private String fileImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image_media_test);
        Log.d(Tag, "onCreate");
        ButterKnife.bind(this);
        fileImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "test.jpg";
        File file = new File(fileImagePath);
        Uri url = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, url);
        startActivityForResult(intent, CAPTURE_ACTION_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
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

//            options.inSampleSize = sampleSize * 2;
//            bmp = BitmapFactory.decodeFile(fileImagePath, options);
//            imageView_2.setImageBitmap(bmp);
//
//            options.inSampleSize = sampleSize * 4;
//            bmp = BitmapFactory.decodeFile(fileImagePath, options);
//            imageView_4.setImageBitmap(bmp);
//
//            options.inSampleSize = sampleSize * 8;
//            bmp = BitmapFactory.decodeFile(fileImagePath, options);
//            imageView_8.setImageBitmap(bmp);
        }
    }
}
