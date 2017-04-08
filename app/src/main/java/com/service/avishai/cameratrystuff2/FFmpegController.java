package com.service.avishai.cameratrystuff2;

/**
 * Created by avishai on 4/8/2017.
 */

import android.content.Context;
import android.util.Log;

import com.service.avishai.cameratrystuff2.Consts_Enums.Constants;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

public class FFmpegController {


    private static FFmpegController instance;
    private FFmpeg ffmpeg;

    private FFmpegController(Context context){

        ffmpeg = FFmpeg.getInstance(context);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Log.d(Constants.Media.Ffmpeg, "Entered onStart ffmpeg");
                }

                @Override
                public void onFailure() {
                    Log.d(Constants.Media.Ffmpeg, "ERROR! Entered onFailure ffmpeg");
                }

                @Override
                public void onSuccess() {
                    Log.d(Constants.Media.Ffmpeg, "Entered onSuccess ffmpeg!!");
                }

                @Override
                public void onFinish() {
                    Log.d(Constants.Media.Ffmpeg, "Entered onFinish ffmpeg");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
            Log.d(Constants.Media.Ffmpeg, "ERROR! ffmpeg" + e.getMessage());

        }
    }

    //use application context
    public static synchronized FFmpegController getInstance(Context context){
        if(instance == null)
        {
            instance = new FFmpegController(context);
        }

        return instance;
    }


    public Boolean executeCommand(String [] commands)
    {

        Boolean isExecuted = false;
        //do command validation

        //execute command
        if(ffmpeg.isFFmpegCommandRunning()){
            Log.d(Constants.Media.Ffmpeg, "ERROR! Other component uses FFmpeg at the moment");
            return false;
        }
        else{

            try{

                FfmpegExecuteResponseHandler handler = new FfmpegExecuteResponseHandler();
                ffmpeg.execute(commands, handler);
                isExecuted = true;
            }
            catch(Exception ex){
                Log.d(Constants.Media.Ffmpeg, "ERROR! " + ex.getMessage().toString());
            }
        }

        return isExecuted;
    }




}
