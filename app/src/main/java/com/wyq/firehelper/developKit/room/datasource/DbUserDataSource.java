package com.wyq.firehelper.developKit.room.datasource;

import android.arch.lifecycle.LiveData;

import com.wyq.firehelper.developKit.room.UserDao;
import com.wyq.firehelper.developKit.room.entity.UserEntity;

public class DbUserDataSource implements UserDataSource {

    private UserDao userDao;

    public DbUserDataSource(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public LiveData<UserEntity> getFirstUser() {
        return userDao.getFirstUser();
    }

    @Override
    public void insertOrUpdateUser(UserEntity entity) {
        userDao.insertUser(entity);
    }

    @Override
    public void deleteAllUser() {
        userDao.deleteAllUser();
    }
}
