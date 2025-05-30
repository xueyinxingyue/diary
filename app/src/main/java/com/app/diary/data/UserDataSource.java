package com.app.diary.data;

import androidx.annotation.NonNull;

import com.app.diary.bean.User;

import io.reactivex.rxjava3.core.Completable;

public interface UserDataSource {
    /**
     * 新增一个用户
     */
    Completable insertUser(@NonNull User user);
}
