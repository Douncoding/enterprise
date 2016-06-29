package com.douncoding.enterprise.view.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.douncoding.enterprise.AndroidApplication;
import com.douncoding.enterprise.Navigator;
import com.douncoding.enterprise.internal.di.components.ApplicationComponent;
import com.douncoding.enterprise.internal.di.modules.ActivityModule;

import javax.inject.Inject;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 매개변수가 없는 생성자를 제공하는 클래스는 바로(?) 주입이 가능하다
     */
    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected ActionBar setActionbar(@IdRes int resId) {
        setSupportActionBar((Toolbar)findViewById(resId));
        return getSupportActionBar();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
