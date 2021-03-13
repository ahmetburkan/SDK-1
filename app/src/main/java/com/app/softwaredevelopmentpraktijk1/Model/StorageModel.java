package com.app.softwaredevelopmentpraktijk1.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

import java.io.File;

public class StorageModel {
    public static int IMAGE_COUNTER = 0;
    private static final String DIRECTORY_NAME = "SDFiles";
    private String FILE_NAME;

    public StorageModel() {
        setStorageDirectory();
    }

    public void setStorageDirectory() {
        File dir = new File(Environment.getExternalStorageDirectory(), DIRECTORY_NAME);
        if(!dir.exists()){
            dir.mkdirs();
            dir.setExecutable(true);
            dir.setReadable(true);
            dir.setWritable(true);
        }
    }

    public String getStorageDirectory() {
        return DIRECTORY_NAME;
    }

    public void setStorageFileName() {
        this.FILE_NAME = "Image-"+ IMAGE_COUNTER +".jpg";
        ++IMAGE_COUNTER;
    }

    public String getStorageFileName() {
        return FILE_NAME;
    }

    public void createNewFile() {
        BitmapDrawable drawable = (BitmapDrawable) placeHolderImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

    }

}
