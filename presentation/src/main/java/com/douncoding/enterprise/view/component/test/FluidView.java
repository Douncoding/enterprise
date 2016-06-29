package com.douncoding.enterprise.view.component.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.douncoding.enterprise.R;

/**
 *
 */
public class FluidView extends ViewGroup {
    private ViewDragHelper mDragHelper;

    private View mHeaderView;
    private View mDescView;

    private float mInitialMotionX;
    private float mInitialMotionY;

    private int mDragRange;
    private int mTop;
    private float mDragOffset;

    public FluidView(Context context) {
        super(context);
        init();
    }

    public FluidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 뷰가 생성되고 자식을 바인드
        //mHeaderView = findViewById(R.id.header);
        //mDescView = findViewById(R.id.desc);
    }

    /**
     * 자식 뷰의 위치를 배치
     * onMeasure(자식 뷰의 크기 측정) 다음 호출
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDragRange = getHeight() - mHeaderView.getHeight();

        mHeaderView.layout(
                0,
                mTop,
                r,
                mTop + mHeaderView.getMeasuredHeight());

        mDescView.layout(
                0,
                mTop + mHeaderView.getMeasuredHeight(),
                r,
                mTop  + b);
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);

        if (mDragHelper.smoothSlideViewTo(mHeaderView, mHeaderView.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    /**
     * 스크롤 시 호출
     * {@link ViewDragHelper#settleCapturedViewAt(int, int)} 호출 시 애니메이션이 지속 되도록 함
     * 지금은 애니메이션이 없어서 그런가? 없어도 동작과는 무관...
     */
    @Override
    public void computeScroll() {

//        if (mDragHelper.continueSettling(true)) {
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
    }

    /**
     * 모든 터치 동작 이벤트들을 가채기 위한다면 이 함수를 구현하면된다. 이 함수를 구현하면 당신은 당신의
     * 자식들에게 이벤트가 보내지는 것을 지켜 볼 수 있다. 그리고 어느 시점이든 현재 제스처에 대한 소유권을
     * 가질 수 있다.
     * 1. DOWN 이벤트 수신
     * 2. 자식 뷰또는 onTouchEvent() 전달
     * @param ev
     * @return true 인 경우 자식들로부터의 이벤트를 훔친다. false를 리턴하면 계속 이함수로 이벤트를 받아서
     * 검사하겠다는 의미
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        if (action != MotionEvent.ACTION_DOWN) {
            mDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        }

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }

        final float x = ev.getX();
        final float y = ev.getY();

        // 눌려진 위치가 HeaderView 에 포함되는지 확인
        boolean isHeaderViewUnder = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                // 시작지점.
                mInitialMotionX = x;
                mInitialMotionY = y;
                break;
            }

            case MotionEvent.ACTION_UP: {
                // 이동한 만큼의 위치
                final float dx = x - mInitialMotionX;
                final float dy = y - mInitialMotionY;
                // 드래그로 인식하기 위한 최소 값?
                final int slop = mDragHelper.getTouchSlop();
                if (dx * dx + dy * dy < slop * slop && isHeaderViewUnder) {
                    if (mDragOffset == 0) {
                        smoothSlideTo(1f);
                    } else {
                        smoothSlideTo(0f);
                    }
                }
                break;
            }
        }

        return isHeaderViewUnder && isViewHit(mHeaderView, (int) x, (int) y) || isViewHit(mDescView, (int) x, (int) y);
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() &&
                screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mHeaderView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop = top;

            mDragOffset = (float) top/mDragRange;
            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);
            mHeaderView.setScaleY(1 - mDragOffset / 2);

            mDescView.setAlpha(1 - mDragOffset);
            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mHeaderView.getHeight() - mHeaderView.getPaddingBottom();

            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }
    }
}
