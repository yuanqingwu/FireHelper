package com.wyq.firehelper.architecture.archcomponents;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserProfileViewModel extends ViewModel {

    private LiveData<UserEntity> user;
    private final UserRepository userRepository;

    @Inject
    public UserProfileViewModel(UserRepository repository) {
        this.userRepository = repository;
    }

    public void init(String userId) {
        if (user != null) {
            return;
        }
        user = userRepository.getUser(userId);
    }

    public LiveData<UserEntity> getUser() {
        return user;
    }
}
