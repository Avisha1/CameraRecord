package com.service.avishai.cameratrystuff2;

/**
 * Created by avishai on 3/25/2017.
 */

import android.graphics.Movie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MediaEditUtil {

    public static void mergeVideos(String firstVideo, String secondVideo){

        try{
            FileInputStream fis1 = new FileInputStream(firstVideo);
            FileInputStream fis2 = new FileInputStream(secondVideo);


            Movie [] inMovies = new Movie[2];
            inMovies[0] = Movie.decodeStream(fis1);
            inMovies[1] = Movie.decodeStream(fis2);



           // Track
           /* List<Track> videoTracks = new LinkedList<Track>();
            List<Track> audioTracks = new LinkedList<Track>();
            for (Movie m : inMovies) {
                for (Track t : m.getTracks()) {
                    if (t.getHandler().equals("soun")) {
                        audioTracks.add(t);
                    }
                    if (t.getHandler().equals("vide")) {
                        videoTracks.add(t);
                    }
                }
            }*/
        }
        catch(FileNotFoundException ex){

        }





    }
}
