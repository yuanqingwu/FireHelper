package com.wyq.firehelper.developKit.Room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.orhanobut.logger.Logger;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    @VisibleForTesting
    private static final String DATABASE_NAME = "user-db";
    private static AppDatabase sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }


    private static AppDatabase buildDatabase(final Context context, AppExecutors executors) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Logger.i("onCreate user-db");
                        executors.diskIO().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(context, executors);
                            User user = new User();
                            user.setUid(0);
                            user.setFirstName("Wu");
                            user.setLastName("Yuanqing");
                            database.runInTransaction(() -> {
                                database.userDao().insertUser(user);
                            });
                            //notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(Context context){
        if(context.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
