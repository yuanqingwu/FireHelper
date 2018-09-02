package com.wyq.firehelper.ui.layout.tangram;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.structure.viewcreator.ViewHolderCreator;
import com.tmall.wireless.tangram.support.SimpleClickSupport;
import com.tmall.wireless.tangram.util.IInnerImageSetter;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

public class TangramActivity extends BaseActivity {

    @BindView(R.id.ui_activity_tangram_recycle_view)
    public RecyclerView recyclerView;

    private TangramEngine engine;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_tangram_layout;
    }

    @Override
    public void initToolBar() {

    }

    public void initView() {
        //1.引入依赖
        //2.初始化 Tangram 环境
        IInnerImageSetter iInnerImageSetter = new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
//                Glide.with(getApplicationContext()).load(url).into(view);
            }
        };
        TangramBuilder.init(getApplicationContext(),iInnerImageSetter , ImageView.class);

        //3.初始化 TangramBuilder
        //这一步 builder 对象生成的时候，内部已经注册了框架所支持的所有组件和卡片，以及默认的IAdapterBuilder（它被用来创建 绑定到 RecyclerView 的Adapter）
        TangramBuilder.InnerBuilder builder = TangramBuilder.newInnerBuilder(this);

        //4.注册自定义的卡片和组件
        // recommend to use string type to register component
        builder.registerCell("testView", TestView.class);
        builder.registerCell("singleImgView", SimpleImgView.class);
        builder.registerCell("ratioTextView", RatioTextView.class);

        // register component with integer type was not recommend to use
        builder.registerCell(1, TestView.class);
        builder.registerCell(10, SimpleImgView.class);
        builder.registerCell(2, SimpleImgView.class);
        builder.registerCell(4, RatioTextView.class);
        builder.registerCell(110,
                TestViewHolderCell.class,
                new ViewHolderCreator<>(R.layout.item_holder, TestViewHolder.class, TextView.class));
        builder.registerCell(199,SingleImageView.class);
        builder.registerVirtualView("vvtest");

        //5.生成TangramEngine实例
        engine = builder.build();

        engine.setVirtualViewTemplate(VVTEST.BIN);
        engine.setVirtualViewTemplate(DEBUG.BIN);

        //6.绑定业务 support 类到 engine
        engine.register(SimpleClickSupport.class,new SampleClickSupport());

        //7.绑定 recyclerView
        engine.bindView(recyclerView);

        //8.监听 recyclerView 的滚动事件
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //在 scroll 事件中触发 engine 的 onScroll，内部会触发需要异步加载的卡片去提前加载数据
                engine.onScrolled();
            }
        });

        //9.设置悬浮类型布局的偏移（可选）
        //如果你的 recyclerView 上方还覆盖有其他 view，比如底部的 tabbar 或者顶部的 actionbar，为了防止悬浮类 view 和这些外部 view 重叠，可以设置一个偏移量。
        engine.getLayoutManager().setFixOffset(0, 40, 0, 0);

        //10.设置卡片预加载的偏移量（可选）
        //在页面滚动过程中触发engine.onScrolled()方法，会去寻找屏幕外需要异步加载数据的卡片，默认往下寻找5个，让数据预加载出来，可以修改这个偏移量。
        engine.setPreLoadNumber(3);

        //11.加载数据并传递给 engine
        String json = new String(getAssertsFile(this, "data.json"));
        JSONArray data = null;
        try {
            data = new JSONArray(json);
            engine.setData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.destroy();
        }
    }

    public byte[] getAssertsFile(Context context, String fileName) {
        InputStream inputStream = null;
        AssetManager assetManager = context.getAssets();
        try {
            inputStream = assetManager.open(fileName);
            if (inputStream == null) {
                return null;
            }

            BufferedInputStream bis = null;
            int length;
            try {
                bis = new BufferedInputStream(inputStream);
                length = bis.available();
                byte[] data = new byte[length];
                bis.read(data);

                return data;
            } catch (IOException e) {

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception e) {

                    }
                }
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
