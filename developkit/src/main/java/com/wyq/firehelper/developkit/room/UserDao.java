package com.wyq.firehelper.developkit.room;

import com.wyq.firehelper.developkit.room.entity.UserEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<UserEntity>> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    LiveData<List<UserEntity>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    LiveData<UserEntity> findByName(String first, String last);

    @Query("SELECT * FROM user WHERE uid = :uid")
    LiveData<UserEntity> getUserById(int uid);

    @Query("SELECT * FROM user LIMIT 1")
    LiveData<UserEntity> getFirstUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity user);

    @Query("DELETE FROM user")
    void deleteAllUser();

    @Delete
    void delete(UserEntity user);


//    boolean hasUser(int refreshTimeOut);
}
