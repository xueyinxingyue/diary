package com.app.diary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.app.diary.R;

public class LoginFragment extends BaseFragment {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    private TextView tvToRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setView();
    }

    private void initView(@NonNull View view) {
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);

        tvToRegister = view.findViewById(R.id.tv_to_register);
    }

    private void setView() {
        btnLogin.setOnClickListener(v -> {
            // 这里添加登录验证逻辑
            // 验证成功后导航到首页
            getNavController().navigate(R.id.action_login_to_index);
        });

        tvToRegister.setOnClickListener(v -> {
            getNavController().navigate(R.id.action_login_to_register);
        });
    }
}