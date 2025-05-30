package com.app.diary.ui.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.diary.Mapp;
import com.app.diary.bean.User;
import com.app.diary.data.UserDataSource;
import com.app.diary.utils.rxjava.CompletableObserverUtils;

import io.reactivex.rxjava3.core.Completable;

public class UserRegisterViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private UserDataSource userDataSource;

    public UserRegisterViewModel(@NonNull Application application) {
        super(application);
        userDataSource = ((Mapp) application).getUserDataSource();
    }

    public LiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    //TODO 无法创建用户
    @SuppressLint("CheckResult")
    public void registerUser(User user) {
        userDataSource.selectOne(user.getUsername())
                .flatMapCompletable(existingUser -> {
                    if (existingUser != null) {
                        return Completable.error(new Exception("用户名已存在"));
                    } else {
                        return userDataSource.insertUser(user);
                    }
                })
                .compose(CompletableObserverUtils.applyUIScheduler(this))
                .subscribe(() -> {
                    registerSuccess.setValue(true);
                }, throwable -> {
                    errorLiveData.setValue(throwable.getMessage());
                });
    }
}