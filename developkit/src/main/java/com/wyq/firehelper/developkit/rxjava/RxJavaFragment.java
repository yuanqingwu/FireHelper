package com.wyq.firehelper.developkit.rxjava;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.developkit.R;
import com.wyq.firehelper.developkit.R2;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 中，已经内置了很多线程选项供我们选择，例如有：
 * <p>
 * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作；
 * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作；
 * Schedulers.newThread() 代表一个常规的新线程；
 * AndroidSchedulers.mainThread() 代表Android的主线程
 * <p>
 * <p>
 * map 操作符可以将一个 Observable 对象通过某种关系转换为另一个Observable 对象。在 2.x 中和 1.x 中作用几乎一致，不同点在于：2.x 将 1.x 中的 Func1 和 Func2 改为了 Function 和 BiFunction。
 * 采用 map 操作符进行网络数据解析
 * <p>
 * <p>
 * concat 可以做到不交错的发射两个甚至多个 Observable 的发射事件，并且只有前一个 Observable 终止(onComplete) 后才会订阅下一个 Observable。
 * 如：采用 concat 操作符先读取缓存再通过网络请求获取数据
 * <p>
 * merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
 * <p>
 * flatMap 操作符可以将一个发射数据的 Observable 变换为多个 Observables ，然后将它们发射的数据合并后放到一个单独的 Observable
 * 如：flatMap 实现多个网络请求依次依赖
 * <p>
 * concatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序
 * <p>
 * <p>
 * zip 操作符可以将多个 Observable 的数据结合为一个数据源再发射出去
 * 如：zip 操作符，实现多个接口数据共同更新 UI
 * <p>
 * 采用 interval 操作符实现心跳间隔任务
 * <p>
 * distinct 去重
 * <p>
 * Filter 过滤器
 * <p>
 * buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个  Observable
 * 我们把 1, 2, 3, 4, 5 依次发射出来，经过 buffer 操作符，其中参数 skip 为 2， count 为 3，而我们的输出 依次是 123，345，5。显而易见，我们 buffer 的第一个参数是 count，代表最大取值，在事件足够的时候，一般都是取 count 个值，然后每次跳过 skip 个事件。
 * <p>
 * skip 作用就和字面意思一样，接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。
 * <p>
 * take 接受一个 long 型参数 count ，代表至多接收 count 个数据。
 * <p>
 * just 就是一个简单的发射器依次调用 onNext() 方法。
 * <p>
 * Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
 * <p>
 * debounce 去除发送频率过快的项
 * <p>
 * defer 简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable
 * <p>
 * last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项
 * <p>
 * reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值
 * <p>
 * scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 只输出结果，而 scan 会始终如一地把每一个步骤都输出。
 * <p>
 * window 按照实际划分窗口，将数据发送给不同的 Observable
 */
public class RxJavaFragment extends BaseCaseFragment {

    public String schedulers = "Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作；\n" +
            " \n" +
            " Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作；\n" +
            " \n" +
            " Schedulers.newThread() 代表一个常规的新线程；\n" +
            " \n" +
            " AndroidSchedulers.mainThread() 代表Android的主线程";

