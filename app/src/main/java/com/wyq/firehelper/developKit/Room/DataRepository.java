package com.wyq.firehelper.developKit.Room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

public class DataRepository {

    private AppDatabase appDatabase;
    private MediatorLiveData<List<User>> mObservableUser;

    private static DataRepository sInstance;

    private DataRepository(AppDatabase database) {
        this.appDatabase = database;
        mObservableUser = new MediatorLiveData<>();

        mObservableUser.addSource(appDatabase.userDao().getAll(), users -> {
            if (appDatabase.getDatabaseCreated().getValue() != null) {
                mObservableUser.postValue(users);
            }
        });
    }

    public static DataRepository getInstance(AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<User>> getUsers() {
        return mObservableUser;
    }

    public LiveData<User> getUserByUid(int uid) {
        return appDatabase.userDao().getUserById(uid);
    }

    public void updateUser(User user) {
        appDatabase.userDao().insertUser(user);
    }

    public LiveData<User> getFirstUser(){
        return appDatabase.userDao().getFirstUser();
    }
}
