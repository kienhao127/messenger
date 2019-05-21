package com.example.messenger.utils;

import android.util.Base64;
import android.util.Log;

import com.example.messenger.R;
import com.example.messenger.model.LocalFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {

    public static ArrayList<LocalFile> getAllFilesOfDir(File directory) {
        ArrayList<LocalFile> filepathArray = new ArrayList<>();
        final File[] files = directory.listFiles();

        if ( files != null ) {
            for ( File file : files ) {
                if ( file != null ) {
                    if ( file.isDirectory() && file.getName() == "Download") {  // it is a folder...
                        filepathArray.addAll(getAllFilesOfDir(file));
                    }
                    else {  // it is a file...
                        LocalFile localFile = new LocalFile(file.getName(), file.getAbsolutePath());
                        filepathArray.add(localFile);
                    }
                }
            }
        }

        return filepathArray;
    }

    public static String fileToBase64(String filepath){
        byte[] byteArray = readBytesFromFile(filepath);
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT).replace("\n", "");
        return encoded;
    }

    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }

    public static int getFileTypeIcon(String filename){
        String[] filenames = filename.split("\\.");
        int length = filenames.length;
        if (length <= 1){
            return R.drawable.other;
        } else  {
            switch (filenames[length-1]){
                case "tif":
                case "jpg":
                case "gif":
                case "png":
                    return R.drawable.picture;
                case "mp3":
                    return R.drawable.music;
                case "mp4":
                case "m4a":
                case "m4v":
                case "f4v":
                case "f4a":
                case "m4b":
                case "m4r":
                case "f4b":
                case "mov":
                case "wmv":
                case "wma":
                case "webm":
                case "flv":
                case "avi":
                    return R.drawable.video;
                case "zip":
                case "rar":
                    return R.drawable.zip;
                case "pdf":
                    return R.drawable.text;
                default:
                    return R.drawable.other;
            }
        }
    }
}
