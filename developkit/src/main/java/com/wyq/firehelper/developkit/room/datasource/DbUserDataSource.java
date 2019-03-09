package com.wyq.firehelper.developkit.room.datasource;

import com.wyq.firehelper.developkit.room.UserDao;
import com.wyq.firehelper.developkit.room.entity.UserEntity;

import androidx.lifecycle.LiveData;

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
