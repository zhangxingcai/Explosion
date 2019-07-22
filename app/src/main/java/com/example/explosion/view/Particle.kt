package com.example.explosion.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

abstract class Particle(val cx: Int, val cy: Int, val color: Int) {

    var alpha:Float = 0f

    fun advance(canvas: Canvas, paint: Paint, factor: Int) {

    }

    abstract fun draw(canvas: Canvas,paint: Paint)
}