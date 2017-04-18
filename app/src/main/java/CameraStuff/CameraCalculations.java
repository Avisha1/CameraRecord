package CameraStuff;

/**
 * Created by avishai on 4/18/2017.
 */

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.List;

public class CameraCalculations {

    public static Camera.Size CalculateSquarePreview(List<Camera.Size> choices) {
        if (choices == null) {
            //choices are null, return max values of video / still accordingly to if isVideoSize.
            //return isVideoSize ? new Size(MAX_VIDEO_WIDTH, MAX_VIDEO_HEIGHT) : new Size(MAX_STILL_WIDTH, MAX_STILL_HEIGHT);
        }

        // max width value of the desired aspect ratio (16:9)
        int maxWidth = 720;
        int delta = 100;
        Camera.Size defultSize = choices.get(4);
        for (Camera.Size size : choices) {

            if (size.width <= maxWidth) {
                if (size.width == size.height) {
                    return size;
                }
               /* else {
                    int currentDelta = Math.abs(size.width - size.height);
                    if(currentDelta < delta){
                        delta = currentDelta;
                        defultSize= size;
                    }

                }*/

            }

        }

        return choices.get(8);
    }

    public static Camera.Size CalculateSquareVideo(List<Camera.Size> sizes) {
        final double ASPECT_TOLERANCE = 0.1;

        if (sizes == null)
            return null;

        double targetRatio = 1.0;
        Camera.Size optimalSize = null;
        double minDiff = Integer.MAX_VALUE;

        int targetHeight = 1000;

        for (Camera.Size size : sizes){

            Log.d("Camera", "Checking size " + size.width + "w " + size.height+ "h");

            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }

        }

        Log.d("Camera", "chosen size " + optimalSize.width + "w " + optimalSize.height+ "h");
        return optimalSize;
    }


}
