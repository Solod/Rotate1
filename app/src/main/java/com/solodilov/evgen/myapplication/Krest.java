package com.solodilov.evgen.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;


public class Krest extends View implements View.OnTouchListener {
    ACTION action;
    String str = "TA-DAAAA";
    Paint p;
    Path path;
    RectF rectF;
    Matrix matrix;
    private float startDegree;
    private float endDegree;
    private float rotate;

    public Krest(Context context) {
        super(context);
        p = new Paint();
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        p.setTextSize(60);
        rectF = new RectF(300, 300, 600, 800);
        path = new Path();
        matrix = new Matrix();
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawARGB(80, 102, 204, 255);
        path.reset();
        path.addRect(rectF, Path.Direction.CW);
        path.addCircle(rectF.right, rectF.bottom, 50, Path.Direction.CW);
        matrix.reset();
        matrix.setRotate(rotate, rectF.centerX(), rectF.centerY());
        path.transform(matrix);
        canvas.restore();
        canvas.drawTextOnPath(str, path, 0, rectF.height() / 2, p);
        canvas.drawPath(path, p);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
if()
                startDegree = getDegree(rectF, event);
                break;
            case MotionEvent.ACTION_MOVE:
                float eventDegree = getDegree(event);
                rotate = endDegree - startDegree + eventDegree;
                break;
            case MotionEvent.ACTION_UP:
                endDegree = rotate;
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
        double radians = Math.atan2(delta_y, delta_x);//
        double degrees = Math.toDegrees(radians);
        return (float) degrees;
    }

    private boolean isCircle(PointF point) {
        float dx = point.x - rectF.right;
        float dy = point.y - rectF.left;

        return Math.sqrt(dx * dx + dy * dy) <= 50;
    }

    enum ACTION {
        RECT, CIRCLE
    }
}

