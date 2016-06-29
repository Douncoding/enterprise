package com.douncoding.timelineview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Marker 위치
 * 1. 최소 자식뷰 크기 정의 필요. (Marker + Top + Bottom)
 *
 */
public class EventView extends View {
    private Line startLine;
    private Line endLine;
    private Marker marker;

    public EventView(Context context) {
        super(context);
        init();
    }

    /**********************************************************************************************
     * API START
     **********************************************************************************************/


    /**********************************************************************************************
     * API END
     **********************************************************************************************/

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT));

        startLine = new Line(getContext());
        startLine.setStrokeWidth(3);
        startLine.setColor(Color.RED);

        endLine = new Line(getContext());
        endLine.setStrokeWidth(3);
        endLine.setColor(Color.YELLOW);

        marker = new Marker(getContext());
        marker.setRadius(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);

        // 최소높이 정의 필요

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int startHeight = 50;
        int pLeft = getWidth() / 2;
        int pTop = 0;


        startLine.setStartPoint(pLeft, 0);
        startLine.setEndPoint(pLeft, startHeight);
        canvas.drawBitmap(startLine.toBitmap(), pLeft - startLine.getWidth()/2, 0, null);

        int tmp = startHeight + marker.getMargin();
        canvas.drawBitmap(marker.toBitmap(), pLeft - marker.getWidth()/2, tmp, null);

        int tmp2 = startHeight + marker.getHeight();
        endLine.setStartPoint(pLeft, tmp2);
        endLine.setEndPoint(pLeft, getHeight());
        canvas.drawBitmap(endLine.toBitmap(), pLeft - endLine.getWidth()/2, tmp2, null);
    }
}
