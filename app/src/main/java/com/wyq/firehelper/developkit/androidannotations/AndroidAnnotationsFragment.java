package com.wyq.firehelper.developkit.androidannotations;

import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseFragment;

import butterknife.BindView;

public class AndroidAnnotationsFragment extends BaseFragment {

    @BindView(R.id.activity_developkit_androidannotations_tv_1)
    public TextView textView;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.developkit_activity_android_annotations_layout,container,false);
//        initData();
//        initView();
//        return view;
//    }

    @Override
    public int attachLayoutRes() {
        return R.layout.developkit_activity_android_annotations_layout;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }
}
