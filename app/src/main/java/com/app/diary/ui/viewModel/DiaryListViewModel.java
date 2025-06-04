package com.app.diary.ui.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.diary.Mapp;
import com.app.diary.bean.Diary;
import com.app.diary.data.DiaryDataSource;
import com.app.diary.utils.ToastUtils;
import com.app.diary.utils.rxjava.SingleObserverUtils;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;

public class DiaryListViewModel extends BaseViewModel {
    private MutableLiveData<List<Diary>> diaryListLiveData = new MutableLiveData<>();
    private DiaryDataSource diaryDataSource;

    public DiaryListViewModel(@NonNull Application application) {
        super(application);
        diaryDataSource = ((Mapp) application).getDiaryDataSource();
    }
    public LiveData<List<Diary>> getDiaryListLiveData() {
        return diaryListLiveData;
    }

    public void loadData(boolean lazy) {
        if (lazy && loaded) return;
        loaded = true;

        // 动态获取当前用户 ID
        long currentUserId = Mapp.getInstance().getCurrentUserId();
        Log.d("tag", "[加载日记列表] Current userId: " + currentUserId);
        diaryDataSource.selectList(currentUserId) // 使用最新的 userId
                .compose(SingleObserverUtils.applyUIScheduler(this))
                .subscribe(new DisposableSingleObserver<List<Diary>>() {
                    @Override
                    public void onSuccess(List<Diary> list) {
                        diaryListLiveData.setValue(list);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.showShort("获取失败: " + e.getMessage());
                        diaryListLiveData.setValue(null);
                    }
                });
    }
}