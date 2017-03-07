package com.service.avishai.cameratrystuff2;

/**
 * Created by avishai on 3/6/2017.
 */

public class CameraControl implements CameraControlInterface {

    private String destinationPath;



    public CameraControl(String destPath){
        destinationPath = destPath;
    }


    @Override
    public void startRecording() {

    }

    @Override
    public void stopRecording() {

    }

    @Override
    public void setCameraType(CameraType type) {

    }
}
