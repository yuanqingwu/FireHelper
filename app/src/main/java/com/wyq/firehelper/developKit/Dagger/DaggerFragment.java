package com.wyq.firehelper.developKit.Dagger;

import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.developKit.DevelopKitBaseFragment;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import dagger.Lazy;

public class DaggerFragment extends DevelopKitBaseFragment {

    @BindView(R.id.activity_developkit_dagger_tv)
    public TextView textView;
    @BindView(R.id.activity_developkit_dagger_tv_1)
    public TextView textView1;
    @BindView(R.id.activity_developkit_dagger_res_tv)
    public TextView resTv;

    //@Named("context")
    @PersonWithContext
    @Inject
    public Person person1;

    //@Named("string")
    @PersonWithName
    @Inject
    public Person person2;

    /**
     * 懒加载Lazy和强制重新加载Provider
     *  lazyPerson 多次get 的是同一个对象，providerPerson多次get，每次get都会尝试创建新的对象。
     */
    @PersonWithName
    @Inject
    public Lazy<Person> personLazy;

    @PersonWithName
    @Inject
    public Provider<Person> personProvider;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.developkit_activity_dagger_layout,container,false);
//        initData();
//        initView();
//
//        return view;
//    }

    @Override
    public int attachLayoutRes() {
        return R.layout.developkit_activity_dagger_layout;
    }

    @Override
    public void initData() {
        resourceList.put(textView, ArticleConstants.DEVKIT_INJECT_DAGGER_0);
        resourceList.put(textView1, ArticleConstants.DEVKIT_INJECT_DAGGER_1);
    }

    @Override
    public void initView() {
        browserArticle(getActivity());

        //        PersonComponent component = DaggerPersonComponent.builder().personModule(new PersonModule(this)).build();
        PersonComponent component = DaggerPersonComponent.builder().personModule(new PersonModule(getActivity(),"wyq")).build();
        component.inject(this);
        StringBuilder builder = new StringBuilder();
        builder.append(person1.logPerson()+"\n");
        builder.append(person2.logPerson()+"\n");
        builder.append(personLazy.get().logPerson()+"\n");
        builder.append(personProvider.get().logPerson()+"\n");
        resTv.setText(builder);

        //1. ActivityModule 也需要创建Person时的Context对象，但是本类中却没有 providesContext() 的方法，因为它通过 ActivityComponent依赖于 AppComponent，所以可以通过 AppComponent中的 providesContext() 方法获取到Context对象。
        //2. AppComponent中必须提供 Context getContext(); 这样返回值是 Context 对象的方法接口，否则ActivityModule中无法获取。
//        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(getApplicationContext())).build();
//        ActivityComponent activityComponent = DaggerActivityComponent.builder().appComponent(appComponent).activityModule(new ActivityModule()).build();
//        activityComponent.inject(this);
    }
}
