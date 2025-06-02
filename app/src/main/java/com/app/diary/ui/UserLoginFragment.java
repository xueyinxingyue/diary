package com.app.diary.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.app.diary.R;
import com.app.diary.ui.viewModel.UserLoginViewModel;

public class UserLoginFragment extends BaseFragment {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    private TextView tvToRegister;

    private UserLoginViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_login, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initView(view);
//        setView();
//    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);       // 1. 初始化控件
        initViewModel();      // 2. 初始化 ViewModel（新增这行）
        setView();           // 3. 设置控件事件监听
    }

    private void initView(@NonNull View view) {
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvToRegister = view.findViewById(R.id.tv_to_register);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(UserLoginViewModel.class);

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                String inputPassword = etPassword.getText().toString();
                if (inputPassword.equals(user.getPassword())) {
                    Log.i("tag","登陆成功");
                    Toast.makeText(requireContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_login_to_index);
                } else {
                    Toast.makeText(requireContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "用户不存在", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setView() {
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            if (username.isEmpty()) {
                etUsername.setError("请输入用户名");
                return;
            }

            String password = etPassword.getText().toString();
            if (password.isEmpty()) {
                etPassword.setError("请输入密码");
                return;
            }

            // 调用 loadData 加载用户信息（触发登录逻辑）
            viewModel.loadData(username, false); // false 表示强制加载
        });

        tvToRegister.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_login_to_register);
        });
    }
}