package com.douncoding.enterprise.view.fragment;

import android.app.Fragment;

import com.douncoding.enterprise.internal.di.HasComponent;

/**
 * Base class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Gets a component for dependency injection by its type.
     */
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
