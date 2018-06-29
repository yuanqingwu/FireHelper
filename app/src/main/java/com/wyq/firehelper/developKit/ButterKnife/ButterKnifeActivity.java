package com.wyq.firehelper.developKit.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButterKnifeActivity extends AppCompatActivity {

    @BindView(R.id.activity_developkit_butterknife_tv)
    public TextView articleTv;//官网

    @BindView(R.id.activity_developkit_butterknife_tv_1)
    public TextView articleTv1;//总结文章

    @BindView(R.id.activity_developkit_butterknife_tv_2)
    public TextView textView2;

    @BindView(R.id.activity_developkit_butterknife_tv_3)
    public TextView textView3;

    @BindView(R.id.activity_developkit_butterknife_tv_4)
    public TextView textView4;

    @BindView(R.id.activity_developkit_butterknife_tv_5)
    public TextView textView5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_developkit_butterknife_layout);
        ButterKnife.bind(this);

        initView();
    }


    @OnClick(R.id.activity_developkit_butterknife_tv)
    public void readArticle() {
        Intent intent = new Intent();
        intent.putExtra("url", ArticleConstants._5_0_0.getUrl());
        intent.setClass(ButterKnifeActivity.this, WebViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.activity_developkit_butterknife_tv_1)
    public void readArticle1() {
        Intent intent = new Intent();
        intent.putExtra("url", ArticleConstants._5_0_1.getUrl());
        intent.setClass(ButterKnifeActivity.this, WebViewActivity.class);
        startActivity(intent);
    }

    private void initView() {
        articleTv.setText(ArticleConstants._5_0_0.getTitle());
        articleTv1.setText(ArticleConstants._5_0_1.getTitle());

        textView2.setText("1、在Activity 类中绑定 ：ButterKnife.bind(this);必须在setContentView();之后绑定；且父类bind绑定后，子类不需要再bind。\n" +
                "\n" +
                "2、在非Activity 类（eg：Fragment、ViewHold）中绑定： ButterKnife.bind(this，view);这里的this不能替换成getActivity（）。\n" +
                "\n" +
                "3、在Activity中不需要做解绑操作，在Fragment 中必须在onDestroyView()中做解绑操作。\n" +
                "\n" +
                "4、使用ButterKnife修饰的方法和控件，不能用private or static 修饰，否则会报错。错误: @BindView fields must not be private or static. \n" +
                "\n" +
                "5、setContentView()不能通过注解实现。（其他的有些注解框架可以）\n" +
                "\n" +
                "6、使用Activity为根视图绑定任意对象时，如果你使用类似MVC的设计模式你可以在Activity 调用ButterKnife.bind(this, activity)，来绑定Controller。\n" +
                "\n" +
                "7、使用ButterKnife.bind(this，view)绑定一个view的子节点字段。如果你在子View的布局里或者自定义view的构造方法里 使用了inflate,你可以立刻调用此方法。或者，从XML inflate来的自定义view类型可以在onFinishInflate回调方法中使用它。\n"
        );

        textView3.setText("\n" +
                "\n" +
                "    @BindView—->绑定一个view；id为一个view 变量\n" +
                "\n" +
                "    @BindViews —-> 绑定多个view；id为一个view的list变量\n" +
                "\n" +
                "    @BindArray—-> 绑定string里面array数组；@BindArray(R.array.city ) String[] citys ;\n" +
                "\n" +
                "    @BindBitmap—->绑定图片资源为Bitmap；@BindBitmap( R.mipmap.wifi ) Bitmap bitmap;\n" +
                "\n" +
                "    @BindBool —->绑定boolean值\n" +
                "\n" +
                "    @BindColor —->绑定color；@BindColor(R.color.colorAccent) int black;\n" +
                "\n" +
                "    @BindDimen —->绑定Dimen；@BindDimen(R.dimen.borth_width) int mBorderWidth;\n" +
                "\n" +
                "    @BindDrawable —-> 绑定Drawable；@BindDrawable(R.drawable.test_pic) Drawable mTestPic;\n" +
                "\n" +
                "    @BindFloat —->绑定float\n" +
                "\n" +
                "    @BindInt —->绑定int\n" +
                "\n" +
                "    @BindString —->绑定一个String id为一个String变量；@BindString( R.string.app_name ) String meg;\n");

        textView4.setText("\n" +
                "\n" +
                "    @OnClick—->点击事件\n" +
                "\n" +
                "    @OnCheckedChanged —->选中，取消选中\n" +
                "\n" +
                "    @OnEditorAction —->软键盘的功能键\n" +
                "\n" +
                "    @OnFocusChange —->焦点改变\n" +
                "\n" +
                "    @OnItemClick item—->被点击(注意这里有坑，如果item里面有Button等这些有点击的控件事件的，需要设置这些控件属性focusable为false)\n" +
                "\n" +
                "    @OnItemLongClick item—->长按(返回真可以拦截onItemClick)\n" +
                "\n" +
                "    @OnItemSelected —->item被选择事件\n" +
                "\n" +
                "    @OnLongClick —->长按事件\n" +
                "\n" +
                "    @OnPageChange —->页面改变事件\n" +
                "\n" +
                "    @OnTextChanged —->EditText里面的文本变化事件\n" +
                "\n" +
                "    @OnTouch —->触摸事件\n" +
                "\n" +
                "    @Optional —->选择性注入，如果当前对象不存在，就会抛出一个异常，为了压制这个异常，可以在变量或者方法上加入一下注解,让注入变成选择性的,如果目标View存在,则注入, 不存在,则什么事情都不做\n");

        textView5.setText("ButterKnife的代码混淆\n"+"-keep class butterknife.** { *; }  \n" +
                "-dontwarn butterknife.internal.**  \n" +
                "-keep class **$$ViewBinder { *; }  \n" +
                "\n" +
                "-keepclasseswithmembernames class * {  \n" +
                "    @butterknife.* <fields>;  \n" +
                "}  \n" +
                "\n" +
                "-keepclasseswithmembernames class * {  \n" +
                "    @butterknife.* <methods>;  \n" +
                "}  ");
    }

}
