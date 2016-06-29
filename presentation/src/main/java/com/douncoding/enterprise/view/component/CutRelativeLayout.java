package com.douncoding.enterprise.view.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * 사선을 가진 ViewGroup
 */
public class CutRelativeLayout extends RelativeLayout {
    private Paint paint;
    private Xfermode pdMode;
    private Path path;

    public CutRelativeLayout(Context context) {
        super(context);
        init();
    }

    public CutRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pdMode  = new PorterDuffXfermode(PorterDuff.Mode.CLEAR); // 지우개
        path = new Path();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);

        paint.setXfermode(pdMode);
        path.moveTo(getWidth(), getHeight());
        path.lineTo(0, getHeight() - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        path.lineTo(0, getHeight());
        path.close();
        canvas.drawPath(path, paint);

        canvas.restoreToCount(saveCount);
        paint.setXfermode(null);
    }
}
