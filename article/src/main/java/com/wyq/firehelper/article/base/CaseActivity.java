package com.wyq.firehelper.article.base;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.article.ArticleRepository;
import com.wyq.firehelper.article.R;
import com.wyq.firehelper.article.databinding.BaseActivityCaseBinding;
import com.wyq.firehelper.base.BaseCaseFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

public class CaseActivity extends BaseCaseActivity {

    private static final String PARAM_NAME = "FRAGMENT_PATH";
    private String[] filters = null;
    private String toolBarTitle = "";

    @Override
    public String getToolBarTitle() {
        return toolBarTitle;
    }

    @Override
    public List getArticleList() {
        return ArticleRepository.getInstance().getListByFilter(filters);
    }

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return BaseActivityCaseBinding.inflate(layoutInflater);
    }

    @Override
    public void initView() {
        String fragmentPath = getIntent().getStringExtra(PARAM_NAME);
        try {
            Object clazz = Class.forName(fragmentPath).newInstance();
            if(clazz instanceof BaseCaseFragment) {
                 filters = ((BaseCaseFragment) clazz).getArticleFilters();
                 toolBarTitle = ((BaseCaseFragment)clazz).getToolBarTitle();
                 refreshToolBarTitle();//因为fragment后加载，所以刷新标题
                refreshArticleList();
                replaceFragment((BaseCaseFragment) clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"找不到相关界面，先浏览其他的吧~",Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction  = manager.beginTransaction();
        transaction.replace(R.id.base_activity_case_stub,fragment);
        transaction.commit();
    }

    public static void instance(Context context,String fragmentPath){
        Logger.i("fragmentPath:"+fragmentPath);
        Intent intent = new Intent();
        intent.putExtra(PARAM_NAME,fragmentPath);
        intent.setClass(context, CaseActivity.class);
        context.startActivity(intent);
    }
}
