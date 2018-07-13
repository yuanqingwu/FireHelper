package com.wyq.firehelper.architecture.mvp.presenter;

import com.wyq.firehelper.architecture.mvp.model.Model;
import com.wyq.firehelper.architecture.mvp.model.Translation;
import com.wyq.firehelper.architecture.mvp.view.IBaseView;
import com.wyq.firehelper.architecture.mvp.view.MvpActivity;

import io.reactivex.functions.Consumer;

public class Presenter implements Ipresenter {

    private Model model;
    private IBaseView baseView;

    public Presenter(MvpActivity activity) {
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
