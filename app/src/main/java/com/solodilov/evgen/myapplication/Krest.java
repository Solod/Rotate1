package com.solodilov.evgen.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class Krest extends View implements View.OnTouchListener {
    float x;
    float y;
    float startPointX;
    float startPointY;
    Paint p;
    Path path;
    RectF rectF;
    Matrix matrix;
    private float startDegree;

    public Krest(Context context) {
        super(context);
        p = new Paint();
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        rectF = new RectF(300, 300, 600, 600);
        path = new Path();
        matrix = new Matrix();
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(80, 102, 204, 255);
        canvas.drawPath(path, p);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPointX = x;
                startPointY = y;
                startDegree = getDegree(rectF, event);
                path.reset();
                path.addRect(rectF, Path.Direction.CW);
                break;
            case MotionEvent.ACTION_MOVE:
                float eventDegree = getDegree(event);
                startPointX = x;
                startPointY = y;
                matrix.reset();
                Log.d("Rott ", String.valueOf(startDegree - eventDegree));
                matrix.setRotate(eventDegree - startDegree, rectF.centerX(), rectF.centerY());
                startDegree = eventDegree;
                Log.d("Rott!!! ", String.valueOf(startDegree));
                path.transform(matrix);
                break;
        }
        invalidate();
        return true;
    }

    private float getDegree(MotionEvent event) {
        return rectF != null ? getDegree(rectF, event) : 0;
    }

    private float getDegree(RectF rect, MotionEvent event) {
        return rect != null ? getDegree(rect.centerX(), rect.centerY(), event.getX(), event.getY()) : 0;
    }

    private float getDegree(float x1, float y1, float x2, float y2) {

        double delta_x = (x1 - x2);
        double delta_y = (y1 - y2);
        double radians = Math.atan2(delta_y, delta_x);
        Log.d("Radians: ", String.valueOf(radians));
        double degrees = Math.toDegrees(radians);
        Log.d("Degrees: ", String.valueOf(degrees));
        return (float) degrees;
    }
}
