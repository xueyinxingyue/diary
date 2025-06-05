package com.app.diary.ui;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.app.diary.bean.User;

import com.app.diary.Mapp;
import com.app.diary.R;
import com.app.diary.data.UserDataSource;
import com.app.diary.ui.viewModel.UserLoginViewModel;
import com.app.diary.utils.rxjava.SingleObserverUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserProfileFragment extends BaseFragment {

    private ImageView ivAvatar;
    private TextView tvNickname, tvUsername, tvEmail;
    private TextView tvUsernameDetail, tvEmailDetail;

    private UserDataSource userDataSource;
    String username = Mapp.getInstance().getCurrentUsername();
    private Button btnSettings, btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    private void initDataSource() {
        userDataSource = ((Mapp) requireActivity().getApplication()).getUserDataSource();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initDataSource();// 初始化数据源
        loadUserData();
        setView();
    }

    private void initView(View view) {
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvNickname = view.findViewById(R.id.tv_nickname);
        tvUsernameDetail = view.findViewById(R.id.tv_username_detail);
        tvEmailDetail = view.findViewById(R.id.tv_email_detail);
        btnSettings = view.findViewById(R.id.btn_settings);
        btnLogout = view.findViewById(R.id.btn_logout);
    }

private void loadUserData() {
    String currentUsername = Mapp.getInstance().getCurrentUsername();
    if (currentUsername != null && !currentUsername.isEmpty()) {
        userDataSource.selectOne(currentUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        updateUI(user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("UserProfile", "Failed to load user data", e);
                    }
                });
    }
}

    private void updateUI(User user) {
        if (user != null) {
            // 设置昵称
            tvNickname.setText(user.getName());

            // 设置用户名
            tvUsernameDetail.setText(user.getUsername());

            // 设置邮箱
            tvEmailDetail.setText(user.getEmail());

            // 可以在这里添加头像加载逻辑
            // 例如：Glide.with(this).load(user.getAvatar()).into(ivAvatar);
        }
    }

    private void setView() {
        btnSettings.setOnClickListener(v -> {
            // 跳转到设置页面
        });

        btnLogout.setOnClickListener(v -> {
            // 清除用户状态
            SharedPreferences.Editor editor = getContext()
                    .getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    .edit();
            editor.putBoolean("is_logged_in", false).apply();
            Mapp.getInstance().clearCurrentUser(); // 清除当前用户ID

            ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
            UserLoginViewModel loginViewModel = viewModelProvider.get(UserLoginViewModel.class);
            loginViewModel.clearUserData();

            // 跳转到登录页
            getNavController().navigate(R.id.action_profile_to_login);
        });
    }
}