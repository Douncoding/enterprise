package com.douncoding.enterprise.view.component;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.douncoding.enterprise.R;

import java.util.Locale;

/**
 *
 */
public class PullDragButton extends RelativeLayout {
    // DIPs per second
    private static final int MIN_FLING_VELOCITY = 400;

    private ViewDragHelper mDragHelper;
    private View mActionView;
    private float onScreen;

    private FlowingView mFlowingView;
    private float rightX;
    private float pointY;
    private int rightMargin;
    private boolean releasing = false;

    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public PullDragButton(Context context) {
        super(context);
        init();
    }

    public PullDragButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setFlowingView(FlowingView flowingView) {
        this.mFlowingView = flowingView;
        ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_NONE, null);
        for (int i = 0; i < getChildCount(); i++) {
            ViewCompat.setLayerType(getChildAt(i), ViewCompat.LAYER_TYPE_NONE, null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);

        MarginLayoutParams lp = (MarginLayoutParams)mActionView.getLayoutParams();

        final int drawerWidthSpec = getChildMeasureSpec(
                widthMeasureSpec,
                lp.leftMargin + lp.rightMargin,
                lp.width);
        final int drawerHeightSpec = getChildMeasureSpec(
                heightMeasureSpec,
                lp.topMargin + lp.bottomMargin,
                lp.height);

        mActionView.measure(drawerWidthSpec, drawerHeightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed, l, t, r, b);

        MarginLayoutParams lp = (MarginLayoutParams)mActionView.getLayoutParams();

        final int width = mActionView.getMeasuredWidth();
        final int heigth = mActionView.getMeasuredHeight();
        int left = -width + (int)(width * onScreen);

        mActionView.layout(
                left,
                lp.topMargin,
                left + width,
                lp.topMargin + heigth);

//        Log.e("CHECK", String.format(Locale.getDefault(), "onLayout %d %d %d %d",
//                l,t,mActionView.getMeasuredWidth(),mActionView.getMeasuredHeight()));

        if (mFlowingView != null) {
            mFlowingView.setRightMargin(100);
        }
    }

    private void setupActionView() {
        mActionView = new View(getContext());
        mActionView.setBackgroundColor(Color.RED);
        mActionView.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mActionView);
    }

    private void init() {
        setupActionView();

        //mFlowingView = new FlowingView(getContext());
        //addView(mFlowingView);

        Log.d("CHECK", "Child Count:" + getChildCount());

        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mDragHelper.setMinVelocity(dpToPx(MIN_FLING_VELOCITY));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        pointY = event.getY();
        return true;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint
                .FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(pfd);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child == null)
                return;
        }
        super.dispatchDraw(canvas);
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mActionView;
        }

        /**
         *
         * @param child
         * @param left 왼쪽끝을 0이라 할때 우측으로 드래그할 수 록 증가
         * @param dx 속도인 것처럼 보임
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            Log.e("CHECK", "clampViewPositionHorizontal:" + Math.max(-child.getWidth(), Math.min(left, 0)));
            return Math.max(-child.getWidth(), Math.min(left, 0));
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(mActionView, pointerId);
        }

        /**
         * 드래그 중이던 뷰를 놓았을 떄 이벤트
         * @param releasedChild
         * @param xvel 가로 스크롤 속도
         * @param yvel 세로 스크롤 속도
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // 관찰대상의 View의 넓이와 Left의 절대좌표의 합 = 보여지는 뷰의 넓이
            final int spaceWidth = releasedChild.getWidth() + releasedChild.getLeft();
            // 즉, 보여지는 부분의 양
            float offset = (spaceWidth) * 1.0f / releasedChild.getWidth();
            // 우측방향의 스크롤이며 50% 이상 노출되면 openMark true.
            boolean openMark = xvel >= 0 && offset > 0.5f;

            // 결론: "이전 상태로 돌아가라" 라는 액션을 취할 수 있음.
            if (!openMark) {
                //TODO 이전상태 애니메이션
                releasing = true;
                mFlowingView.resetContent();
            }

            Log.e("CHECK", "onViewReleased xvel:" + xvel + "//offset:" + offset);
            invalidate();
        }

        /**
         * 뷰가 움직이는 중의 이벤트
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            final int childWidth = changedView.getWidth();
            // 보여지는 부분의 양
            float offset = (float)(childWidth + left)/childWidth;
            onScreen = offset;

            changedView.setVisibility(offset == 0 ? View.INVISIBLE : View.VISIBLE);
            rightX = left + childWidth;

            if (mFlowingView.isStartAuto(rightX)) {
                Log.d("CHECK", "1: right:" + rightX);
                mFlowingView.autoUpping(rightX);
                if (rightX == 0) {
                    mFlowingView.resetStatus();
                    releasing = false;
                }
                return;
            }

            if (mFlowingView.isupping()) {
                Log.d("CHECK", "2");
                if (rightX == 0) {
                    mFlowingView.resetStatus();
                    releasing = false;
                }
                return;
            }

            if (!releasing) {
                Log.d("CHECK", "3");
                mFlowingView.show(rightX, pointY, FlowingView.Status.STATUS_UP);
            } else {
                Log.d("CHECK", "4");
                mFlowingView.show(rightX, pointY, FlowingView.Status.STATUS_DOWN);
                if (rightX == 0) {
                    mFlowingView.resetStatus();
                    releasing = false;
                }
            }

            invalidate();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mActionView == child ? child.getWidth() : 0;
        }
    }
}
