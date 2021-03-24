package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.softwaredevelopmentpraktijk1.Model.StorageModel;

import java.io.File;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearlayout);
        LayoutInflater inflater = getLayoutInflater();

        Intent ImageSelectionIntent = new Intent(this, ImageSelectionActivity.class);

        // TODO: Frits
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        StorageModel storageModel = new StorageModel();
        File[] files = storageModel.getDirectoryFiles();

        for (File file : files) {
            if (!file.isDirectory()) {
                File imageFile = new File(storageModel.getStorageDirectory(), file.getName());
                Uri imageUri =  Uri.fromFile(imageFile);

                // TODO: Frits
                // Uri imageUri = FileProvider.getUriForFile(this.getContext(), context.getApplicationContext().getPackageName() + ".fileprovider", imageFile);

                View currentRowLayout = inflater.inflate(R.layout.basic_gallery_layout, mainLayout, false);

                // Override imageview
                ImageView imageView = (ImageView) currentRowLayout.findViewById(R.id.current_image);
                String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+StorageModel.DIRECTORY_NAME+"/"+ file.getName();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);

                // Override share button
                Button shareButton = (Button) currentRowLayout.findViewById(R.id.share_button);
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
                Button editButton = (Button) currentRowLayout.findViewById(R.id.edit_button);
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageSelectionIntent.putExtra("main_imagepath",imagePath);
                        startActivity(ImageSelectionIntent);
                    }
                });

                mainLayout.addView(currentRowLayout);
            }
        }
    }
}