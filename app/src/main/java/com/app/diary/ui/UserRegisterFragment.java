package com.app.diary.ui;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.app.diary.bean.User;
import com.app.diary.ui.viewModel.UserRegisterViewModel;

public class UserRegisterFragment extends BaseFragment {

    private EditText etName, etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvToLogin;

    private UserRegisterViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initViewModel();
        setView();
    }

    private void initView(View view) {
        etName = view.findViewById(R.id.et_name);
        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        btnRegister = view.findViewById(R.id.btn_register);
        tvToLogin = view.findViewById(R.id.tv_to_login);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(UserRegisterViewModel.class);

        viewModel.getRegisterSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Log.i("tag", "注册成功");
                Toast.makeText(requireContext(), "注册成功", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigate(R.id.action_register_to_index);
            }
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMsg -> {
            Log.i("tag", errorMsg);
            Toast.makeText(requireContext(), "注册失败：" + errorMsg, Toast.LENGTH_SHORT).show();
        });
    }

    private void setView() {
        btnRegister.setOnClickListener(v -> {
            if (validateInput()) {
                String name = etName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();

                User newUser = new User();
                newUser.setName(name);
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setEmail(email);

                viewModel.registerUser(newUser);
            }
        });

        tvToLogin.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.login_fragment);
        });
    }

    private boolean validateInput() {
        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (name.isEmpty()){
            etName.setError("请输入昵称");
            return false;
        }

        if (username.isEmpty()) {
            etUsername.setError("请输入用户名");
            return false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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