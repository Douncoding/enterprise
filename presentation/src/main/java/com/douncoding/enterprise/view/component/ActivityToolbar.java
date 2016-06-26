package com.douncoding.enterprise.view.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.douncoding.enterprise.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ActivityToolbar extends Toolbar {

    ImageView mMenuButton;

    public ActivityToolbar(Context context) {
        super(context);

        init();
    }

    public ActivityToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setContentInsetsAbsolute(0, 0);

        View contentView = LayoutInflater.from(getContext())
                .inflate(R.layout.content_toolbar_basic, this, false);

        mMenuButton = (ImageView)contentView.findViewById(R.id.toolbar_menu_btn);
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                mMenuButton.startAnimation(animation);
            }
        });

        addView(contentView);
    }
}
