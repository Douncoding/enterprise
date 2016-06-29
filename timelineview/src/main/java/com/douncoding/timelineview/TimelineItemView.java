package com.douncoding.timelineview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 하나의 아이템 의미하는 뷰
 */
public class TimelineItemView extends LinearLayout {
    // Measure 관련
    private static final int MIN_HEIGHT_DP = 100;
    private static final int MIN_WIDTH_DP = 100;
    private float density;

    // RealView 관련
    private EventView mEventView;
    private View mContentView;
    @LayoutRes private int contentLayoutRes;

    public TimelineItemView(Context context, @LayoutRes int contentLayoutRes) {
        super(context);
        this.contentLayoutRes = contentLayoutRes;

        init();
    }

    private void init() {
        density = Resources.getSystem().getDisplayMetrics().density;

        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(HORIZONTAL);
        setBackgroundColor(Color.LTGRAY);

        mEventView = new EventView(getContext());
        addView(mEventView);

        mContentView = LayoutInflater.from(getContext()).inflate(contentLayoutRes, this, false);
        addView(mContentView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContentView.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);

        int minHeight = (int) density * MIN_HEIGHT_DP;
        int contentHeight = mContentView.getMeasuredHeight();

        int targetHeight = contentHeight < minHeight ? minHeight : contentHeight;
        int targetWidth = View.MeasureSpec.getSize(widthMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, targetWidth, 0, targetHeight, 0);
                measureChild(child, targetWidth, targetHeight);
            }
        }

        setMeasuredDimension(targetWidth, targetHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
