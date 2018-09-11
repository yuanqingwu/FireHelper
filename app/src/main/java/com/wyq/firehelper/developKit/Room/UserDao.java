package com.wyq.firehelper.developKit.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    LiveData<List<User>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE uid = :uid")
    LiveData<User> getUserById(int uid);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    LiveData<User> findByName(String first, String last);

    @Insert
    void insertAll(User... user);

    @Delete
    void delete(User user);

}
