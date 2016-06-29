package com.douncoding.enterprise.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.douncoding.enterprise.R;
import com.douncoding.enterprise.view.fragment.ProfileDetailFragment;
import com.douncoding.enterprise.view.fragment.ProfileListFragment;

import butterknife.ButterKnife;

public class ProfileDetailActivity extends BaseActivity  {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ProfileDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            addFragment(R.id.content_container, new ProfileDetailFragment());
        }
    }

}
