package com.example.explosion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import com.example.explosion.view.ExplosionAnimator;
import com.example.explosion.view.ExplosionField;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExplosionField field = new ExplosionField(this);
        field.attachParentView((ViewGroup) findViewById(Window.ID_ANDROID_CONTENT));
        field.observe(findViewById(R.id.iv));
    }
}
