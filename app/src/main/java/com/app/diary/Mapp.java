package com.app.diary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.app.diary.data.DiaryDataSource;
import com.app.diary.data.UserDataSource;
import com.app.diary.data.impl.DiaryDataSourceImpl;
import com.app.diary.data.impl.UserDataSourceImpl;
import com.app.diary.room.database.AppDatabase;

public class Mapp extends Application {

    private static Mapp instance;//单例

    private AppDatabase appDatabase;//数据库
    private DiaryDataSource diaryDataSource;//日记数据源

    private UserDataSource userDataSource;//用户数据源

    private long currentUserId = -1;

    private String currentUsername = "";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 获取实例
     */
    public static Mapp getInstance() {
        return instance;
    }

    public void setCurrentUserId(long userId) {
        currentUserId = userId;
    }

    public long getCurrentUserId() {
        return currentUserId;
    }


    public void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

//    修改 Mapp.java 中的方法，避免解析 null：
    public void clearCurrentUser() {
        this.currentUserId = -1; // 或 0，表示无效用户
        this.currentUsername = ""; // 同时清空用户名
    }

    /**
     * 懒加载获取数据库
     */
    public AppDatabase getAppDatabase() {
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "diary.db")
                .addMigrations(new Migration(2, 3) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        // 迁移逻辑
                    }
                })
                .fallbackToDestructiveMigration() // 开发阶段使用
                .build();
        return appDatabase;
    }

    /**
     * 懒加载获取日记数据源
     */
    public DiaryDataSource getDiaryDataSource() {
        if (diaryDataSource == null) {
            diaryDataSource = new DiaryDataSourceImpl(getAppDatabase());
        }
        return diaryDataSource;
    }

    /**
     * 懒加载获取用户数据源
     */
    public UserDataSource getUserDataSource() {
        if (userDataSource == null) {
            // 修改为使用 getAppDatabase() 而不是 AppDatabase.getDatabase()
            AppDatabase db = getAppDatabase();
            userDataSource = new UserDataSourceImpl(db);
        }
        return userDataSource;
    }

}