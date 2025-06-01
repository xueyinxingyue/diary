package com.app.diary.data.impl;

import androidx.annotation.NonNull;

import com.app.diary.bean.User;
import com.app.diary.data.UserDataSource;
import com.app.diary.room.database.AppDatabase;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

/**
 * 用户数据源的实现类
 */
public class UserDataSourceImpl implements UserDataSource {
    @NonNull
    private AppDatabase appDatabase;

    public UserDataSourceImpl(@NonNull AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    @Override
    public Completable insertUser(@NonNull User user) {
        return appDatabase.userDao().insert(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return appDatabase.userDao().existsByUsername(username);
    }

    @Override
    public Single<User> selectOne(String username) {
        return appDatabase.userDao().selectOne(username);
    }


}
