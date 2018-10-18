package com.wyq.firehelper.architecture.archcomponents;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wyq.firehelper.developkit.room.UserDao;
import com.wyq.firehelper.developkit.room.entity.UserEntity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class UserRepository {

    private Webservice webservice;

    private ConcurrentHashMap<String, LiveData<UserEntity>> userCache;

    private UserDao userDao;
    private Executor executor;

    public static final int FRESH_TIMEOUT = 24 * 60 * 60 * 1000;

    public UserRepository(Webservice webservice, UserDao userDao, Executor executor) {
        userCache = new ConcurrentHashMap<>();
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }


    public LiveData<Resource<UserEntity>> loadUser(String userId) {
        return new NetworkBoundResource<UserEntity, UserEntity>() {

            @Override
            protected void saveCallResult(@NonNull UserEntity item) {
                userDao.insertUser(item);
            }

            @Override
            protected boolean shouldFetch(@NonNull UserEntity data) {

                return false;
            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return userDao.getUserById(Integer.parseInt(userId));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserEntity>> createCall() {
                return webservice.getUser(userId);
            }
        }.getAsLiveData();
    }

    public LiveData<UserEntity> getUser(String userId) {
        refreshUser(userId);
        return userDao.getUserById(Integer.parseInt(userId));
    }


    private void refreshUser(String userId) {
        executor.execute(() -> {

            //TODO
//            boolean isUserExists = userDao.hasUser(FRESH_TIMEOUT);
            boolean isUserExists = false;
            if (!isUserExists) {
                getUserRemote(userId);
            }
        });
    }

    public LiveData<UserEntity> getUserRemote(String userId) {
        LiveData<UserEntity> userLiveData = userCache.get(userId);
        if (userLiveData != null) {
            return userLiveData;
        }

        MutableLiveData<UserEntity> userMutableLiveData = new MutableLiveData<>();

        userCache.put(userId, userMutableLiveData);

        webservice.getUser(userId).getValue().enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                userMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {

            }
        });

        return userMutableLiveData;
    }

}
