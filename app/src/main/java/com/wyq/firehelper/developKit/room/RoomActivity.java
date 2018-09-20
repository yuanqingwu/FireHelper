package com.wyq.firehelper.developKit.room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.FireHelpApplication;
import com.wyq.firehelper.databinding.DevelopkitActivityRoomBinding;
import com.wyq.firehelper.developKit.room.entity.UserEntity;

import io.reactivex.disposables.CompositeDisposable;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    public DevelopkitActivityRoomBinding roomBinding;

    private UserViewModel userViewModel;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomBinding = DataBindingUtil.setContentView(this, R.layout.developkit_activity_room);

        roomBinding.developkitActivityRoomCommitBt.setOnClickListener(this);

        UserViewModel.Factory factory = new UserViewModel.Factory(((FireHelpApplication) getApplication()).getRepository());

        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);

        subscribeUi(userViewModel);

        roomBinding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void subscribeUi(UserViewModel userViewModel) {
        //observe user
        userViewModel.getObservableUser().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity user) {
                Logger.i(user == null ? "user is null" : "onChanged:" + user.getFirstName() + user.getLastName());
                if (user != null) {
                    roomBinding.setUser(user);
                } else {

                }
                roomBinding.executePendingBindings();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.clear();
    }

    @Override
    public void onClick(View v) {
        String firstName = roomBinding.developkitActivityRoomFirstEt.getText().toString();
        String lastName = roomBinding.developkitActivityRoomLastEt.getText().toString();
        UserEntity user = (UserEntity) userViewModel.getObservableUser().getValue();
        if (user == null) {
            user = new UserEntity();
        }
        user.setUid(0);
        user.setFirstName(firstName == null || firstName.isEmpty() ? "wu" : firstName);
        user.setLastName(lastName == null || lastName.isEmpty() ? "yuanqing" : lastName);

        userViewModel.setUser(user);
    }
}
