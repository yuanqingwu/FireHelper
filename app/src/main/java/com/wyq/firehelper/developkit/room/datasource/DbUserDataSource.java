package com.wyq.firehelper.developkit.room.datasource;

import android.arch.lifecycle.LiveData;

import com.wyq.firehelper.developkit.room.UserDao;
import com.wyq.firehelper.developkit.room.entity.UserEntity;

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
