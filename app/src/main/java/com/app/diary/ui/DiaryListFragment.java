package com.app.diary.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.diary.R;
import com.app.diary.adapter.DiaryRecyclerAdapter;
import com.app.diary.bean.Constant;
import com.app.diary.bean.Diary;
import com.app.diary.utils.SizeUtils;

import java.util.List;

/**
 * 日志列表
 */
public class DiaryListFragment extends BaseFragment {

    private Toolbar toolbar;//标题栏控件
    private RecyclerView recyclerView;//列表控件

    private DiaryRecyclerAdapter diaryRecyclerAdapter;
    private DiaryListViewModel diaryListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavBackStackEntry navBackStackEntry = getNavController().getCurrentBackStackEntry();
        navBackStackEntry.getSavedStateHandle().getLiveData(Constant.DATA_CHANGE).observe(navBackStackEntry, new Observer<Object>() {

            @Override
            public void onChanged(Object dataChanged) {
                if ((boolean) dataChanged) {
                    if (diaryListViewModel != null) {
                        diaryListViewModel.loadData(false);
                    }
                }
            }

        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diary_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        diaryListViewModel = new ViewModelProvider(this).get(DiaryListViewModel.class);
        initView(view);
        setView();
        diaryListViewModel.loadData(true);
    }

    /**
     * 初始化控件
     */
    private void initView(@NonNull View view) {
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    /**
     * 设置控件
     */
    private void setView() {
        //将标题栏关联到页面
        initSupportActionBar(toolbar, true);

        //设置列表的布局样式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置列表的间隔距离
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int count = parent.getAdapter().getItemCount();
                int index = parent.getChildAdapterPosition(view);
                if (index < count - 1) {
                    outRect.set(0, 0, 0, SizeUtils.dp2px(30));
                }
            }

        });
        //设置列表的适配器
        diaryRecyclerAdapter = new DiaryRecyclerAdapter();
        diaryRecyclerAdapter.setOnItemClickListener(new DiaryRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Diary diary, int position) {
                getNavController().navigate(DiaryListFragmentDirections.diaryBrowseAction(diary.getId()));
            }

        });
        recyclerView.setAdapter(diaryRecyclerAdapter);

        //观察日记列表数据并将数据加入适配器
        diaryListViewModel.getDiaryListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Diary>>() {

            @Override
            public void onChanged(List<Diary> list) {
                diaryRecyclerAdapter.setNewData(list);
            }

        });
    }

}