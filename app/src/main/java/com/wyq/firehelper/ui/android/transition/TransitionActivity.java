package com.wyq.firehelper.ui.android.transition;

import android.support.transition.Scene;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import butterknife.BindView;

public class TransitionActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ui_activity_transition_bt)
    public Button changeBt;

    private Scene scene1;
    private Scene scene2;

    private ViewGroup mSceneRoot;
    private TransitionManager mTransitionManager;

    private boolean flag = true;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_transition;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        changeBt.setOnClickListener(this);

        mSceneRoot = (ViewGroup)findViewById(R.id.ui_activity_transition_scene_root);
        scene1 = new Scene(mSceneRoot,(ViewGroup)mSceneRoot.findViewById(R.id.ui_activity_transition_scene_container));
        scene2 = Scene.getSceneForLayout(mSceneRoot,R.layout.ui_activty_transition_scene_2,this);

         mTransitionManager = TransitionInflater.from(this).inflateTransitionManager(R.transition.changebounds_translate_manager,mSceneRoot);

         //default
         TransitionManager.go(scene1);


    }

    @Override
    public void onClick(View v) {
        //automatic transition
//        TransitionManager.go(flag?scene2:scene1);

        //custom TransitionManager
        mTransitionManager.transitionTo(flag?scene2:scene1);

        flag = !flag;


    }
}
