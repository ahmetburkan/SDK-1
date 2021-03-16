package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if(getIntent().hasExtra("imagepath")) {
            Bundle imagePath = getIntent().getExtras();

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath.getString("imagepath"));

            imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }
    }
}