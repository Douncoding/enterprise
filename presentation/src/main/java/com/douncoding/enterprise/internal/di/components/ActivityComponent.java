package com.douncoding.enterprise.internal.di.components;

import android.app.Activity;

import com.douncoding.enterprise.internal.di.PerActivity;
import com.douncoding.enterprise.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 *
 */
@PerActivity
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();
}
