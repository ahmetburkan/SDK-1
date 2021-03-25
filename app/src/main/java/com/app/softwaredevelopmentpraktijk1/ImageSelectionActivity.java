package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageSelectionActivity extends AppCompatActivity {

    ImageView blackHat;
    ImageView bunnyEars;
    ImageView cowboyHat;
    ImageView greenHat;
    ImageView vikingHelmet;
    ImageView wizardHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent ImageMergeIntent = new Intent(this, ImageMergeActivity.class);

        Bundle extras = getIntent().getExtras();
        String mainImagePath = extras.getString("main_imagepath");

        blackHat = findViewById(R.id.black_hat);
        blackHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageMergeIntent.putExtra("main_imagepath",mainImagePath);
                ImageMergeIntent.putExtra("overlay_image", "black_hat");
                startActivity(ImageMergeIntent);
            }
        });

        bunnyEars = findViewById(R.id.bunny_ears);
        bunnyEars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageMergeIntent.putExtra("main_imagepath",mainImagePath);
                ImageMergeIntent.putExtra("overlay_image", "bunny_ears");
                startActivity(ImageMergeIntent);
            }
        });

        cowboyHat = findViewById(R.id.cowboy_hat);
        cowboyHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageMergeIntent.putExtra("main_imagepath",mainImagePath);
                ImageMergeIntent.putExtra("overlay_image", "cowboy_hat");
                startActivity(ImageMergeIntent);
            }
        });

        greenHat = findViewById(R.id.green_hat);
        greenHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageMergeIntent.putExtra("main_imagepath",mainImagePath);
                ImageMergeIntent.putExtra("overlay_image", "green_hat");
                startActivity(ImageMergeIntent);
            }
        });

        vikingHelmet = findViewById(R.id.viking_helmet);
        vikingHelmet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageMergeIntent.putExtra("main_imagepath",mainImagePath);
                ImageMergeIntent.putExtra("overlay_image", "viking_helmet");
                startActivity(ImageMergeIntent);
            }
        });

        wizardHat = findViewById(R.id.wizard_hat);
        wizardHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageMergeIntent.putExtra("main_imagepath",mainImagePath);
                ImageMergeIntent.putExtra("overlay_image", "wizard_hat");
                startActivity(ImageMergeIntent);
            }
        });
    }
}