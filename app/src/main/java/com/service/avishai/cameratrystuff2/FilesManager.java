package com.service.avishai.cameratrystuff2;

import java.io.File;

/**
 * Created by avishai on 3/10/2017.
 */

public class FilesManager {


    private FilesManager(){

    }

    //check if file exists, if so, add integer to name
    public static String getFilePath(String filePath){

        File f = new File(filePath);
        String folder = f.getParent();
        String fileName = f.getName();

        Integer counter = 1;
        while(f.exists()){
            String nameWithNoExtension = fileName.substring(0, fileName.lastIndexOf("."));
            nameWithNoExtension += counter.toString();

            f = new File(folder + "/" + nameWithNoExtension + ".mp4");
            counter++;
        }

        return f.getPath();
    }

    public String merge2Videos(String video1, String video2){



        //Videokit vk = Videokit.getInstance();
        return "";
    }



}
