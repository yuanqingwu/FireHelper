package com.wyq.firehelper.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.wyq.firehelper.base.BaseListActivity;
import com.wyq.firehelper.ui.layout.drag.DragLayoutActivity;
import com.wyq.firehelper.ui.layout.tangram.TangramActivity;
import com.wyq.firehelper.ui.layout.ultraViewPager.UltraViewPagerActivity;

public class UiMainActivity extends BaseListActivity {

    /**
     * FlexboxLayout
     * PhotoView
     */
    @Override
    public String[] listItemsNames() {
        return new String[]{"MetroStyle[MyImageView]", "ZoomImageView[MyZoomImageView]", "CircleBlurImageView[MyCircleImageView]", "DragLayout", "Tangram", "UltraViewPager"};
    }

    @Override
    public String toolBarName() {
        return "UI";
    }

    @Override
    public boolean isShowBackIcon() {
        return true;
    }

    @Override
    public AdapterView.OnItemClickListener onListItemClickListener() {
        return new AdapterView.OnItemClickListener() {
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
        };
    }
}
