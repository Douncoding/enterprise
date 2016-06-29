package com.douncoding.enterprise.view.activity;

import android.os.Bundle;

import com.douncoding.enterprise.R;
import com.douncoding.enterprise.view.fragment.ProfileListFragment;


public class ProfileListActivity extends BaseActivity implements
        ProfileListFragment.ProfileListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setActionbar(R.id.toolbar);

        if (savedInstanceState == null) {
            addFragment(R.id.content_container, new ProfileListFragment());
        }
    }

    @Override
    public void onProfileClicked() {
        this.navigator.navigateToProfileDetail(this);
    }
}
