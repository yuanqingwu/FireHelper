package com.wyq.firehelper.ui.layout.tangram;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.op.AppendGroupOp;
import com.tmall.wireless.tangram.op.LoadGroupOp;
import com.tmall.wireless.tangram.op.LoadMoreOp;
import com.tmall.wireless.tangram.op.ParseSingleGroupOp;
import com.tmall.wireless.tangram.op.UpdateCellOp;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.viewcreator.ViewHolderCreator;
import com.tmall.wireless.tangram.support.BannerSupport;
import com.tmall.wireless.tangram.support.RxBannerScrolledListener;
import com.tmall.wireless.tangram.support.async.CardLoadSupport;
import com.tmall.wireless.tangram.util.IInnerImageSetter;
import com.tmall.wireless.vaf.framework.VafContext;
import com.tmall.wireless.vaf.virtualview.Helper.ImageLoader;
import com.tmall.wireless.vaf.virtualview.view.image.ImageBase;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.base.utils.FireHelperUtils;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.R2;
import com.wyq.firehelper.ui.layout.tangram.rx.JSONArrayObservable;
import com.wyq.firehelper.ui.layout.tangram.rx.ViewClickObservable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class TangramActivity extends BaseActivity {

    @BindView(R2.id.ui_activity_tangram_recycle_view)
    public RecyclerView recyclerView;

    private TangramEngine engine;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private IInnerImageSetter iInnerImageSetter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_tangram_layout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        //1.引入依赖
        //2.初始化 Tangram 环境
//        iInnerImageSetter = new IInnerImageSetter() {
//            @Override
//            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
//                Picasso.get().load(url).into(view);
//            }
//        };
        TangramBuilder.init(getApplicationContext(), new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
                Picasso.get().load(url).into(view);
            }
        }, ImageView.class);

        //3.初始化 TangramBuilder
        //这一步 builder 对象生成的时候，内部已经注册了框架所支持的所有组件和卡片，以及默认的IAdapterBuilder（它被用来创建 绑定到 RecyclerView 的Adapter）
        TangramBuilder.InnerBuilder builder = TangramBuilder.newInnerBuilder(this);

        //4.注册自定义的卡片和组件
        // recommend to use string type to register component
