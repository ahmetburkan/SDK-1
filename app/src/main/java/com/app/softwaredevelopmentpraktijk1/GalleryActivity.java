package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

        StorageModel storageModel = new StorageModel();

        File folder = new File(String.valueOf(storageModel.getStorageDirectory()));
        File[] filesInFolder = folder.listFiles();

        for (File file : filesInFolder) {
            if (!file.isDirectory()) {

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


                final Button mailButton = new Button(this);
                mailButton.setText("Share");

                Intent intent = new Intent(this, ShareActivity.class);


                mailButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("imagepath", imagePath);
                        startActivity(intent);
                    }
                });


                // Add image to wrapper
                linearLayoutWrapper.addView(imageView);
                linearLayoutWrapper.addView(mailButton);

                // Add wrapper to scrollview
                LinearLayout linearLayout = findViewById(R.id.scrollview);
                linearLayout.addView(linearLayoutWrapper);



            }
        }
    }
}