package com.wyq.firehelper.observability;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.wyq.firehelper.db.TestData;
import com.wyq.firehelper.developKit.room.UserViewModel;
import com.wyq.firehelper.developKit.room.datasource.DataRepository;
import com.wyq.firehelper.developKit.room.entity.UserEntity;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private DataRepository dataRepository;

    @Captor
    private ArgumentCaptor<UserEntity> argumentCaptor;

    private UserViewModel userViewModel;

    @Before
    public void setUp()throws Exception{
        MockitoAnnotations.initMocks(this);
        userViewModel = new UserViewModel(dataRepository);
    }

    @Test
    public void getUser_whenNoUserSaved(){
        Mockito.when(dataRepository.getUser()).thenReturn(null);

        junit.framework.Assert.assertNull(userViewModel.getObservableUser().getValue());
    }

    @Test
    public void getUser_whenSetUser(){
        userViewModel.setUser(TestData.USER_ENTITY);

        Mockito.verify(dataRepository).insertOrUpdateUser(argumentCaptor.capture());

        Assert.assertThat(argumentCaptor.getValue(), Matchers.is(TestData.USER_ENTITY));
    }
}
