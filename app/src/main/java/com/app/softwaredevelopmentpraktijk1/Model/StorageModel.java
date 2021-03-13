package com.app.softwaredevelopmentpraktijk1.Model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.app.softwaredevelopmentpraktijk1.GalleryActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class StorageModel {
    private static final String DIRECTORY_NAME = "SDFiles";
    private String FILE_NAME;

    public File directory;

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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
