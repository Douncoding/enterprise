package com.douncoding.timelineview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;

/** *
 * {@link EventView}의 포함되는 원형 아이콘 생성
 */
public class Marker {
    private float density;

    private int radius;  // 반지음
    private int width;   // 원지름
    private int height;  // 세로크기(margin*2 + radius*2)
    private int margin;  // 여백
    private Paint paint;

    public Marker(Context context) {
        this.density = Resources.getSystem().getDisplayMetrics().density;

        // default radius(dp)
        this.setRadius(12);

        // default margin(dp)
        this.setMargin(4);

        // Default paint color is 'colorPrimary' resource.
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        TypedValue typedValue = new TypedValue();
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data, new int[] {
                R.attr.colorPrimary
        });
        this.paint.setColor(typedArray.getColor(0, 0));
    }

    public void setRadius(int radius) {
        this.radius = (int) density * radius;
        this.width = this.radius * 2;
        this.height = (this.radius * 2) + margin;
    }

    public void setColor(int color) {
        this.paint.setColor(color);
    }

    public void setMargin(int margin) {
        this.margin = margin;
        this.height += (margin * density);
    }

    public int getRadius() {
        return radius;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMargin() {
        return margin;
    }

    public Bitmap toBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(width/2, height/2, radius, paint);
        Log.e("CHECK", width/2 + "//" + height/2 + "//" + radius);
        return bitmap;
    }
}
