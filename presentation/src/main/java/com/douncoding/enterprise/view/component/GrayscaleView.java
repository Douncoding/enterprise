package com.douncoding.enterprise.view.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *
 */
public class GrayscaleView extends ImageView {

    public GrayscaleView(Context context) {
        super(context);
        init();
    }

    public GrayscaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
