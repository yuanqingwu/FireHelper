package com.wyq.firehelper.ui.android.transition;

import android.support.transition.Scene;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class TransitionFragment extends BaseCaseFragment {

    @BindView(R.id.ui_activity_transition_bt)
    public Button changeBt;

    private Scene scene1;
    private Scene scene2;

    private ViewGroup mSceneRoot;
    private TransitionManager mTransitionManager;

    private boolean flag = true;


    @Override
    public String[] getArticleFilters() {
        return new String[]{"Transition"};
    }

    @Override
    public String getToolBarTitle() {
        return "Transition";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_transition;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        mSceneRoot = (ViewGroup)view.findViewById(R.id.ui_activity_transition_scene_root);
        scene1 = new Scene(mSceneRoot,(ViewGroup)mSceneRoot.findViewById(R.id.ui_activity_transition_scene_container));
        scene2 = Scene.getSceneForLayout(mSceneRoot,R.layout.ui_activty_transition_scene_2,getContext());

        mTransitionManager = TransitionInflater.from(getContext()).inflateTransitionManager(R.transition.changebounds_translate_manager,mSceneRoot);

        //default
        TransitionManager.go(scene1);

    }

    @OnClick(R.id.ui_activity_transition_bt)
    public void onClick(){
        mTransitionManager.transitionTo(flag?scene2:scene1);

        flag = !flag;
    }
}
