package com.wyq.firehelper.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.ui.layout.drag.DragLayoutActivity;
import com.wyq.firehelper.ui.layout.tangram.TangramActivity;
import com.wyq.firehelper.ui.layout.ultraViewPager.UltraViewPagerActivity;

import butterknife.BindView;

public class UiMainActivity extends BaseActivity {

    /**
     * FlexboxLayout
     * PhotoView
     */
    private String[] items = {"MetroStyle[MyImageView]", "ZoomImageView[MyZoomImageView]", "CircleBlurImageView[MyCircleImageView]", "DragLayout", "Tangram", "UltraViewPager"};

    @BindView(R.id.home_list)
    public ListView homeList;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_main;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "UI", true);
    }

    @Override
    public void initView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                UiMainActivity.this, android.R.layout.simple_list_item_1,
                items);
        homeList.setAdapter(adapter);
        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(UiMainActivity.this,
                                MetroStyleActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(UiMainActivity.this,
                                ZoomImageViewActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(UiMainActivity.this,
                                CircleBlurImageViewActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(UiMainActivity.this,
                                DragLayoutActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(UiMainActivity.this,
                                TangramActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(UiMainActivity.this,
                                UltraViewPagerActivity.class));
                        break;
                    default:
                        break;
                }

            }
        });
    }

}
