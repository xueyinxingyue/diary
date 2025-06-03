package com.app.diary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.diary.R;

public class ProfileFragment extends BaseFragment {

    private ImageView ivAvatar;
    private TextView tvUsername;
    private Button btnSettings;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setView();
    }

    private void initView(View view) {
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvUsername = view.findViewById(R.id.tv_username);
        btnSettings = view.findViewById(R.id.btn_settings);
        btnLogout = view.findViewById(R.id.btn_logout);
    }

    private void setView() {
        // 这里应该从SharedPreferences或数据库加载用户信息
        tvUsername.setText("用户名");

        btnSettings.setOnClickListener(v -> {
            // 跳转到设置页面
        });

        btnLogout.setOnClickListener(v -> {
            // 清除登录状态
            // SharedPreferences.Editor editor = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE).edit();
            // editor.putBoolean("is_logged_in", false).apply();

            // 跳转到登录页
            getNavController().navigate(R.id.action_profile_to_login);
        });
    }
}