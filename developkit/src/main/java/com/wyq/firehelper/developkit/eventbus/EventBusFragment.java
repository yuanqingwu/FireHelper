package com.wyq.firehelper.developkit.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.developkit.R;
import com.wyq.firehelper.developkit.R2;
import com.wyq.firehelper.developkit.eventbus.livedata.LiveDataBus;
import com.wyq.firehelper.developkit.eventbus.rx.RxBus;
import com.wyq.firehelper.developkit.eventbus.rx.RxBusSimple;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.lifecycle.Observer;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class EventBusFragment extends BaseCaseFragment {

    @BindView(R2.id.activity_developkit_eventbus_tv_1)
    public TextView textView1;
    @BindView(R2.id.activity_developkit_rxbus_bt)
    public Button rxBusBt;

    private final String KEY_EVENT_BUS = "KEY_EVENT_BUS";
    private final String KEY_RX_BUS = "KEY_RX_BUS";
    private final String KEY_LIVE_DATA_BUS = "KEY_LIVE_DATA_BUS";

    @Override
    public int attachLayoutRes() {
        return R.layout.developkit_activity_eventbus_layout;
    }

    private String defectStr = "由于是Event,在发布Event的时候就要做好准备可能并没有人接受这个Event, Subscribe的时候也要做好准备可能永远不会收到Event。Event无论顺序还是时间上都某种程度上不太可控。如果你将数据寄托在Event上然后就直接在Android其他生命周期方法中直接使用这个数据或成员变量。那么很有可能你会得到NPE。\n" +
            "EventBus看似将你的程序解耦，但是又有些过了。我们常常使用EventBus传数据，这已经是Dependency级别的数据而不是一个可以被解耦出来的模块。这样就造成了过多EventBus的代码会造成代码结构混乱，难以测试和追踪，违背了解耦的初衷。这时如果有意或无意的造成了Nested Event。那情况会更糟。\n";

    @Override
    public String[] getArticleFilters() {
        return new String[]{"EventBus"};
    }

    @Override
    public String getToolBarTitle() {
        return "EventBus";
    }


    @Override
    public void initData() {
        EventBus.getDefault().register(this);

        registerRxBus();

//        testLiveDataBus();//测试先发事件再监听的情况

        //注册监听者
        registerLiveDataObserver();
    }

    @Override
    public void initView(View view) {
        textView1.setText(defectStr);

        rxBusBt.setText("RxBus");
        rxBusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testEventBus();

                testRxBus();

                testLiveDataBus();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(priority = 1, sticky = true, threadMode = ThreadMode.MAIN_ORDERED)
    public void testEventBus(EventBusMessage message) {
        Logger.i("message:" + message.getMessage());
    }

    private void testEventBus(){
        EventBus.getDefault().post(new EventBusMessage(1));
    }

    private void registerRxBus(){
//        RxBus.get().subscribe(this, KEY_RX_BUS, new RxBus.OnEventListener<String>() {
//            @Override
//            public void onEvent(String s) {
//                Logger.i(s);
//            }
//        });
    }

    private void testRxBus() {
//        RxBusSimple.get().toObservable().subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                Logger.i(o.toString());
//            }
//        });
//
//        RxBusSimple.get().toObservable().subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                Logger.i(o.toString());
//            }
//        });
//
//        RxBusSimple.get().post("RxBusSimple action 1");
//        RxBusSimple.get().post("RxBusSimple action 2");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                RxBusSimple.get().post("RxBusSimple action 3");
//            }
//        }).start();

        RxBus.get().post(KEY_RX_BUS, "RxBus action");

    }

    private void registerLiveDataObserver(){
        LiveDataBus.get().with(KEY_LIVE_DATA_BUS).observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Logger.i("observe:" + o.toString());
            }
        });

        LiveDataBus.get().with(KEY_LIVE_DATA_BUS).observeSticky(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Logger.i("observeSticky:" + o.toString());
            }
        });

    }

    private void testLiveDataBus() {
        LiveDataBus.get().with(KEY_LIVE_DATA_BUS).post("LiveDataBus action 1");
        LiveDataBus.get().with("key2").post("LiveDataBus action 2");

        LiveDataBus.get().with(KEY_LIVE_DATA_BUS).postDelay("LiveDataBus action 3 delay", 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LiveDataBus.get().with(KEY_LIVE_DATA_BUS).post("LiveDataBus action 4");
            }
        }).start();

    }

}
