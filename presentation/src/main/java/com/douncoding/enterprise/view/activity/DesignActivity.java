package com.douncoding.enterprise.view.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.douncoding.enterprise.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DesignActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_menu_btn) ImageView mMenuBT;

    @BindView(R.id.profile_list_container)
    ViewPager mProfileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mMenuBT = (ImageView)findViewById(R.id.toolbar_menu_btn);
        mMenuBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                mMenuBT.startAnimation(animation);
            }
        });


        ProfileAdapter mAdapter = new ProfileAdapter();
        mAdapter.addProfile("123123");
        mAdapter.addProfile("123123");
        mAdapter.addProfile("123123");
        mAdapter.addProfile("123123");
        mAdapter.addProfile("123123");

    }

    class ProfileAdapter extends PagerAdapter {
        public ProfileAdapter() {
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
