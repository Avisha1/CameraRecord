package com.service.avishai.cameratrystuff2;

/**
 * Created by avishai on 4/8/2017.
 */

import android.util.Log;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.service.avishai.cameratrystuff2.Consts_Enums.Constants;

public class FfmpegExecuteResponseHandler implements FFmpegExecuteResponseHandler{
    @Override
    public void onSuccess(String message) {
        Log.d(Constants.Media.Ffmpeg, "Entered onFinish Handler" + message);
    }

    @Override
    public void onProgress(String message) {
        Log.d(Constants.Media.Ffmpeg, "Entered onFinish Handler" + message);

    }

    @Override
    public void onFailure(String message) {
        Log.d(Constants.Media.Ffmpeg, "Entered onFinish Handler" + message);
    }

    @Override
    public void onStart() {
        Log.d(Constants.Media.Ffmpeg, "Entered onStart Handler");
    }

    @Override
    public void onFinish() {
        Log.d(Constants.Media.Ffmpeg, "Entered onFinish Handler");
    }
}
