package com.wyq.firehelper.architecture.mvp.model;

import io.reactivex.functions.Consumer;

public interface IModel {
    void queryInfo(String word, Consumer<Translation> consumer);
}
