package com.example.explosion.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class Utils {

    public static Bitmap createBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
