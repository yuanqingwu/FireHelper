package com.wyq.firehelper.developkit.retrofit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import butterknife.BindView;

public class RetrofitFragment extends BaseCaseFragment {

    @BindView(R.id.activity_developkit_retrofit_res_tv)
    public TextView resTv;

    @BindView(R.id.activity_developkit_retrofit_iv)
    public ImageView imageView;

    @Override
    public int attachLayoutRes() {
        return R.layout.developkit_activity_retrofit_layout;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView(View view) {

        IName name = proxy(IName.class);
        String res = "proxy class name:"+name.getClass().getName()+"\n"+name.getName();

//        IAge age = proxy(IAge.class);
//        res = age.getClass().getName()+"\n"+age.getAge();
        resTv.setText(res);
//        Logger.i(res);
    }


    /**
     * 动态代理
     *
     * 通过 Proxy.newProxyInstance 创建的代理对象是在jvm运行时动态生成的一个对象，它并不是我们的InvocationHandler类型，也不是我们定义的那组接口的类型，而是在运行是动态生成的一个对象，并且命名方式都是这样的形式，以$开头，proxy为中，最后一个数字表示对象的标号。
     * @param inf
     * @param <T>
     * @return
     */
    public <T> T proxy(Class<T> inf){
        if(!inf.isInterface()){
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }

        return (T)Proxy.newProxyInstance(inf.getClassLoader(), new Class[]{inf}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //If the method is a method from Object then defer to normal invocation
                if(method.getDeclaringClass() == Object.class){
                    method.invoke(this,args);
                }

                if(method.getDeclaringClass() == IName.class){
                    return "wyq";
                }
                return method.invoke(this,args);
            }
        });
    }

    @Override
    public String[] getArticleFilters() {
        return new String[]{"Retrofit"};
    }

    @Override
    public String getToolBarTitle() {
        return "Retrofit";
    }

    public interface IName{
        String getName();
    }

    public interface IAge{
        String getAge();
    }

}
