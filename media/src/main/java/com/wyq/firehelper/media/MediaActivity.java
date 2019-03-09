package com.wyq.firehelper.media;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wyq.firehelper.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.CaseActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.base.navigation.NavigationManager;
import com.wyq.firehelper.media.opengles.OpenGLESActivity;
import com.wyq.firehelper.media.video.VideoFragment;

import java.util.List;

@Route(path = NavigationManager.NAVIGATION_MEDIA_MAIN_ACTIVITY)
public class MediaActivity extends BaseRecyclerViewActivity {


    public static void instance(Context context){
        context.startActivity(new Intent(context,MediaActivity.class));
    }

    @Override
    public String[] listItemsNames() {
        return new String[]{"OpenGL ES", "audio", "video"};
    }

    @Override
    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        OpenGLESActivity.instance(MediaActivity.this);
                        break;
                    case 1:

                        break;
                    case 2:
//                        VideoActivity.instance(MediaActivity.this);
                        CaseActivity.instance(MediaActivity.this,VideoFragment.class.getName());
                        break;
                    default:
                        break;

                }
            }
        };
    }

    @Override
    public String getToolBarTitle() {
        return "Media";
    }

    @Override
    public List getArticleList() {
        return null;
    }
}
