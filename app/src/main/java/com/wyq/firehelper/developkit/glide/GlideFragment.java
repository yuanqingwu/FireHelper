package com.wyq.firehelper.developkit.glide;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

import butterknife.BindView;

public class GlideFragment extends BaseCaseFragment {

    @BindView(R.id.activity_developkit_glide_img_1)
    public ImageView imageView;
    @BindView(R.id.activity_developkit_glide_tv)
    public TextView textView;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.developkit_activity_glide_layout,container,false);
//        initData();
//        initView();
//        return view;
//    }

    @Override
    public int attachLayoutRes() {
        return R.layout.developkit_activity_glide_layout;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView(View view) {
        Glide.with(getActivity()).load("https://github.com/bumptech/glide/raw/master/static/glide_logo.png")
                .into(imageView);
    }

    @Override
    public String[] getArticleFilters() {
        return new String[]{"Glide"};
    }

    @Override
    public String getToolBarTitle() {
        return "Glide";
    }
}
