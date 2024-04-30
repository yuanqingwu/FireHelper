package com.wyq.firehelper.developkit.retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.developkit.databinding.DevelopkitActivityRetrofitLayoutBinding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RetrofitFragment extends BaseCaseFragment {

    public TextView resTv;

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return DevelopkitActivityRetrofitLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView(View view) {

        resTv = ((DevelopkitActivityRetrofitLayoutBinding)binding).activityDevelopkitRetrofitResTv;
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
