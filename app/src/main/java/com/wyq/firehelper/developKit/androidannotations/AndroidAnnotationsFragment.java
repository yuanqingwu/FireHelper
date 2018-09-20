package com.wyq.firehelper.developKit.androidannotations;

import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.developKit.DevelopKitBaseFragment;

import butterknife.BindView;

public class AndroidAnnotationsFragment extends DevelopKitBaseFragment {

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
        resourceList.put(textView, ArticleConstants.DEVKIT_INJECT_ANDROIDANNOTATIONS_0);
    }

    @Override
    public void initView() {
        browserArticle(getActivity());
    }
}
