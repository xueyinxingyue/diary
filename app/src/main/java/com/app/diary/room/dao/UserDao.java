package com.app.diary.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.app.diary.bean.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {
    /**
     * 新增用户
     */
    @Insert
    Completable insert(User user);

    @Query("SELECT * FROM user WHERE username = (:username)")
    Single<User> getOne(long username);
}
