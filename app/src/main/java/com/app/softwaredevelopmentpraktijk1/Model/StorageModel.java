package com.app.softwaredevelopmentpraktijk1.Model;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class StorageModel {
    public static final String DIRECTORY_NAME = "SDFiles";
    private File directory;
    private String FILE_NAME;

    public StorageModel() {
        hasPermissionsToStorage();
        setStorageDirectory();
        setStorageFileName();
    }

    private void hasPermissionsToStorage() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("State","yes writeable");
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

    public void createFile(Bitmap bitmap) {
        File file = new File(getStorageDirectory(), getStorageFileName());

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File[] getDirectoryFiles() {
        File folder = new File(String.valueOf(getStorageDirectory()));
        File[] files = folder.listFiles();

        return files;
    }
}
