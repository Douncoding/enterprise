package com.douncoding.enterprise.view.activity;

import android.os.Bundle;
import com.douncoding.enterprise.R;
import com.douncoding.enterprise.view.component.FlowingView;
import com.douncoding.enterprise.view.component.PullDragButton;


public class DesignActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        setActionbar(R.id.toolbar);


        FlowingView sv = (FlowingView)findViewById(R.id.sv);
        PullDragButton btn = (PullDragButton)findViewById(R.id.drag);
        btn.setFlowingView(sv);

        /*
        if (savedInstanceState == null) {
            addFragment(R.id.content_container, new ProfileListFragment());
        }
        */
    }
}
