package com.app.softwaredevelopmentpraktijk1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.softwaredevelopmentpraktijk1.Model.DateModel;
import com.app.softwaredevelopmentpraktijk1.Model.DrawableToBitmap;
import com.app.softwaredevelopmentpraktijk1.Model.StorageModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView currentMonthField;
    ImageView placeHolderImage;
    Button galleryButton;
    Button cameraButton;
    Button nextButton;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int STORAGE_REQUEST_CODE = 1001;
    private static final int CAMERA_REQUEST_CODE = 1002;

    public String currentImagePath = null;

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
                captureCameraImage();
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
    public void setCurrentMonthField() {
        DateModel dateModel = new DateModel();
        currentMonthField = (TextView)findViewById(R.id.current_month_field);
        currentMonthField.setText(dateModel.getMonthString());
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

    public void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    private void captureCameraImage() {
        Intent cameraIntent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File imageFile = null;

            try {
                imageFile = getCameraImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(this, "com.app.softwaredevelopmentpraktijk1.fileprovider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File getCameraImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();

        return imageFile;
    }

    public void saveCurrentImage() {
        BitmapDrawable drawable = (BitmapDrawable) placeHolderImage.getDrawable();
        DrawableToBitmap drawableToBitmap = new DrawableToBitmap();
        Bitmap bitmap = drawableToBitmap.createDrawableToBitmap(drawable);

        StorageModel storageModel = new StorageModel();
        storageModel.createFile(bitmap);

        Toast.makeText(getApplicationContext(), "Saved current image", Toast.LENGTH_SHORT).show();

        // Redirect to new view
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted to access storage", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied to access storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            currentImagePath = String.valueOf(data.getData());
            placeHolderImage.setImageURI(Uri.parse(currentImagePath));
            Toast.makeText(getApplicationContext(), "Image added", Toast.LENGTH_SHORT).show();
        }

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            placeHolderImage.setImageURI(Uri.parse(currentImagePath));
            Toast.makeText(getApplicationContext(), "Image added", Toast.LENGTH_SHORT).show();
        }
    }
}