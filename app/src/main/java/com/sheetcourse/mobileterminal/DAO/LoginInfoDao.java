//DAO层定义 https://developer.android.google.cn/training/data-storage/room/accessing-data?hl=zh-cn
package com.sheetcourse.mobileterminal.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sheetcourse.mobileterminal.pojo.LoginInfo;

@Dao
public interface LoginInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(LoginInfo loginInfo);

    @Update
    void updateUser(LoginInfo loginInfo);

    @Delete
    void deleteUser(LoginInfo loginInfo);

    @Query("SELECT * FROM LoginInfo where username= :name")
    LoginInfo getUserByUsername(String name);

    @Query("select * from LoginInfo where remember = 1 ORDER BY id DESC limit 1")
    LoginInfo getTopUser();
}
