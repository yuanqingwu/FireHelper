package com.wyq.firehelper.developKit.room.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.wyq.firehelper.developKit.room.AppDatabase;
import com.wyq.firehelper.developKit.room.entity.UserEntity;

public class DataRepository {

    private UserDataSource dataSource;
    private MediatorLiveData<UserEntity> mObservableUser;

    private static DataRepository sInstance;

    private DataRepository(UserDataSource dataSource) {
        this.dataSource = dataSource;
        mObservableUser = new MediatorLiveData<>();

        mObservableUser.addSource(dataSource.getFirstUser(), new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity entity) {
                if(entity != null){
                    mObservableUser.postValue(entity);
                }
            }
        });
    }

    public static DataRepository getInstance(AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(new DbUserDataSource(database.userDao()));
                }
            }
        }
        return sInstance;
    }

    public LiveData<UserEntity> getUser() {
        return mObservableUser;
    }

    public void insertOrUpdateUser(UserEntity user) {
       dataSource.insertOrUpdateUser(user);
    }

//    public LiveData<UserEntity> getFirstUser(){
////        return dataSource.getFirstUser();
////    }
}
