package com.wyq.firehelper.ui.android.transition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.databinding.UiActivityTransitionBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.Scene;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;
import androidx.viewbinding.ViewBinding;

public class TransitionFragment extends BaseCaseFragment {

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
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiActivityTransitionBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        mSceneRoot = view.findViewById(R.id.ui_activity_transition_scene_root);
        scene1 = new Scene(mSceneRoot, mSceneRoot.findViewById(R.id.ui_activity_transition_scene_container));
        scene2 = Scene.getSceneForLayout(mSceneRoot,R.layout.ui_activty_transition_scene_2,getContext());

        mTransitionManager = TransitionInflater.from(getContext()).inflateTransitionManager(R.transition.changebounds_translate_manager,mSceneRoot);

        //default
        TransitionManager.go(scene1);

        ((UiActivityTransitionBinding)binding).uiActivityTransitionBt.setOnClickListener(v -> {
            mTransitionManager.transitionTo(flag?scene2:scene1);

            flag = !flag;
        });

    }
}
