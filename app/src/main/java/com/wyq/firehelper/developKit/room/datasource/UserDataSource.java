package com.wyq.firehelper.developKit.room.datasource;

import android.arch.lifecycle.LiveData;

import com.wyq.firehelper.developKit.room.entity.UserEntity;

public interface UserDataSource {

    LiveData<UserEntity> getFirstUser();

    void insertOrUpdateUser(UserEntity entity);

    void deleteAllUser();
}
