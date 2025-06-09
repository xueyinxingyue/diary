package com.app.diary.data;

import androidx.annotation.NonNull;

import com.app.diary.bean.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

/**
 * 用户数据源
 */
public interface UserDataSource {
    /**
     * 新增一个用户
     */
    Completable insertUser(@NonNull User user);

    boolean existsByUsername(String username);

    /**
     * 根据username查询用户
     */
    Single<User> selectOne(String username);
}
