package com.wyq.firehelper.developKit.Room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.wyq.firehelper.base.FireHelpApplication;

import io.reactivex.Completable;

public class UserViewModel extends AndroidViewModel {

    private MediatorLiveData<User> mObservableUser;

    //data binding
    public ObservableField<User> user = new ObservableField<>();

    private DataRepository repository;

    public UserViewModel(@NonNull Application application, DataRepository repository, int userId) {
        super(application);
        mObservableUser = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableUser.setValue(null);
        LiveData<User> userLiveData = ((FireHelpApplication)application).getRepository().getUserByUid(userId);

        // observe the changes of the user from the database and forward them
        mObservableUser.addSource(userLiveData,mObservableUser::setValue);

        this.repository = repository;
    }

    /**
     * Expose the LiveData user query so the UI can observe it.
     */
    public LiveData<User> getObservableUser() {
        return mObservableUser;
    }


    public Completable setUser(User user) {
        this.user.set(user);

        return Completable.fromAction(() -> {
            repository.updateUser(user);
        });
    }

    public LiveData<User> getUser() {
        return repository.getFirstUser();
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
