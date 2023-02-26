package com.sheetcourse.mobileterminal.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sheetcourse.mobileterminal.DAO.LoginInfoDao;
import com.sheetcourse.mobileterminal.pojo.LoginInfo;

@Database(entities = {LoginInfo.class},version=1,exportSchema=false)
public abstract class LoginDBdatabase extends RoomDatabase {
    public abstract LoginInfoDao loginInfoDao();

}
