package CameraStuff;

/**
 * Created by avishai on 3/3/2017.
 */

import android.annotation.TargetApi;
import android.content.Context;

import android.os.Build;
import android.util.AttributeSet;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.service.avishai.cameratrystuff2.SurfaceCreatedEvent;

import org.greenrobot.eventbus.EventBus;


public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private CameraControl camControl;

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        //initSurface();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setWillNotDraw(true);
        initSurface();
    }

    public CameraSurfaceView(Context context, CameraControl control){//}, Camera camera) {
        super(context);
        //mCamera = camera;
        camControl = control;
        initSurface();
    }

    private void initSurface() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

/*    public SurfaceHolder getHolderFromSurface(){
        if(mHolder == null)
            mHolder = getHolder();

        return mHolder;
    }*/


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }


/*    public void surfaceCreated(SurfaceHolder holder) {
        *//*try {
            // create the surface and start camera preview
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.d(VIEW_LOG_TAG, "Error setting camera preview: " + e.getMessage());
        }*//*
        isSurfaceCreate = true;
    }*/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        EventBus.getDefault().post(new SurfaceCreatedEvent());
        //camControl.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


/*    public void previewCamera() {
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            isPreviewRunning = true;
        } catch (Exception e) {
            Log.d("APP_CLASS", "Cannot start preview", e);
        }
    }*/

 /*   public void setCamera(Camera camera) {
        //method to set a camera instance
        mCamera = camera;
    }*/

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // mCamera.release();

    }
}