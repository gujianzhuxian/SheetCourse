package com.sheetcourse.mobileterminal.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "LoginInfo")
public class LoginInfo {
    @PrimaryKey
    public int id;
    @ColumnInfo
    public String username;
    @ColumnInfo
    public String password;
    @ColumnInfo
    public String identify;
    @ColumnInfo
    public boolean remember = false;

//    @Ignore
//    public LoginInfo() {
//    }
//
//    public LoginInfo(int id, String username, String password,String identify, boolean remember) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.remember = remember;
//        this.identify=identify;
//    }

//    @Override
//    public String toString() {
//        return "LoginInfo{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", identify='" + identify + '\'' +
//                ", remember=" + remember +
//                '}';
//    }
}
