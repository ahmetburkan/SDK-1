package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ImageMergeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_merge);
        Bundle extras = getIntent().getExtras();

        String mainImagePath = extras.getString("main_imagepath");
        String overlayImage = extras.getString("overlay_image");

        ImageView image = (ImageView) findViewById(R.id.imageView);

        Bitmap bitmapMain = BitmapFactory.decodeFile(mainImagePath);
        Bitmap bitmapOverlay = BitmapFactory.decodeResource(this.getResources(), R.drawable.green_hat);

//        Bitmap scaledbitmapMain = Bitmap.createScaledBitmap(bitmapOverlay, 400, 400, true);
//        Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(bitmapOverlay, 35, 35, true);

        Bitmap combineImages = overlay(bitmapMain, bitmapMain);

        image.setImageBitmap(combineImages);
    }

    public static Bitmap overlay(Bitmap background, Bitmap overlay)
    {
        try {
            Bitmap bmOverlay = Bitmap.createBitmap(background.getWidth(), background.getHeight(),  background.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(background, new Matrix(), null);
            canvas.drawBitmap(overlay, 300, 400, null);
            return bmOverlay;

        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
}