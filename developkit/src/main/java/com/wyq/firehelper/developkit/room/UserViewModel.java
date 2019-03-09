package com.wyq.firehelper.developkit.room;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.developkit.room.datasource.DataRepository;
import com.wyq.firehelper.developkit.room.entity.UserEntity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserViewModel extends ViewModel {

    private MediatorLiveData<UserEntity> mObservableUser = new MediatorLiveData<>();

    private DataRepository repository;

    public UserViewModel(DataRepository repository) {
        this.repository = repository;

        // set by default null, until we get data from the database.
        mObservableUser.setValue(null);
        LiveData<UserEntity> userLiveData = repository.getUser();

        // observe the changes of the user from the database and forward them
        mObservableUser.addSource(userLiveData, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity entity) {
                mObservableUser.setValue(entity);
                Logger.i("repository userLiveData onChanged");
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
        repository.insertOrUpdateUser(user);
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
