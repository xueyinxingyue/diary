package com.app.diary.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.app.diary.bean.Diary;
import com.app.diary.room.converter.DateConverter;
import com.app.diary.room.dao.DiaryDao;

@Database(entities = {Diary.class}, version = 2)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract DiaryDao diaryDao();

}