package com.wyq.firehelper.developKit.Room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.databinding.DevelopkitActivityRoomBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RoomActivity extends FragmentActivity implements View.OnClickListener {

    public DevelopkitActivityRoomBinding roomBinding;

    private UserViewModel userViewModel;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomBinding = DataBindingUtil.setContentView(this, R.layout.developkit_activity_room);

        roomBinding.developkitActivityRoomCommitBt.setOnClickListener(this);

//        roomBinding.setUser(userViewModel.getUser());
        roomBinding.setUserViewModel(userViewModel);

    }

    @Override
    protected void onResume() {
        super.onResume();
        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(), 0);

        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);

        subscribeUi(userViewModel);
    }

    private void subscribeUi(UserViewModel userViewModel) {
        //observe user
        userViewModel.getObservableUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    userViewModel.setUser(user);
                    roomBinding.developkitActivityRoomFirstEt.setText(user.getFirstName());
                    roomBinding.developkitActivityRoomLastEt.setText(user.getLastName());
                    roomBinding.developkitActivityRoomNameTv.setText(user.getFirstName() + user.getLastName());
                } else {

                }
                roomBinding.executePendingBindings();
                Logger.i(user == null ? "user is null" : "onChanged:" + user.getFirstName() + user.getLastName());
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
        User user = userViewModel.getUser().getValue();
        if (user == null) {
            user = new User();
        }
        user.setUid(0);
        user.setFirstName(firstName == null || firstName.isEmpty() ? "wu" : firstName);
        user.setLastName(lastName == null || lastName.isEmpty() ? "yuanqing" : lastName);
        roomBinding.developkitActivityRoomCommitBt.setEnabled(false);
        disposable.add(userViewModel.setUser(user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    roomBinding.developkitActivityRoomCommitBt.setEnabled(true);
                }, throwable -> {

                }));
    }
}
