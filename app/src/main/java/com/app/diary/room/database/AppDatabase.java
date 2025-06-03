package com.app.diary.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.app.diary.bean.Diary;
import com.app.diary.bean.User;
import com.app.diary.room.converter.DateConverter;
import com.app.diary.room.dao.DiaryDao;
import com.app.diary.room.dao.UserDao;

@Database(entities = {Diary.class, User.class}, version = 4)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract DiaryDao diaryDao();

    public abstract UserDao userDao();

}