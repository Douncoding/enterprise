package com.douncoding.enterprise;

import android.app.Application;

import com.douncoding.enterprise.internal.di.components.ApplicationComponent;
import com.douncoding.enterprise.internal.di.components.DaggerApplicationComponent;
import com.douncoding.enterprise.internal.di.modules.ApplicationModule;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initializeLeakDetection() {

    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