    public String operator = " map 操作符可以将一个 Observable 对象通过某种关系转换为另一个Observable 对象。在 2.x 中和 1.x 中作用几乎一致，不同点在于：2.x 将 1.x 中的 Func1 和 Func2 改为了 Function 和 BiFunction。\n" +
            "  采用 map 操作符进行网络数据解析\n" +
            " \n" +
            "  concat 可以做到不交错的发射两个甚至多个 Observable 的发射事件，并且只有前一个 Observable 终止(onComplete) 后才会订阅下一个 Observable。\n" +
            "  如：采用 concat 操作符先读取缓存再通过网络请求获取数据\n" +
            "  <p>\n" +
            "  merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。\n" +
            "  <p>\n" +
            "  flatMap 操作符可以将一个发射数据的 Observable 变换为多个 Observables ，然后将它们发射的数据合并后放到一个单独的 Observable\n" +
            "  如：flatMap 实现多个网络请求依次依赖\n" +
            "  <p>\n" +
            "  concatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序\n" +
            "  <p>\n" +
            "  switchMap:与flatMap操作符不同的是，switchMap操作符会保存最新的Observable产生的\n" +
            "                 结果而舍弃旧的结果" +
            "  <p>\n" +
            "  zip 操作符可以将多个 Observable 的数据结合为一个数据源再发射出去\n" +
            "  如：zip 操作符，实现多个接口数据共同更新 UI\n" +
            "  <p>\n" +
            "  采用 interval 操作符实现心跳间隔任务\n" +
            "  <p>\n" +
            "  distinct 去重\n" +
            "  <p>\n" +
            "  Filter 过滤器\n" +
            "  <p>\n" +
            "  buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个  Observable\n" +
            "  我们把 1, 2, 3, 4, 5 依次发射出来，经过 buffer 操作符，其中参数 skip 为 2， count 为 3，而我们的输出 依次是 123，345，5。显而易见，我们 buffer 的第一个参数是 count，代表最大取值，在事件足够的时候，一般都是取 count 个值，然后每次跳过 skip 个事件。\n" +
            "  <p>\n" +
            "  skip 作用就和字面意思一样，接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。\n" +
            "  <p>\n" +
            "  take 接受一个 long 型参数 count ，代表至多接收 count 个数据。\n" +
            "  <p>\n" +
            "  just 就是一个简单的发射器依次调用 onNext() 方法。\n" +
            "  <p>\n" +
            "  Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。\n" +
            "  <p>\n" +
            "  debounce 去除发送频率过快的项\n" +
            "  <p>\n" +
            "  defer 简单地说就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable\n" +
            "  <p>\n" +
            "  last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项\n" +
            "  <p>\n" +
            "  reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值\n" +
            "  <p>\n" +
            "  scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 只输出结果，而 scan 会始终如一地把每一个步骤都输出。\n" +
            "  <p>\n" +
            "  window 按照实际划分窗口，将数据发送给不同的 Observable";

    public String observableType = "Observable<T> \t能够发射0或n个数据，并以成功或错误事件终止。\n" +
            "Flowable<T> \t能够发射0或n个数据，并以成功或错误事件终止。 支持Backpressure，可以控制数据源发射的速度。\n" +
            "Single<T> \t只发射单个数据或错误事件。\n" +
            "Completable \t它从来不发射数据，只处理 onComplete 和 onError 事件。可以看成是Rx的Runnable。\n" +
            "Maybe<T> \t能够发射0或者1个数据，要么成功，要么失败。有点类似于Optional\n";

    @BindView(R2.id.activity_developkit_rxjava_res_tv)
    public TextView resTv;

    @BindView(R2.id.activity_developkit_rxjava_tv_1)
    public TextView textView1;

    @BindView(R2.id.activity_developkit_rxjava_tv_2)
    public TextView textView2;

    @BindView(R2.id.activity_developkit_rxjava_tv_3)
    public TextView textView3;
    @Override
    public int attachLayoutRes() {
        return R.layout.developkit_activity_rxjava_layout;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView(View view) {
        textView1.setText(observableType);
        textView2.setText(schedulers);
        Spanned spanned = Html.fromHtml(operator);
        textView3.setText(spanned);

        observable();
        flowable();
        completable();
        single();
        maybe();
    }

    @Override
    public String[] getArticleFilters() {
        return new String[]{"RxJava"};
    }

    @Override
    public String getToolBarTitle() {
        return "RxJava";
    }

    public void observable() {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
                emitter.onNext("4");

            }
        }).subscribe(new Observer<String>() {

            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                if (s.equals("2")) {
                    disposable.dispose();
                }
                resTv.append(s);
            }

            @Override
            public void onError(Throwable e) {
                resTv.append("\n" + e.toString());
            }

            @Override
            public void onComplete() {
                resTv.append("\nonComplete");
            }
        });

        consumer();
    }

    public void consumer() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Consumer");
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                resTv.append("\n" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }

    public void flowable() {
        Flowable.just("Flowable1", "Flowable2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                resTv.append("\n\n" + s);
                            }
                        }
                );
    }

    public void completable() {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                resTv.append("\n\n" + "completable");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        completableWithNext();
    }

    public void completableWithNext() {
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                TimeUnit.SECONDS.sleep(1);
                emitter.onComplete();
            }
        }).andThen(Observable.range(1, 3))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        resTv.append("\n completable" + integer);
                    }
                });
    }

    public void single() {
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                emitter.onSuccess("single");
            }
        }).subscribe(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) throws Exception {
                resTv.append("\n\n" + s);
            }
        });
    }

    public void maybe() {
        Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {
                emitter.onSuccess("Maybe1");
                emitter.onComplete();
                emitter.onSuccess("Maybe2");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                resTv.setText("\n" + s);
            }
        });

    }
}
