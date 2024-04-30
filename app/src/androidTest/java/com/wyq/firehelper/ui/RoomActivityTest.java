package com.wyq.firehelper.ui;

import androidx.arch.core.executor.testing.CountingTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.wyq.firehelper.R;
import com.wyq.firehelper.developkit.room.AppDatabase;
import com.wyq.firehelper.developkit.room.AppExecutors;
import com.wyq.firehelper.developkit.room.RoomActivity;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;

public class RoomActivityTest {

    @Rule
    public ActivityTestRule<RoomActivity> activityTestRule = new ActivityTestRule<>(RoomActivity.class);

    @Rule
    public CountingTaskExecutorRule countingTaskExecutorRule = new CountingTaskExecutorRule();

    @Before
    public void waitForDbCreation() throws Throwable {
        CountDownLatch latch = new CountDownLatch(1);
        LiveData<Boolean> dataBaseCreated = AppDatabase.getInstance(InstrumentationRegistry.getTargetContext()
                , new AppExecutors())
                .getDatabaseCreated();
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataBaseCreated.observeForever(new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (java.lang.Boolean.TRUE.equals(aBoolean)) {
                            dataBaseCreated.removeObserver(this);
                            latch.countDown();
                        }
                    }
                });
            }
        });

        MatcherAssert.assertThat("",latch.await(1, TimeUnit.MINUTES), CoreMatchers.is(true));
    }

    @Test
    public void clickCommitBt() throws Throwable {
//        drain();
//        //清空输入框内容
//        Espresso.onView(ViewMatchers.withId(com.wyq.firehelper.developkit.R.id.developkit_activity_room_first_et))
//                .perform(clearText());
//        Espresso.onView(ViewMatchers.withId(com.wyq.firehelper.developkit.R.id.developkit_activity_room_last_et))
//                .perform(clearText());
//
//        drain();
//        //点击commit按钮
//        Espresso.onView(ViewMatchers.withId(com.wyq.firehelper.developkit.R.id.developkit_activity_room_commit_bt))
//                .check(ViewAssertions.matches(ViewMatchers.withText("commit")))
//                .perform(click());
//
//        drain();
//        //检查输入框内容是否为默认值
//        Espresso.onView(ViewMatchers.withId(com.wyq.firehelper.developkit.R.id.developkit_activity_room_first_et))
//                .check(ViewAssertions.matches(ViewMatchers.withText("wu")));
//        Espresso.onView(ViewMatchers.withId(com.wyq.firehelper.developkit.R.id.developkit_activity_room_last_et))
//                .check(ViewAssertions.matches(ViewMatchers.withText("yuanqing")));

    }


    /**
     *  Waits until all active tasks are finished.
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public void drain() throws TimeoutException, InterruptedException {
        countingTaskExecutorRule.drainTasks(1,TimeUnit.MINUTES);
    }
}
