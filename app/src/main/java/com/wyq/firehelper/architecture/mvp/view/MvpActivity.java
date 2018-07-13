package com.wyq.firehelper.architecture.mvp.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.activity.BaseActivity;
import com.wyq.firehelper.architecture.mvp.presenter.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MvpActivity extends BaseActivity implements IBaseView {

    @BindView(R.id.architecture_activity_mvp_translate_tv)
    public TextView textView;

    @BindView(R.id.architecture_activity_mvp_translate_et)
    public EditText editText;

    @BindView(R.id.architecture_activity_mvp_translate_bt)
    public Button translateBt;

    private Presenter presenter;

    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.architecture_activity_mvp_layout);
        ButterKnife.bind(this);

        presenter = new Presenter(this);

    }

    @OnClick(R.id.architecture_activity_mvp_translate_bt)
    public void translate(){
        str = editText.getText().toString();
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
