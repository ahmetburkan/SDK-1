package com.app.softwaredevelopmentpraktijk1.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Date;

public class StorageModel {
    private static final String DIRECTORY_NAME = "SDFiles";
    private String FILE_NAME;

    public File directory;

    public StorageModel() {
        hasPermissionsToStorage();
    }

    private void hasPermissionsToStorage() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("State","yes writeable");
            setStorageDirectory();
            setStorageFileName();
        }
        else {
            Log.i("State","not writeable");
        }
    }

    private void setStorageDirectory() {
        this.directory = new File(Environment.getExternalStorageDirectory(), DIRECTORY_NAME);

        if(!directory.exists()){
            directory.mkdirs();
            directory.setExecutable(true);
            directory.setReadable(true);
            directory.setWritable(true);
        }
    }

    public File getStorageDirectory() {
        return this.directory;
    }

    private void setStorageFileName() {
        Date date = new Date();
        long timeMilli = date.getTime();
        this.FILE_NAME = "Image-"+ timeMilli +".jpg";
    }

    public String getStorageFileName() {
        return FILE_NAME;
    }

}
