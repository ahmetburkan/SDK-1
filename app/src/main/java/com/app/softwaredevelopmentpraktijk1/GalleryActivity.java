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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

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

        // inflate the main layout for the activity
        setContentView(R.layout.activity_gallery);

        // get a reference to the already created main layout
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearlayout);

        // inflate (create) another copy of our custom layout
        LayoutInflater inflater = getLayoutInflater();

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


//                TODO: Frits
//                Uri imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", imageFile);




                View myLayout = inflater.inflate(R.layout.basic_gallery_layout, mainLayout, false);

                // Override imageview
                ImageView imageView = (ImageView) myLayout.findViewById(R.id.current_image);
                String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+StorageModel.DIRECTORY_NAME+"/"+ file.getName();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);

                // Override share button
                Button shareButton = (Button) myLayout.findViewById(R.id.share_button);
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

                // Override edit button
                Button editButton = (Button) myLayout.findViewById(R.id.edit_button);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                mainLayout.addView(myLayout);
            }
        }
    }
}