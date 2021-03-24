package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.softwaredevelopmentpraktijk1.Model.DateModel;
import com.app.softwaredevelopmentpraktijk1.Model.StorageModel;

public class ImageMergeActivity extends AppCompatActivity {

    Drawable drawable;
    Button goBack;
    Button saveImage;
    Bitmap mergedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_merge);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent goBackIntent = new Intent(this, GalleryActivity.class);
        goBack = findViewById(R.id.go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goBackIntent);
            }
        });

        saveImage = findViewById(R.id.save_image);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageModel storageModel = new StorageModel();
                storageModel.createFile(mergedImages);

                Toast.makeText(getApplicationContext(), "Saved current image", Toast.LENGTH_SHORT).show();

                // Redirect to new view
                startActivity(goBackIntent);
            }
        });



        Bundle extras = getIntent().getExtras();

        String mainImagePath = extras.getString("main_imagepath");
        String overlayImage = extras.getString("overlay_image");


        if (overlayImage.equals("green_hat")) {
            this.drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.green_hat, null);
        }

        ImageView image = (ImageView) findViewById(R.id.imageView);

        Bitmap bitmapMain = BitmapFactory.decodeFile(mainImagePath);
        Bitmap bitmapOverlay = drawableToBitmap(this.drawable);
        Bitmap scaledbitmapOverlay = Bitmap.createScaledBitmap(bitmapOverlay, 400, 400, true);

        this.mergedImages = Bitmap.createBitmap(bitmapMain.getWidth(), bitmapMain.getHeight(),  bitmapMain.getConfig());

        Canvas canvas = new Canvas(this.mergedImages);

        canvas.drawBitmap(bitmapMain, 0, 0, null);
        canvas.drawBitmap(scaledbitmapOverlay,
                (bitmapMain.getWidth() - scaledbitmapOverlay.getWidth()) / 2,
                (bitmapMain.getHeight() - scaledbitmapOverlay.getHeight()) / 5,
                null);


        Paint paint = new Paint();
        Rect bounds = new Rect();

        int text_height = 0;
        int text_width = 0;

        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(60);

        String text = new DateModel().getMonthString();

        paint.getTextBounds(text, 0, text.length(), bounds);
        paint.setColor(Color.WHITE);
        text_height =  bounds.height();
        text_width =  bounds.width();

        canvas.drawText(text,
                (bitmapMain.getWidth() - text_width) / 2,
                text_height * 2,
                paint);

        image.setImageBitmap(this.mergedImages);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}