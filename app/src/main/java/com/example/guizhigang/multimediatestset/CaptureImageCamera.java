package com.example.guizhigang.multimediatestset;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CaptureImageCamera extends AppCompatActivity implements SurfaceHolder.Callback,Camera.PictureCallback {
    private int CAPTURE_ACTION_RESULT = 0;
    private String Tag = "CaptureImageCamera";
    @Bind(R.id.id_camera_view)
    SurfaceView cameraView;

    private SurfaceHolder surfaceHolder;
    private Camera camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        Log.d(Tag, "onCreate");
        ButterKnife.bind(this);

        surfaceHolder = cameraView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);

        cameraView.setFocusable(true);
        cameraView.setFocusableInTouchMode(true);
        cameraView.setClickable(true);
        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null,null,CaptureImageCamera.this);
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(surfaceHolder);
            Camera.Parameters parameters = camera.getParameters();
            if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
                parameters.set("orientation", "portrait");//PORTRAIT
                //android 2.2 uper
                camera.setDisplayOrientation(90);
                parameters.setRotation(90);
            }
            camera.setParameters(parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(Tag,"surfaceChanged");
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(Tag,"surfaceDestroyed");
        camera.stopPreview();
        camera.release();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d(Tag,"onPictureTaken");
        ContentResolver contentResolver = getContentResolver();
        Log.d(Tag,"contentResolver:" + contentResolver);
        Uri imageFileUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Log.d(Tag,"imageFileUri:" + imageFileUri);
        try {
            OutputStream imageFileOs = contentResolver.openOutputStream(imageFileUri);
            imageFileOs.write(data);
            imageFileOs.flush();
            imageFileOs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }
}
