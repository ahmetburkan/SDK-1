package com.app.softwaredevelopmentpraktijk1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.app.softwaredevelopmentpraktijk1.Model.StorageModel;

public class ImageMergeActivity extends AppCompatActivity {

    Drawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_merge);
        Bundle extras = getIntent().getExtras();

        String mainImagePath = extras.getString("main_imagepath");
        String overlayImage = extras.getString("overlay_image");


        if(overlayImage.equals("green_hat")) {
            this.drawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.green_hat, null);
        }

        ImageView image = (ImageView) findViewById(R.id.imageView);

        Bitmap bitmapMain = BitmapFactory.decodeFile(mainImagePath);
        Bitmap bitmapOverlay = drawableToBitmap(this.drawable);
        Bitmap scaledbitmapOverlay = Bitmap.createScaledBitmap(bitmapOverlay, 400, 400, true);

        Bitmap MergedImages = Bitmap.createBitmap(bitmapMain.getWidth(), bitmapMain.getHeight(),  bitmapMain.getConfig());

        Canvas canvas = new Canvas(MergedImages);

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
        paint.setTextSize(35);

        String text = "Some random text";

        paint.getTextBounds(text, 0, text.length(), bounds);

        text_height =  bounds.height();
        text_width =  bounds.width();

        canvas.drawText("Enter Amount",
                (bitmapMain.getWidth() - text_width) / 2,
                text_height,
                paint);

        image.setImageBitmap(MergedImages);

        StorageModel storageModel = new StorageModel();
        storageModel.createFile(MergedImages);
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