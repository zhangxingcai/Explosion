package com.example.explosion.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SimpleParticle {


    private int radious;
    private Random random = new Random();
    private int color;
    private float alpha;
    private int cx, cy;//相对于本view的原点坐标
    private int bound;

    public SimpleParticle(int radious, int color, int cx, int cy, int bound) {
        this.radious = radious;
        this.color = color;
        this.cx = cx;
        this.cy = cy;
        this.bound = bound;
    }

    public void draw(@NotNull Canvas canvas, @NotNull Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(cx, cy, radious, paint);
    }

    public void calculate(float factor) {
        cx = (int) (cx + random.nextInt(bound) * factor * (random.nextFloat() - 0.5f));
        cy = (int) (cy + random.nextInt(bound) * factor * (random.nextFloat() - 0.5f));
        //粒子变小变透明
        radious -= factor * 10;

        alpha = (1f - factor) * (1f + random.nextFloat());
    }


}
