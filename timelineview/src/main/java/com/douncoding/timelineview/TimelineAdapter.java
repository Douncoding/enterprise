package com.douncoding.timelineview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douncoding.timelineview.exception.TimeLineRuntimeException;

/**
 *
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineHolder>{
    @LayoutRes private int contentLayoutRes;

    public TimelineAdapter(@LayoutRes int contentLayoutRes) {
        this.contentLayoutRes = contentLayoutRes;
        if (this.contentLayoutRes <= 0) {
            throw new TimeLineRuntimeException("ContentLayout 할당 예외발생:");
        }
    }

    @Override
    public TimelineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewGroup itemView = new TimelineItemView(parent.getContext(), contentLayoutRes);
        return new TimelineHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimelineHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
