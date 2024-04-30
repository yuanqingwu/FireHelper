package com.wyq.firehelper.developkit.room;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.developkit.R;
import com.wyq.firehelper.developkit.databinding.DevelopkitActivityRoomBinding;
import com.wyq.firehelper.developkit.room.datasource.DataRepository;
import com.wyq.firehelper.developkit.room.entity.UserEntity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    public DevelopkitActivityRoomBinding roomBinding;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomBinding = DataBindingUtil.setContentView(this, R.layout.developkit_activity_room);

        roomBinding.developkitActivityRoomCommitBt.setOnClickListener(this);

        UserViewModel.Factory factory = new UserViewModel.Factory(DataRepository.getInstance(AppDatabase.getInstance(this, new AppExecutors())));

        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);

        subscribeUi(userViewModel);

        roomBinding.setLifecycleOwner(this);
    }

    private void subscribeUi(UserViewModel userViewModel) {
        //observe user
        userViewModel.getObservableUser().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity user) {
                Logger.i(user == null ? "user is null" : "onChanged:" +user.getUid()+ user.getFirstName() + user.getLastName());
                if (user != null) {
                    roomBinding.setUser(user);
                } else {

                }
                roomBinding.executePendingBindings();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String firstName = roomBinding.developkitActivityRoomFirstEt.getText().toString();
        String lastName = roomBinding.developkitActivityRoomLastEt.getText().toString();
        UserEntity user = userViewModel.getObservableUser().getValue();
        if (user == null) {
            user = new UserEntity();
        }
        user.setUid(0);
        user.setFirstName(firstName == null || firstName.isEmpty() ? "wu" : firstName);
        user.setLastName(lastName == null || lastName.isEmpty() ? "yuanqing" : lastName);

        userViewModel.setUser(user);
    }
}
