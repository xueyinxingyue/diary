package com.app.diary.ui.viewModel;

import android.app.Application;
import android.util.Log;

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

    private MutableLiveData<Boolean> loginAttempted = new MutableLiveData<>(false);

    private UserDataSource userDataSource;
    private boolean loaded = false;

    public UserLoginViewModel(@NonNull Application application) {
        super(application);
        userDataSource = ((Mapp) application).getUserDataSource();
    }

    public void clearUserData() {
        userLiveData.setValue(null); // 清空用户数据
    }

    // 获取用户 LiveData
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    /**
     * 加载用户信息（登录逻辑）
     */
//    public void loadData(String username, boolean lazy) {
//        if (lazy && loaded) {
//            return;
//        }
//        loaded = true;
//
//        userDataSource.selectOne(username)
//                .compose(SingleObserverUtils.applyUIScheduler(this))
//                .subscribe(new DisposableSingleObserver<User>() {
//                    @Override
//                    public void onSuccess(User user) {
//                        userLiveData.setValue(user);
//                        Mapp.getInstance().setCurrentUserId(user.getId());
//                        Mapp.getInstance().setCurrentUsername(user.getUsername());
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        userLiveData.setValue(null);
//                    }
//                });
//    }

//    private boolean isLoginAttempt = false;  // 新增字段，标记是否为登录尝试

    public void loadData(String username, boolean lazy) {
        Log.d("tag", "开始加载数据，登录尝试状态: " + isLoginAttempted());
        if (lazy && loaded) {
            return;
        }

        // 标记为登录尝试
        setLoginAttempted(true);
        loaded = true;

        userDataSource.selectOne(username)
                .compose(SingleObserverUtils.applyUIScheduler(this))
                .subscribe(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        // 只在登录尝试时更新全局状态
                        if (isLoginAttempted()) {
                            Mapp.getInstance().setCurrentUserId(user.getId());
                            Mapp.getInstance().setCurrentUsername(user.getUsername());
                        }
                        userLiveData.setValue(user);
                        setLoginAttempted(false); // 重置状态
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 如果是登录尝试，才设置null触发错误提示
                        if (isLoginAttempted()) {
                            userLiveData.setValue(null);
                            errorLiveData.setValue(e.getMessage());
                        }
                        loaded = false;
                        setLoginAttempted(false);
                    }
                });
    }

    // 移除 clearLoginAttempt() 方法，改用 setLoginAttempted(false)
//    public void clearLoginAttempt() {
//        isLoginAttempt = false;
//    }
    public void setLoginAttempted(boolean attempted) {
        loginAttempted.setValue(attempted);
    }

    public boolean isLoginAttempted() {
        return loginAttempted.getValue() != null && loginAttempted.getValue();
    }
}