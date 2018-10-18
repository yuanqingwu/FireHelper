package com.wyq.firehelper.developkit.room.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.developkit.room.AppDatabase;
import com.wyq.firehelper.developkit.room.entity.UserEntity;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

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
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Logger.i("DataRepository insertOrUpdateUser");
                dataSource.insertOrUpdateUser(user);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();

    }
}
