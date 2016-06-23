package com.douncoding.enterprise.internal.di.modules;

import android.content.Context;

import com.douncoding.enterprise.AndroidApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesApplication() {
        return this.application;
    }
}
