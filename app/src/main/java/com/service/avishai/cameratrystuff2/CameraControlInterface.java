package com.service.avishai.cameratrystuff2;

/**
 * Created by avishai on 3/6/2017.
 */





public interface CameraControlInterface {
    void startRecording();
    void stopRecording();

    void setCameraType(CameraType type);

    enum CameraType {
        FRONT,
        BACK
    }
}
