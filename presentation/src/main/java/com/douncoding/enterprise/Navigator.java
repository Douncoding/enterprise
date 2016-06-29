package com.douncoding.enterprise;

import android.content.Context;
import android.content.Intent;

import com.douncoding.enterprise.view.activity.ProfileDetailActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 액티비티 간 호출 관리
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {}

    public void navigateToProfileDetail(Context context) {
        if (context != null) {
            Intent intentToLaunch = ProfileDetailActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
