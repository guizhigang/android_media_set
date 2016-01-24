package com.example.guizhigang.multimediatestset;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CaptureVideoCamera extends AppCompatActivity implements SurfaceHolder.Callback{
    private int CAPTURE_ACTION_RESULT = 0;
    private String Tag = "CaptureVideoCamera";
    @Bind(R.id.id_camera_view)
    SurfaceView cameraView;

    private SurfaceHolder surfaceHolder;
    private MediaRecorder recorder;
    private boolean recording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_video);
        Log.d(Tag, "onCreate");
        ButterKnife.bind(this);

        recorder = new MediaRecorder();
        initMediaRecorder();
        surfaceHolder = cameraView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);

        cameraView.setFocusable(true);
        cameraView.setFocusableInTouchMode(true);
        cameraView.setClickable(true);
        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    stopRecoder();
                } else {
                    starRecoder();
                }
            }
        });
    }

    private void stopRecoder(){
        recording = false;
        recorder.stop();
        Log.d(Tag, "stop recoder");
        initMediaRecorder();
        prepareRecoder();
    }

    private void starRecoder(){
        recording = true;
        recorder.start();
        Log.d(Tag, "recoding started...");
    }

    private void resetRecoder(){
        if(recording){
            recording = false;
            recorder.reset();
            initMediaRecorder();
            prepareRecoder();
        }
    }

    private void initMediaRecorder(){
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "video_test.mp4";

        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(profile);
        recorder.setOutputFile(filePath);

        recorder.setMaxDuration(10000);//最大时长
        int maxFileSize = 5 * 1024 * 1024;
        recorder.setMaxFileSize(maxFileSize);//最大时长
        recorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                Log.e(Tag, "onError what:" + what + " extra:" + extra);
            }
        });
        recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                Log.e(Tag, "onInfo what:" + what + " extra:" + extra);
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    Log.e(Tag, "MEDIA_RECORDER_INFO_MAX_DURATION_REACHED");
//                    stopRecoder();
                    resetRecoder();
                } else if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED) {
                    Log.e(Tag, "MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED");
//                    stopRecoder();
                    resetRecoder();
                }
            }
        });
    }


    private void prepareRecoder(){
        recorder.setPreviewDisplay(surfaceHolder.getSurface());
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecoder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(Tag, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(Tag,"surfaceDestroyed");
        if(recording){
            recorder.stop();
            recording = false;
        }
        recorder.release();
        finish();
    }

}
