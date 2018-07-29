package com.wyq.firehelper.architecture.mvp.translation.presenter;

import com.wyq.firehelper.architecture.mvp.translation.model.Model;
import com.wyq.firehelper.architecture.mvp.translation.model.Translation;
import com.wyq.firehelper.architecture.mvp.translation.view.IBaseView;
import com.wyq.firehelper.architecture.mvp.translation.view.MvpActivity;

import io.reactivex.functions.Consumer;

public class PresenterImpl implements IPresenter {

    private Model model;
    private IBaseView baseView;

    public PresenterImpl(MvpActivity activity) {
        baseView = activity;
        model = new Model();
    }

    @Override
    public void translate(String ip) {

        Consumer<Translation> consumer = new Consumer<Translation>() {
            @Override
            public void accept(Translation translation) throws Exception {
                baseView.showInfo(translation.getTranslateResult().get(0).get(0).getTgt());
            }
        };
        model.queryInfo(ip, consumer);
    }
}
