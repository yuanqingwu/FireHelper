package com.wyq.firehelper.developKit.Room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.databinding.DevelopkitActivityRoomBinding;

public class RoomActivity extends FragmentActivity {

    public DevelopkitActivityRoomBinding roomBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomBinding = DataBindingUtil.setContentView(this,R.layout.developkit_activity_room);


    }

    @Override
    protected void onResume() {
        super.onResume();
        UserViewModel.Factory factory = new UserViewModel.Factory(getApplication(),0);

        UserViewModel userViewModel = ViewModelProviders.of(this,factory).get(UserViewModel.class);

        roomBinding.setUserViewModel(userViewModel);
        User user = userViewModel.getObservableUser().getValue();


        //observe user
        userViewModel.getObservableUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                userViewModel.setUser(user);
                Logger.i(user==null?"user is null":user.getFirstName()+user.getLastName());
            }
        });
    }
}
