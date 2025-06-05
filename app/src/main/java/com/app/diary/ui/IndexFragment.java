package com.app.diary.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.app.diary.Mapp;
import com.app.diary.R;
import com.app.diary.ui.viewModel.UserLoginViewModel;
import com.app.diary.utils.AppUtils;

/**
 * 首页
 */
public class IndexFragment extends BaseFragment {

    private Toolbar toolbar;//标题栏控件
    private Button browseButton;//查看日记按钮控件
    private Button createButton;//创建日记按钮控件

//    private Button loginButton;
    private Button profileButton;

    private TextView versionTextView;//当前版本文本控件

    private UserLoginViewModel userLoginViewModel;

    String username = Mapp.getInstance().getCurrentUsername();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userLoginViewModel = new ViewModelProvider(this).get(UserLoginViewModel.class);
        initView(view);
        setView();
        userLoginViewModel.loadData(username,true);
    }

    /**
     * 初始化数据
     */
    private void initData(){
    }

    /**
     * 初始化控件
     */
    private void initView(@NonNull View view) {
        toolbar = view.findViewById(R.id.toolbar);
        browseButton = view.findViewById(R.id.browse_button);
        createButton = view.findViewById(R.id.create_button);

        profileButton = view.findViewById(R.id.profile_button);
        versionTextView = view.findViewById(R.id.version_textView);
    }

    /**
     * 设置控件
     */
    private void setView() {
        //将标题栏关联到页面
        initSupportActionBar(toolbar, false);

        //设置查看日记按钮的点击事件
        browseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getNavController().navigate(IndexFragmentDirections.diaryListAction());
            }

        });

        //设置创建日记按钮的点击事件
        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getNavController().navigate(IndexFragmentDirections.diaryEditAction(0));
            }

        });

        //将当前版本名称显示在文本上
        versionTextView.setText("当前版本:v" + AppUtils.getVersionName());

        profileButton.setOnClickListener(v -> {
            getNavController().navigate(R.id.action_index_to_profile);
        });
    }

}