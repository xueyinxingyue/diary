package com.app.diary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.diary.R;

public class RegisterFragment extends BaseFragment {

    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;

    private TextView tvToLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setView();
    }

    private void initView(View view) {
        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btnRegister = view.findViewById(R.id.btn_register);

        tvToLogin = view.findViewById(R.id.tv_to_login);
    }

    private void setView() {
        btnRegister.setOnClickListener(v -> {
            if (validateInput()) {
                // 这里添加实际的注册逻辑
                Toast.makeText(requireContext(), "注册成功", Toast.LENGTH_SHORT).show();
                // 注册成功后跳转到首页
                getNavController().navigate(R.id.action_register_to_index);
            }
        });
        tvToLogin.setOnClickListener(v -> {
            getNavController().navigate(R.id.login_fragment);
        });
    }

    private boolean validateInput() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (username.isEmpty()) {
            etUsername.setError("请输入用户名");
            return false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("请输入有效的邮箱");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            etPassword.setError("密码至少6位");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("两次密码不一致");
            return false;
        }

        return true;
    }
}