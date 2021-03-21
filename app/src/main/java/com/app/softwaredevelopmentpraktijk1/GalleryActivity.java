package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.softwaredevelopmentpraktijk1.Model.StorageModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // TODO: Frits
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        StorageModel storageModel = new StorageModel();
        Context context = this;

        File folder = new File(String.valueOf(storageModel.getStorageDirectory()));
        File[] filesInFolder = folder.listFiles();

        for (File file : filesInFolder) {
            if (!file.isDirectory()) {
                File imageFile = new File(folder, file.getName());
                Uri imageUri =  Uri.fromFile(imageFile);
                // TODO: Frits
//                Uri imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", imageFile);

                // Create wrapper for ImageView
                final LinearLayout linearLayoutWrapper = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
                params.setMargins(30, 30, 30, 30);
                linearLayoutWrapper.setLayoutParams(params);
                linearLayoutWrapper.setOrientation(LinearLayout.HORIZONTAL);
                linearLayoutWrapper.setBackgroundColor(0xFF00FF00);

                // Create ImageView
                final ImageView imageView = new ImageView(this);
                String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+StorageModel.DIRECTORY_NAME+"/"+ file.getName();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
                imageView.setMinimumHeight(300);


                final Button shareButton = new Button(this);
                shareButton.setText("Share");
                shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(shareIntent,"Share"));
                    }
                });

                // Add image to wrapper
                linearLayoutWrapper.addView(imageView);
                linearLayoutWrapper.addView(shareButton);

                // Add wrapper to scrollview
                LinearLayout linearLayout = findViewById(R.id.scrollview);
                linearLayout.addView(linearLayoutWrapper);
            }
        }
    }
}