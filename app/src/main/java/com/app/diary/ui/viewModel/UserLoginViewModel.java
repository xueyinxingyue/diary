package com.app.diary.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.diary.Mapp;
import com.app.diary.bean.User;
import com.app.diary.data.UserDataSource;
import com.app.diary.utils.ToastUtils;
import com.app.diary.utils.rxjava.SingleObserverUtils;

import io.reactivex.rxjava3.observers.DisposableSingleObserver;

public class UserLoginViewModel extends BaseViewModel {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private UserDataSource userDataSource;
    private boolean loaded = false;

    public UserLoginViewModel(@NonNull Application application) {
        super(application);
        userDataSource = ((Mapp) application).getUserDataSource();
    }

    // 获取用户 LiveData
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    /**
     * 加载用户信息（登录逻辑）
     */
    public void loadData(String username, boolean lazy) {
        if (lazy && loaded) {
            return;
        }
        loaded = true;

        userDataSource.selectOne(username)
                .compose(SingleObserverUtils.applyUIScheduler(this))
                .subscribe(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        userLiveData.setValue(user);
                        Mapp.getInstance().setCurrentUserId(user.getId());
                        Mapp.getInstance().setCurrentUsername(user.getUsername());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        userLiveData.setValue(null);
                    }
                });
    }
}