package com.app.diary.data.impl;

import androidx.annotation.NonNull;

import com.app.diary.bean.User;
import com.app.diary.data.UserDataSource;
import com.app.diary.room.database.AppDatabase;

import io.reactivex.rxjava3.core.Completable;

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
}
