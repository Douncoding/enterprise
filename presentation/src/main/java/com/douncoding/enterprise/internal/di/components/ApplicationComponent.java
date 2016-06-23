package com.douncoding.enterprise.internal.di.components;

import android.content.Context;

import com.douncoding.enterprise.internal.di.modules.ApplicationModule;
import com.douncoding.enterprise.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    Context context();
}
