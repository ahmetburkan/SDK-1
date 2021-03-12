package com.app.softwaredevelopmentpraktijk1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.softwaredevelopmentpraktijk1.Model.DateModel;

public class MainActivity extends AppCompatActivity {

    TextView currentMonthField;
    ImageView placeHolderImage;
    Button galleryButton;
    Button cameraButton;
    Button nextButton;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
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
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        // No permissions, asking for it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // P0p uP
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        getImageFromGallery();
                    }
                }
                else {
                    getImageFromGallery();
                }
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
                    Toast.makeText(getApplicationContext(), "Saved current image", Toast.LENGTH_SHORT).show();

//                    TODO: 1. Call class Storage and add the current image to the private files of the app.
//                    TODO: 2. Redirect to new view. This view will show all images saved. Only accessible if clicked 'use this picture'.
//                    TODO: 2.1 Possible to share image already without editing.
//                    TODO: 3. Choose image of choice to edit.


                }
                else {
                    Toast.makeText(getApplicationContext(), "No image selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getImageFromGallery();
                }
                else {
                    Toast.makeText(this, "PeRMissi0n dEniEd", Toast.LENGTH_SHORT).show();
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