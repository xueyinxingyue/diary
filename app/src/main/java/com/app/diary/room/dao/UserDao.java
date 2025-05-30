package com.app.diary.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.app.diary.bean.User;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface UserDao {
    /**
     * 新增用户
     */
    @Insert
    Completable insert(User user);
}
