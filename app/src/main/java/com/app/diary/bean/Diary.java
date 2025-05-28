package com.app.diary.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * 日记
 */
@Entity(tableName = "diary")
public class Diary extends BaseBean {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long id;//主键

    @NonNull
    private Date date;//日期

    @NonNull
    private String weather;//天气

    @NonNull
    private String title;//标题

    @NonNull
    private String content;//内容

    @NonNull
    @ColumnInfo(name = "create_time")
    private Date createTime;//创建时间

    @NonNull
    @ColumnInfo(name = "update_time")
    private Date updateTime;//修改时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}