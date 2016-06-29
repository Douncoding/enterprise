package com.douncoding.timelineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import static com.douncoding.timelineview.Constants.logging;

/**
 *
 */
public class TimelineView extends RecyclerView {

    @LayoutRes int contentLayoutRes;
    @DimenRes float eventWidth;

    LinearLayoutManager mLayoutManager;
    TimelineAdapter mAdapter;

    public TimelineView(Context context) {
        super(context);
        init();
    }

    public TimelineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(attrs);
        init();
    }

    public TimelineView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributeSet(attrs);
        init();
    }

    private void parseAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.timelineview);
        contentLayoutRes = typedArray.getResourceId(R.styleable.timelineview_contentLayout, -1);
        eventWidth = typedArray.getDimension(R.styleable.timelineview_event_width, 0f);
        typedArray.recycle();

        if (contentLayoutRes <= 0) {
            contentLayoutRes = R.layout.item_content_default;
            logging("contentLayoutRes: " + contentLayoutRes);
        }
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new TimelineAdapter(contentLayoutRes);

        setLayoutManager(mLayoutManager);
        setAdapter(mAdapter);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(mLayoutManager);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(mAdapter);
    }
}
