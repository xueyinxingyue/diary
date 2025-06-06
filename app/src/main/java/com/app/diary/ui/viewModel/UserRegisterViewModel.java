package com.app.diary.ui.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.diary.Mapp;
import com.app.diary.bean.User;
import com.app.diary.data.UserDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    /**
     * 校验用户名是否可用
     */
    public boolean isUsernameExists(String username) {
        try {
            return userDataSource.existsByUsername(username);
        } catch (Exception e) {
            Log.e("tag", "该用户已存在，请登录", e);
            return false;
        }
    }

    @SuppressLint("CheckResult")
    public void registerUser(User user) {
        userDataSource.insertUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {
                    registerSuccess.setValue(true);
                    Log.i("tag", "注册成功，欢迎"+ user.getUsername());
                }, throwable -> {
                    errorLiveData.setValue("注册失败：" + throwable.getMessage());
                    Log.e("tag", "注册失败", throwable);
                });
    }
}