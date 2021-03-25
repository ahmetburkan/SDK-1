package com.app.softwaredevelopmentpraktijk1.Model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class CanvasModel {
    public Bitmap finalBitmap;
    private final Bitmap overlayBitmap;
    private final Bitmap backgroundBitmap;

    public CanvasModel(Bitmap backgroundBitmap, Bitmap overlayBitmap) {
        this.backgroundBitmap = backgroundBitmap;
        this.overlayBitmap = Bitmap.createScaledBitmap(overlayBitmap, 400, 400, true);
        createCanvas(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), backgroundBitmap.getConfig());
    }

    public void createCanvas(int width, int height, Bitmap.Config config) {
        this.finalBitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(this.finalBitmap);

        canvas.drawBitmap(this.backgroundBitmap, 0, 0, null);
        canvas.drawBitmap(overlayBitmap,
                (this.backgroundBitmap.getWidth() - overlayBitmap.getWidth()) / 2,
                (this.backgroundBitmap.getHeight() - overlayBitmap.getHeight()) / 5,
                null);

        Paint paint = new Paint();
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(60);

        Rect bounds = new Rect();
        String text = new DateModel().getMonthString();
        paint.getTextBounds(text, 0, text.length(), bounds);
        paint.setColor(Color.WHITE);

        canvas.drawText(text,
                (this.backgroundBitmap.getWidth() - bounds.width()) / 2,
                this.backgroundBitmap.getHeight() - bounds.height(),
                paint);
    }
}
