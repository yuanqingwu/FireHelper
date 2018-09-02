package com.wyq.firehelper.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.utils.FireUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class UiMainActivity extends BaseActivity {

    @BindView(R.id.ui_activity_main_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.ui_activity_main_viewpager)
    public ViewPager viewPager;
    @BindView(R.id.ui_activity_main_tablayout)
    public TabLayout tabLayout;

//    public String[] uiType = new String[]{"layout", "weight", "anim"};

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_main_layout;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "UI", true);
    }

    @Override
    public void initView() {
        for (UiBean type : getUiName()) {
            tabLayout.addTab(tabLayout.newTab().setText(type.getCategory()));
        }

        UiViewPagerAdapter pagerAdapter = new UiViewPagerAdapter(getSupportFragmentManager(), getUiName());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private List<UiBean> getUiName() {
        List<UiBean> uiBeanList = new ArrayList<>();

        String uiJson = FireUtils.readAssets2String(this, "ui.json");
        JsonArray jsonArray = new Gson().fromJson(uiJson, JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            UiBean uiBean = new Gson().fromJson(jsonArray.get(i), UiBean.class);
            uiBeanList.add(uiBean);
        }
        return uiBeanList;
    }

//    /**
//     * FlexboxLayout
//     * PhotoView
//     */
//    @Override
//    public String[] listItemsNames() {
//        return new String[]{"MetroStyle[MyImageView]", "ZoomImageView[MyZoomImageView]", "CircleBlurImageView[MyCircleImageView]", "DragLayout", "Tangram", "UltraViewPager"};
//    }
//
//    @Override
//    public String toolBarName() {
//        return "UI";
//    }
//
//    @Override
//    public boolean isShowBackIcon() {
//        return true;
//    }
//
//    @Override
//    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
//        return new TvRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                switch (position) {
//                    case 0:
//                        startActivity(new Intent(UiMainActivity.this,
//                                MetroImageViewActivity.class));
//                        break;
//                    case 1:
//                        startActivity(new Intent(UiMainActivity.this,
//                                ZoomImageViewActivity.class));
//                        break;
//                    case 2:
//                        startActivity(new Intent(UiMainActivity.this,
//                                CircleImageViewActivity.class));
//                        break;
//                    case 3:
//                        startActivity(new Intent(UiMainActivity.this,
//                                DragLayoutActivity.class));
//                        break;
//                    case 4:
//                        startActivity(new Intent(UiMainActivity.this,
//                                TangramActivity.class));
//                        break;
//                    case 5:
//                        startActivity(new Intent(UiMainActivity.this,
//                                UltraViewPagerActivity.class));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
//    }


}
