package com.wyq.firehelper.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.wyq.firehelper.LiveDataTestUtil;
import com.wyq.firehelper.developKit.room.AppDatabase;
import com.wyq.firehelper.developKit.room.UserDao;
import com.wyq.firehelper.developKit.room.entity.UserEntity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class UserDAOTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private UserDao userDao;

    @Before
    public void initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDao = appDatabase.userDao();
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void getUserWhenNoUserInsert() throws InterruptedException {
        List<UserEntity> users = LiveDataTestUtil.getValue(userDao.getAll());
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void getUserAfterInserted() throws InterruptedException {
        userDao.insertUser(TestData.USER_ENTITY);
        userDao.insertUser(TestData.USER_ENTITY1);

        List<UserEntity> users = LiveDataTestUtil.getValue(userDao.getAll());
        Assert.assertThat(users.size(), is(2));
    }


    @Test
    public void getUserById() throws InterruptedException {
        userDao.insertUser(TestData.USER_ENTITY);

        UserEntity user = LiveDataTestUtil.getValue(userDao.getUserById(TestData.USER_ENTITY.getUid()));
        Assert.assertThat(user.getUid(), is(TestData.USER_ENTITY.getUid()));
        Assert.assertThat(user.getFirstName(), is(TestData.USER_ENTITY.getFirstName()));
        Assert.assertThat(user.getLastName(), is(TestData.USER_ENTITY.getLastName()));
    }
}
