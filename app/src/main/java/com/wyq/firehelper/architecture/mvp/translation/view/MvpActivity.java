package com.wyq.firehelper.architecture.mvp.translation.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.architecture.mvp.translation.presenter.PresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MVP(Model-View-Presenter)模式:

 Model: 数据层. 负责与网络层和数据库层的逻辑交互.
 View: UI层. 显示数据, 并向Presenter报告用户行为.
 Presenter: 从Model拿数据, 应用到UI层, 管理UI的状态, 决定要显示什么, 响应用户的行为.
 MVP模式的最主要优势就是耦合降低, Presenter变为纯Java的代码逻辑, 不再与Android Framework中的类如Activity, Fragment等关联, 便于写单元测试.
 */
public class MvpActivity extends BaseActivity implements IBaseView {

    @BindView(R.id.architecture_activity_mvp_translate_tv)
    public TextView textView;

    @BindView(R.id.architecture_activity_mvp_translate_et)
    public EditText editText;

    @BindView(R.id.architecture_activity_mvp_translate_bt)
    public Button translateBt;

    private PresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.architecture_activity_mvp_layout);
        ButterKnife.bind(this);

        presenter = new PresenterImpl(this);

    }

    @OnClick(R.id.architecture_activity_mvp_translate_bt)
    public void translate(){
        String str = editText.getText().toString();
        if (str != null && str.length() > 0) {
            presenter.translate(str);
        }
    }

    @Override
    public void showInfo(String bean) {
        textView.setText(bean);
    }

    @Override
    public void showError() {

    }
}
