package com.douncoding.timelineview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.TypedValue;

public class Line {
    private float density;

    private Paint paint;
    private Point startPoint;
    private Point endPoint;

    private int strokeWidth;
    private int width;
    private int height;

    public Line(Context context) {
        this.density = Resources.getSystem().getDisplayMetrics().density;

        // default paint config.
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        setStrokeWidth(10);

        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data, new int[] {
                R.attr.colorPrimary
        });
        this.paint.setColor(typedArray.getColor(0, 0));
    }

    public void setStrokeWidth(int width) {
        this.strokeWidth = (int)density * width;
        paint.setStrokeWidth(strokeWidth);
    }

    public void setStartPoint(int x, int y) {
        this.startPoint = new Point(x, y);
    }

    public void setEndPoint(int x, int y) {
        this.endPoint = new Point(x, y);
    }

    public void setColor(int color) {
        this.paint.setColor(color);
    }

    public Bitmap toBitmap() {
        width = strokeWidth;
        height = endPoint.y - startPoint.y;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(
                startPoint.x,
                startPoint.y,
                endPoint.x - startPoint.x,
                endPoint.y - startPoint.y, paint);
        return bitmap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