//        builder.registerCell("testView", TestView.class);
//        builder.registerCell("singleImgView", SimpleImgView.class);
//        builder.registerCell("ratioTextView", RatioTextView.class);

        // register component with integer type was not recommend to use
        builder.registerCell(1, TestView.class);
        builder.registerCell(10, SimpleImgView.class);
        builder.registerCell(2, SimpleImgView.class);
        builder.registerCell(4, RatioTextView.class);
        builder.registerCell(110,
                TestViewHolderCell.class,
                new ViewHolderCreator<>(R.layout.ui_activity_tangram_item_holder, TestViewHolder.class, TextView.class));
        builder.registerCell(199, SingleImageView.class);
        builder.registerVirtualView("vvtest");

        //5.生成TangramEngine实例
        engine = builder.build();
        engine.setSupportRx(true);
        engine.setVirtualViewTemplate(VVTEST.BIN);
        engine.getService(VafContext.class).setImageLoaderAdapter(new ImageLoader.IImageLoaderAdapter() {
            @Override
            public void bindImage(String uri, ImageBase imageBase, int reqWidth, int reqHeight) {
                Logger.i("bindImage " + uri + " request width height " + reqHeight + " " + reqWidth);
                RequestCreator requestCreator = Picasso.get().load(uri);
                if (reqHeight > 0 && reqWidth > 0) {
                    requestCreator.resize(reqWidth, reqHeight);
                }
                ImageTarget imageTarget = new ImageTarget(imageBase);
                requestCreator.into(imageTarget);
            }

            @Override
            public void getBitmap(String uri, int reqWidth, int reqHeight, ImageLoader.Listener lis) {
                Logger.i("getBitmap " + uri + " request width height " + reqHeight + " " + reqWidth);
                RequestCreator requestCreator = Picasso.get().load(uri);
                if (reqHeight > 0 && reqWidth > 0) {
                    requestCreator.resize(reqWidth,reqHeight);
                }
                ImageTarget imageTarget = new ImageTarget(lis);
                requestCreator.into(imageTarget);
            }
        });

        CardLoadSupport cardLoadSupport = new CardLoadSupport();
        engine.addCardLoadSupport(cardLoadSupport);
        Observable<Card> loadCardObservable = cardLoadSupport.observeCardLoading();
        Disposable disposable = loadCardObservable.observeOn(Schedulers.io())
                .map(new Function<Card, LoadGroupOp>() {
                    @Override
                    public LoadGroupOp apply(Card card) throws Exception {

                        JSONArray cells = new JSONArray();
                        for (int i = 0; i < 10; i++) {
                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("type", 1);
                                obj.put("msg", "async loaded");
                                JSONObject style = new JSONObject();
                                style.put("bgColor", "#ff0000");
                                obj.put("style", style.toString());
                                cells.put(obj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        List<BaseCell> result = engine.parseComponent(cells);
                        LoadGroupOp loadGroupOp = new LoadGroupOp(card, result);
                        return loadGroupOp;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(cardLoadSupport.asDoLoadFinishConsumer());
        mCompositeDisposable.add(disposable);

        Observable<Card> loadMoreObservable = cardLoadSupport.observeCardLoadingMore();
        Disposable disposable1 = loadMoreObservable.observeOn(Schedulers.io())
                .map(new Function<Card, LoadMoreOp>() {
                    @Override
                    public LoadMoreOp apply(Card card) throws Exception {
                        JSONArray cells = new JSONArray();
                        for (int i = 0; i < 9; i++) {
                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("type", 1);
                                obj.put("msg", "async page loaded, params: " + card.getParams().toString());
                                cells.put(obj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        List<BaseCell> cs = engine.parseComponent(cells);
                        //mock loading 6 pages
                        LoadMoreOp loadMoreOp = new LoadMoreOp(card, cs, card.page != 6);
                        return loadMoreOp;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(cardLoadSupport.asDoLoadMoreFinishConsumer());
        mCompositeDisposable.add(disposable1);

        //6.绑定业务 support 类到 engine
        engine.addSimpleClickSupport(new SampleClickSupport());
        BannerSupport bannerSupport = new BannerSupport();
        engine.register(BannerSupport.class, bannerSupport);

        Disposable disposable2 = bannerSupport.observeSelected("banner1").subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("banner1 select:" + integer);
            }
        });
        mCompositeDisposable.add(disposable2);

        Disposable disposable3 = bannerSupport.observeScrollStateChanged("banner2").subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Logger.i("banner2 scroll state change:" + integer);
            }
        });
        mCompositeDisposable.add(disposable3);

        Disposable disposable4 = bannerSupport.observeScrolled("banner2").subscribe(new Consumer<RxBannerScrolledListener.ScrollEvent>() {
            @Override
            public void accept(RxBannerScrolledListener.ScrollEvent scrollEvent) throws Exception {
                Logger.i("banner2 scroll :" + scrollEvent.toString());
            }
        });
        mCompositeDisposable.add(disposable4);

        //enable auto load more if your page's data is lazy loaded
        engine.enableAutoLoadMore(true);

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
        engine.getLayoutManager().setFixOffset(0, 40, 0, 50);

        //10.设置卡片预加载的偏移量（可选）
        //在页面滚动过程中触发engine.onScrolled()方法，会去寻找屏幕外需要异步加载数据的卡片，默认往下寻找5个，让数据预加载出来，可以修改这个偏移量。
        engine.setPreLoadNumber(3);

        engine.register(SampleScrollSupport.class, new SampleScrollSupport(recyclerView));

        //11.加载数据并传递给 engine
        Disposable disposable5 = Observable.create(new ObservableOnSubscribe<JSONArray>() {
            @Override
            public void subscribe(ObservableEmitter<JSONArray> emitter) throws Exception {
                String json = new String(FireHelperUtils.readAssets2String(getApplicationContext(), "tangram_data.json"));
                JSONArray data = null;
                try {
                    data = new JSONArray(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                emitter.onNext(data);
                emitter.onComplete();
            }
        }).flatMap(new Function<JSONArray, ObservableSource<JSONObject>>() {
            @Override
            public ObservableSource<JSONObject> apply(JSONArray jsonArray) throws Exception {
                return JSONArrayObservable.fromJsonArray(jsonArray);
            }
        }).map(new Function<JSONObject, ParseSingleGroupOp>() {
            @Override
            public ParseSingleGroupOp apply(JSONObject jsonObject) throws Exception {
                return new ParseSingleGroupOp(jsonObject, engine);
            }
        }).compose(engine.getSingleGroupTransformer())
                .filter(new Predicate<Card>() {
                    @Override
                    public boolean test(Card card) throws Exception {
                        return card.isValid();
                    }
                }).map(new Function<Card, AppendGroupOp>() {
                    @Override
                    public AppendGroupOp apply(Card card) throws Exception {
                        return new AppendGroupOp(card);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(engine.asAppendGroupConsumer(), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        mCompositeDisposable.add(disposable5);

        mCompositeDisposable.add(ViewClickObservable.from(findViewById(R.id.ui_activity_tangram_tab_1)).map(new Function<Object, UpdateCellOp>() {
            @Override
            public UpdateCellOp apply(Object o) throws Exception {
                Logger.i("tab1 click; apply UpdateCellOp");
                BaseCell baseCell = (BaseCell) engine.getGroupBasicAdapter().getComponents().get(0);
                baseCell.extras.put("title", "Title1");
                return new UpdateCellOp(baseCell);
            }
        }).subscribe(engine.asUpdateCellConsumer()));

        mCompositeDisposable.add(ViewClickObservable.from(findViewById(R.id.ui_activity_tangram_tab_2)).map(new Function<Object, UpdateCellOp>() {
            @Override
            public UpdateCellOp apply(Object o) throws Exception {
                Logger.i("tab2 click; apply UpdateCellOp");
                BaseCell baseCell = (BaseCell) engine.getGroupBasicAdapter().getComponents().get(1);
                baseCell.extras.put("title", "Title2");
                return new UpdateCellOp(baseCell);
            }
        }).subscribe(engine.asUpdateCellConsumer()));
    }

    private class ImageTarget implements com.squareup.picasso.Target {

        ImageBase imageBase;
        ImageLoader.Listener listener;

        public ImageTarget(ImageBase imageBase) {
            this.imageBase = imageBase;
        }

        public ImageTarget(ImageLoader.Listener listener) {
            this.listener = listener;
        }


        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            imageBase.setBitmap(bitmap, true);
            if (listener != null) {
                listener.onImageLoadSuccess(bitmap);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            if (listener != null) {
                listener.onImageLoadFailed();
            }
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.destroy();
        }
    }

}
