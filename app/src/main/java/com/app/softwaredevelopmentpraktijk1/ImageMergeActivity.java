package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.softwaredevelopmentpraktijk1.Model.CanvasModel;
import com.app.softwaredevelopmentpraktijk1.Model.DrawableToBitmap;
import com.app.softwaredevelopmentpraktijk1.Model.StorageModel;

public class ImageMergeActivity extends AppCompatActivity {

    Drawable drawable;
    Button goBack;
    Button saveImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_merge);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent galleryIntent = new Intent(this, GalleryActivity.class);
        Intent imageSelectionIntent = new Intent(this, ImageSelectionActivity.class);

        goBack = findViewById(R.id.go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(imageSelectionIntent);
            }
        });

        Bundle extras = getIntent().getExtras();
        String mainImagePath = extras.getString("main_imagepath");
        String overlayImage = extras.getString("overlay_image");

        switch (overlayImage) {
            case "black_hat":
                this.drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.black_hat, null);
                break;

            case "bunny_ears":
                this.drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.bunny_ears, null);
                break;

            case "cowboy_hat":
                this.drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.cowboy_hat, null);
                break;

            case "green_hat":
                this.drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.green_hat, null);
                break;

            case "viking_helmet":
                this.drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.viking_helmet, null);
                break;

            case "wizard_hat":
                this.drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.wizard_hat, null);
                break;

            default:
                this.drawable = null;
                break;
        }

        Bitmap backgroundBitmap = BitmapFactory.decodeFile(mainImagePath);
        DrawableToBitmap drawableToBitmap = new DrawableToBitmap();
        Bitmap overlayBitmap = drawableToBitmap.createDrawableToBitmap(this.drawable);

        CanvasModel canvasModel = new CanvasModel(backgroundBitmap, overlayBitmap);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageBitmap(canvasModel.finalBitmap);

        saveImage = findViewById(R.id.save_image);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageModel storageModel = new StorageModel();
                storageModel.createFile(canvasModel.finalBitmap);

                Toast.makeText(getApplicationContext(), "Saved current image", Toast.LENGTH_SHORT).show();

                // Redirect to new view
                startActivity(galleryIntent);
            }
        });
    }
}