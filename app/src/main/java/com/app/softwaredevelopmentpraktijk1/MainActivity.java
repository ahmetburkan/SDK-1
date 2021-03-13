package com.app.softwaredevelopmentpraktijk1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.softwaredevelopmentpraktijk1.Model.DateModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView currentMonthField;
    ImageView placeHolderImage;
    Button galleryButton;
    Button cameraButton;
    Button nextButton;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int STORAGE_REQUEST_CODE = 1001;
    private static final int CAMERA_REQUEST_CODE = 1002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setCurrentMonthField();

        placeHolderImage = findViewById(R.id.placeholder_image);

        galleryButton = findViewById(R.id.open_gallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check permission
                getPermissionOfUser("galleryButton");
            }
        });

        cameraButton = findViewById(R.id.open_camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        nextButton = findViewById(R.id.save_file_and_open_storage);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(placeHolderImage.getDrawable() != null) {
                    // Check permission
                    getPermissionOfUser("nextButton");
                }
                else {
                    Toast.makeText(getApplicationContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void saveCurrentImage() {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("State","yes writeable");
        }
        else {
            Log.i("State","not writeable");
        }

        BitmapDrawable drawable = (BitmapDrawable) placeHolderImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File dir = new File(Environment.getExternalStorageDirectory(), "SDFiles");
        if(!dir.exists()){
            dir.mkdirs();
            dir.setExecutable(true);
            dir.setReadable(true);
            dir.setWritable(true);
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (dir, fname);

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getApplicationContext(), "Saved current image", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }


////                    TODO: 1. Call class Storage and add the current image to the external files of the app.
////                    TODO: 2. Redirect to new view. This view will show all images saved. Only accessible if clicked 'use this picture'.
////                    TODO: 2.1 Possible to share image already without editing.
////                    TODO: 3. Choose image of choice to edit.
    }

    public void setCurrentMonthField() {
        DateModel dateModel = new DateModel();
        currentMonthField = (TextView)findViewById(R.id.current_month_field);
        currentMonthField.setText(dateModel.getMonthString());
    }

    public void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    public void getPermissionOfUser(String context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                // No permissions, asking for it.
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                // Pop up
                requestPermissions(permissions, STORAGE_REQUEST_CODE);
            } else {
                if (context.equals("nextButton")) {
                    saveCurrentImage();
                }
                if (context.equals("galleryButton")) {
                    getImageFromGallery();
                }
            }
        }
        else {
            if (context.equals("nextButton")) {
                saveCurrentImage();
            }
            if (context.equals("galleryButton")) {
                getImageFromGallery();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted to access storage", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Permission denied to access storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            placeHolderImage.setImageURI(data.getData());
            Toast.makeText(getApplicationContext(), "Image added", Toast.LENGTH_SHORT).show();
        }

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            placeHolderImage.setImageBitmap(bitmap);
            Toast.makeText(getApplicationContext(), "Image added", Toast.LENGTH_SHORT).show();
        }
    }
}