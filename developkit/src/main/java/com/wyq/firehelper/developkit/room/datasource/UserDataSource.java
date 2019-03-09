package com.wyq.firehelper.developkit.room.datasource;

import com.wyq.firehelper.developkit.room.entity.UserEntity;

import androidx.lifecycle.LiveData;

public interface UserDataSource {

    LiveData<UserEntity> getFirstUser();

    void insertOrUpdateUser(UserEntity entity);

    void deleteAllUser();
}
