package com.service.avishai.cameratrystuff2;

/**
 * Created by avishai on 3/25/2017.
 */

//import android.graphics.Movie;

import android.net.Uri;

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

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.TrackBox;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;
import com.googlecode.mp4parser.util.Matrix;
import com.googlecode.mp4parser.util.Path;

public class MediaEditUtil {

    public static boolean mergeVideos(String firstVideo, String secondVideo, String output) {

        boolean result = true;
        try {


            /*ArrayList<String> SegmentNumber = new ArrayList<>();
            SegmentNumber.add(firstVideo);
            SegmentNumber.add(secondVideo);

            for (int i = 1; i <= SegmentNumber.size(); i++) {

                IsoFile isoFile = new IsoFile(SegmentNumber.get(i));
                Movie m = new Movie();

                List<TrackBox> trackBoxes = isoFile.getMovieBox().getBoxes(
                        TrackBox.class);

                for (TrackBox trackBox : trackBoxes) {

                    trackBox.getTrackHeaderBox().setMatrix(Matrix.ROTATE_90);
                    m.addTrack(new Mp4TrackImpl("", trackBox));

                }
                //inMovies[i - 1] = m;
            }*/





            Movie[] inMovies = new Movie[2];

            /*File file1 = new File(firstVideo);
            File file2 = new File(secondVideo);

            if(file1.exists() && file2.exists()){

                FileInputStream fis1 = new  FileInputStream(file1);
                FileInputStream fis2 = new FileInputStream(file2);

                FileChannel channel1 = fis1.getChannel();
                FileChannel channel2 = fis2.getChannel();

                inMovies[0] = MovieCreator.build(channel1);
            }*/


            inMovies[0] = MovieCreator.build(firstVideo);
            inMovies[1] = MovieCreator.build(secondVideo);


            Matrix firstMatrix = inMovies[0].getMatrix();
            Matrix secondMatrix = inMovies[1].getMatrix();
            //inMovies[1].setMatrix(Matrix.ROTATE_270);
            //secondMatrix = inMovies[1].getMatrix();


            //change the wrong orientation for the second video
            /*IsoFile isoFile1 = new IsoFile(firstVideo);
            IsoFile isoFile2 = new IsoFile(secondVideo);
            Movie secondMov = new Movie();

            List<TrackBox> trackBoxes = isoFile1.getMovieBox().getBoxes(
                    TrackBox.class);

            Matrix matrix = null ;
            for (TrackBox trackBox : trackBoxes) {
                matrix = trackBox.getTrackHeaderBox().getMatrix();
            }

            trackBoxes = isoFile2.getMovieBox().getBoxes(
                    TrackBox.class);
            for (TrackBox trackBox : trackBoxes) {


                trackBox.getTrackHeaderBox().setMatrix(matrix);
                secondMov.addTrack(new Mp4TrackImpl("sad", trackBox));

            }*/

/*            String path = new File(secondVideo).getAbsolutePath();
            IsoFile isoFile = new IsoFile(secondVideo);
            MovieHeaderBox mvhb = Path.getPath(isoFile, "/moov/mvhd"); //secondVideo;
            mvhb.setMatrix(Matrix.ROTATE_180);
            isoFile.writeContainer(new FileOutputStream("resultOrFix.mp4").getChannel());*/




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
}
