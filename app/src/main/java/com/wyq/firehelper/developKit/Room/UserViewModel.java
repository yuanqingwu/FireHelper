package com.wyq.firehelper.developKit.Room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.wyq.firehelper.base.FireHelpApplication;

public class UserViewModel extends AndroidViewModel {

    private LiveData<User> mObservableUser;

    private int uid;

    //data binding
    private ObservableField<User> user = new ObservableField<>();

    public UserViewModel(@NonNull Application application, DataRepository repository, int userId) {
        super(application);
        this.uid = userId;
        mObservableUser = repository.getUserByUid(uid);
    }

    public LiveData<User> getObservableUser() {
        return mObservableUser;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public ObservableField<User> getUser() {
        return user;
    }
    /**
     * A creator is used to inject the User ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the user ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private Application application;

        private DataRepository repository;

        private int uid;

        public Factory(Application application, int uid) {
            this.application = application;
            this.repository = ((FireHelpApplication) application).getRepository();
            this.uid = uid;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserViewModel(application, repository, uid);
        }
    }
}
