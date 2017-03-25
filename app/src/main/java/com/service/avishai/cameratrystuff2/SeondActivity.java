package com.service.avishai.cameratrystuff2;

import android.content.Context;
import android.graphics.Color;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import android.hardware.Camera;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import CameraStuff.CameraControl;
import CameraStuff.CameraSurfaceView;

public class SeondActivity extends AppCompatActivity {

    public CameraSurfaceView mPreview;
    private CameraControl mCamControl;

    private Button start, switchCamera;

    private TextView txtTimer;
    private Context myContext;
    private CountDownTimer waitTimer;

    private String [] VideoPathArr;
    boolean recording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        myContext = this;

        initialize();
    }


    public void initialize() {
        VideoPathArr = new String[2];
        mPreview = (CameraSurfaceView) findViewById(R.id.camera_preview);

        txtTimer = (TextView) findViewById(R.id.txt_timer);
        mCamControl = new CameraControl(mPreview, this, System.currentTimeMillis());

        start = (Button) findViewById(R.id.button_start);
        start.setOnClickListener(startListener);

        switchCamera = (Button) findViewById(R.id.button_ChangeCamera);
        switchCamera.setOnClickListener(switchCameraListener);
    }

    OnClickListener switchCameraListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // get the number of cameras
            if (!recording) {
                int camerasNumber = Camera.getNumberOfCameras();
                if (camerasNumber > 1) {
                    // release the old camera instance
                    // switch camera, from the front and the back and vice versa

                    mCamControl.swapCamera();
                    //mCamControl.refreshCamera(myContext);
                    /*releaseCamera();
                    chooseCamera();*/
                } else {
                    Toast toast = Toast.makeText(myContext, "Sorry, your phone has only one camera!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    };

    private CountDownTimer timer = new CountDownTimer(6000, 1000) {

        boolean recordState = false;

        public void onTick(long millisUntilFinished) {

            Long delta = millisUntilFinished / 1000;
            txtTimer.setText(delta.toString());

            if(!recordState) {
                try {
                    String VideoPath = String.format("/sdcard/slangify%s.mp4", String.valueOf(System.currentTimeMillis()));
                    String fixedFilePath = FilesManager.getFilePath(VideoPath);

                    if(VideoPathArr[0] == null)
                        VideoPathArr[0] = fixedFilePath;
                    else
                        VideoPathArr[1] = fixedFilePath;

                    mCamControl.startRecording(fixedFilePath);
                    start.setBackgroundColor(Color.RED);
                    recordState = true;
                } catch (final Exception ex) {
                    Log.i("-------", "Call start recording more than once");
                }
            }
        }

        public void onFinish() {
            txtTimer.setText("done!");

            try {
                start.setBackgroundColor(Color.WHITE);
                mCamControl.stopRecording();
                recordState = false;
            } catch (final Exception ex) {
                // Log.i("---","Exception in thread");
            }

        }

    };

    OnClickListener startListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (waitTimer != null) {
                waitTimer.cancel();
                waitTimer = null;
            }

            if(mCamControl.isFrontCamera())
                mCamControl.swapCamera();

            timer.start();

             new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    mCamControl.swapCamera();
                    timer.start();
                }
            }, 8000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //VideoPathArr


                }
            }, 14);
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    public void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();
        // when on Pause, release camera in order to be used from other
        // applications
        mCamControl.releaseCamera();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

/*    private boolean hasCamera(Context context) {
        // check if the device has camera
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }*/

    ///////////////////////////////////////////////////////////////////////////
    // Events
    ///////////////////////////////////////////////////////////////////////////
    @Subscribe
    public void onSurfaceCreated(SurfaceCreatedEvent event) {
        mCamControl.startPreview();
    }
}
