package com.wyq.firehelper.developkit.room.datasource;

import androidx.lifecycle.LiveData;

import com.wyq.firehelper.developkit.room.entity.UserEntity;

public interface UserDataSource {

    LiveData<UserEntity> getFirstUser();

    void insertOrUpdateUser(UserEntity entity);

    void deleteAllUser();
}
