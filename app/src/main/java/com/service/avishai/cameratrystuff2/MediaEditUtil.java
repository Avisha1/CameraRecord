package com.service.avishai.cameratrystuff2;

/**
 * Created by avishai on 3/25/2017.
 */

//import android.graphics.Movie;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.service.avishai.cameratrystuff2.Consts_Enums.Constants;

public class MediaEditUtil {

    public static boolean mergeVideos(String firstVideo, String secondVideo, String output) {

        boolean result = true;
        try {
            Movie[] inMovies = new Movie[2];


            inMovies[0] = MovieCreator.build(firstVideo);
            inMovies[1] = MovieCreator.build(secondVideo);

            //Append the two videos into one video
            List<Track> videoTracks = new LinkedList<>();
            List<Track> audioTracks = new LinkedList<>();
            for (Movie m : inMovies) {

                for (Track t : m.getTracks()) {
                    if (t.getHandler().equals("soun")) {
                        audioTracks.add(t);
                    }
                    if (t.getHandler().equals("vide")) {
                        videoTracks.add(t);
                    }
                }
            }
            Movie resultMov = new Movie();


            if (audioTracks.size() > 0) {
                resultMov.addTrack(new AppendTrack(audioTracks
                        .toArray(new Track[audioTracks.size()])));
            }
            if (videoTracks.size() > 0) {
                resultMov.addTrack(new AppendTrack(videoTracks
                        .toArray(new Track[videoTracks.size()])));
            }

            //
            Container out = new DefaultMp4Builder().build(resultMov);
            FileOutputStream fos = new FileOutputStream(new File(output));
            FileChannel channel = fos.getChannel();
            out.writeContainer(channel);
            fos.close();


        } catch (FileNotFoundException ex) {
            result = false;
        } catch (IOException ex) {
            result = false;
        } catch (Exception ex) {
            result = false;
        }

        return result;

    }



    public static Boolean merge2VideosFFMPEG(String firstVideo, String secondVideo, String output, Context context) {



        FFmpegController ffmpeg = FFmpegController.getInstance(context);



        String [] commands = getCommands(firstVideo, secondVideo, output);
        //String [] commands = {AltStr};
        Boolean isExecuted = ffmpeg.executeCommand(commands);

        return isExecuted;
    }

    private static String[] getCommands(String firstVideo, String secondVideo, String output){

        String firstLine = "ffmpeg -i " + firstVideo + " -c copy -bsf:v h264_mp4toannexb -f mpegts /sdcard/intermediate1.ts";
        Log.d("MediaEditUtils", "First command: " + firstLine);

        String secondLine = "ffmpeg -i " + secondVideo + " -c copy -bsf:v h264_mp4toannexb -f mpegts /sdcard/intermediate2.ts";
        Log.d("MediaEditUtils", "Second command: " + secondLine);

        String thirdLine = "ffmpeg -i \"concat:/sdcard/intermediate1.ts|/sdcard/intermediate2.ts\" -c copy -bsf:a aac_adtstoasc " +  output;
        Log.d("MediaEditUtils", "Third command: " + thirdLine);

/*        String firstLine = "ffmpeg -i " + firstVideo + " -qscale 0 /sdcard/1.mpg";
        Log.d("MediaEditUtils", "First command: " + firstLine);

        String secondLine = "ffmpeg -i " + secondVideo + " -qscale 0 /sdcard/2.mpg";
        Log.d("MediaEditUtils", "Second command: " + secondLine);

        String thirdLine = "cat /sdcard/1.mpg /sdcard/2.mpg | ffmpeg -f mpeg -i - -qscale 0 -vcodec mpeg4 " + output;
        Log.d("MediaEditUtils", "Third command: " + thirdLine);*/


        //this is the command that worked on ffmpeg windows version
        String command = "-i " +
                "\"" + firstVideo + "\"" +" -i " +
                "\"" + secondVideo + "\"" +  " -filter_complex “[0:v:0] [0:a:0] [1:v:0] [1:a:0] concat=n=2:v=1:a=1 [v] [a]” -map “[v]” -map “[a]” " + output;




        String [] commands = {"-i",firstVideo,
                "-i",secondVideo,
                "-filter_complex","[0:v:0] [0:a:0] [1:v:0] [1:a:0] concat=n=2:v=1:a=1 [v] [a]",
                "-map","[v]",
                "-map","[a]",
                "-preset","ultrafast",
                output};


        return commands;
    }
}
