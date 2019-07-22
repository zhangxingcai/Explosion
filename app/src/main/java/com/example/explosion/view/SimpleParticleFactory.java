package com.example.explosion.view;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.jetbrains.annotations.NotNull;

public class SimpleParticleFactory extends ParticleFactory {
    private int radious2 = 50;//粒子初始直径
    private final int BOUND = 100;

    @NotNull
    @Override
    public SimpleParticle[][] generateParticles(@NotNull Bitmap bitmap, @NotNull Rect rect) {
        int width = rect.width();
        int height = rect.height();
        int rowNumber = height / radious2;
        int columnNumber = width / radious2;
        SimpleParticle[][] res = new SimpleParticle[rowNumber][];
        for (int i = 0; i < rowNumber; i++) {
            res[i] = new SimpleParticle[columnNumber];
            for (int j = 0; j < columnNumber; j++) {
                int cx = (int) (radious2 * (j + 0.5));
                int cy = (int) (radious2 * (i + 0.5));
                int color = bitmap.getPixel(cx, cy);
                res[i][j] = new SimpleParticle(radious2 / 2, color, cx + rect.left, cy + rect.top, BOUND);
            }
        }
        return res;
    }
}
