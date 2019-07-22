package com.example.explosion.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ExplosionField extends View {

    private SimpleParticleFactory factory;
    private SimpleParticle[][] simpleParticles;
    private float factor;
    private Rect wideRect;

    private final int EXPLODE_DURATION = 1000;

    public ExplosionField(Context context) {
        this(context, null);
    }

    public ExplosionField(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExplosionField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void attachParentView(ViewGroup viewGroup) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(this, layoutParams);
    }

    public void observe(View v) {
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                explode(v);
            }
        });
    }

    private void init() {
        factory = new SimpleParticleFactory();
    }

    private float a = 0.05f;//震动幅度

    private void explode(final View v) {
        ValueAnimator animator = new ValueAnimator().ofFloat(0f, 1.0f).setDuration(100);
        final int width = v.getWidth();
        final int height = v.getHeight();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationX((float) ((Math.random() - 0.5) * width * a));
                v.setTranslationY((float) ((Math.random() - 0.5) * height * a));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束时恢复原位置
                v.setTranslationX(0);
                v.setTranslationY(0);
                explode2(v);
            }
        });
        animator.start();
    }

    private void explode2(final View v) {
        Rect rect = new Rect();
        v.getGlobalVisibleRect(rect);

        wideRect = new Rect();
        getGlobalVisibleRect(wideRect);
        rect.left -= wideRect.left;
        rect.right -= wideRect.left;
        rect.top -= wideRect.top;
        rect.bottom -= wideRect.top;

        v.animate().scaleX(0).scaleY(0).alpha(0).setDuration(100);
        v.animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                v.animate().setListener(null);
//                v.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(100).start();
            }
        });

        simpleParticles = factory.generateParticles(Utils.createBitmapFromView(v), rect);
        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0f, 1.0f).setDuration(EXPLODE_DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                factor = (float) animation.getAnimatedValue();
                v.setScaleX((1f-factor)/10);
                v.setScaleY((1f-factor)/10);
                v.setAlpha((1f-factor)/10);
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.animate().setListener(null);
                v.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(100).start();
            }
        });

        //两组动画一起开始
//        v.animate().start();
        valueAnimator.start();
    }

    private final Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (simpleParticles != null) {
            for (SimpleParticle[] simpleParticle : simpleParticles) {
                for (SimpleParticle particle : simpleParticle) {
                    particle.calculate(factor);
                    particle.draw(canvas, paint);
                }
            }
        }
    }
}
