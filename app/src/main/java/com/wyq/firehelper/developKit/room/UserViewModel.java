package com.wyq.firehelper.developKit.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wyq.firehelper.developKit.room.datasource.DataRepository;
import com.wyq.firehelper.developKit.room.entity.UserEntity;

public class UserViewModel extends ViewModel {

    private MediatorLiveData<UserEntity> mObservableUser = new MediatorLiveData<>();;

    public UserViewModel(DataRepository repository) {
        // set by default null, until we get data from the database.
        mObservableUser.setValue(null);
        LiveData<UserEntity> userLiveData = repository.getUser();

        // observe the changes of the user from the database and forward them
        mObservableUser.addSource(userLiveData, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity entity) {
                mObservableUser.setValue(entity);
            }
        });

    }

    /**
     * Expose the LiveData user query so the UI can observe it.
     */
    public LiveData<UserEntity> getObservableUser() {
        return mObservableUser;
    }

    public void setUser(UserEntity user) {
        mObservableUser.postValue(user);
    }

    /**
     * A creator is used to inject the IUser ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the user ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private DataRepository repository;

        public Factory(DataRepository repository) {
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserViewModel(repository);
        }
    }
}
