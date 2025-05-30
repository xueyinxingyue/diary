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

    /**
     * 懒加载获取数据库
     */
    public AppDatabase getAppDatabase() {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "diary.db").addMigrations(new Migration(2, 3) {

                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    // 将SQLite迁移到Room，数据结构未发生变化，所以这里不做任何处理，保持空实现
                    database.execSQL("CREATE TABLE IF NOT EXISTS `user` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`name` TEXT NOT NULL, " +
                            "`username` TEXT NOT NULL, " +
                            "`password` TEXT NOT NULL, " +
                            "`email` TEXT NOT NULL)");

                }

            }).build();

        }
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
            userDataSource = new UserDataSourceImpl(getAppDatabase());
        }
        return userDataSource;
    }

}