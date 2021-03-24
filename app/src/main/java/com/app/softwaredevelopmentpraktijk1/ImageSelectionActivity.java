package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageSelectionActivity extends AppCompatActivity {

    ImageView greenHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);

        Intent ImageMergeIntent = new Intent(this, ImageMergeActivity.class);

        Bundle extras = getIntent().getExtras();
        String mainImagePath = extras.getString("main_imagepath");

        greenHat = findViewById(R.id.green_hat);
        greenHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageMergeIntent.putExtra("main_imagepath",mainImagePath);
                ImageMergeIntent.putExtra("overlay_image", "green_hat");
                startActivity(ImageMergeIntent);
            }
        });
    }
}