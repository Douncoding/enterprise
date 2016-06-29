package com.douncoding.enterprise.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douncoding.enterprise.R;
import com.douncoding.enterprise.view.component.SnappingHorizontalListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Profile 정보를 나타내기 위한 Fragment
 *
 */
public class ProfileListFragment extends BaseFragment {

    /**
     * Interface for listening user list events.
     */
    private ProfileListListener profileListListener;
    public interface ProfileListListener {
        void onProfileClicked();
    }

    @BindView(R.id.profile_list)
    SnappingHorizontalListView mSnappingHorizontalListView;

    public ProfileListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileListListener) {
            this.profileListListener = (ProfileListListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile_list, container, false);
        ButterKnife.bind(this, rootView);

        setupListView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        profileListListener = null;
    }

    private void setupListView() {
        mSnappingHorizontalListView.setOnClickListener(new SnappingHorizontalListView.OnPageListener() {
            @Override
            public void onClick(int position, View view) {
                if (profileListListener != null)
                    profileListListener.onProfileClicked();
            }
        });
    }
}
