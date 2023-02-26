package com.sheetcourse.mobileterminal.pojo;

public class Status {
    private String info;
    private UserData user;

    @Override
    public String toString() {
        return "Status{" +
                "info='" + info + '\'' +
                ", user=" + user +
                '}';
    }

    public Status() {
    }

    public String getInfo() {
        return info;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public Status(String info, UserData user) {
        this.info = info;
        this.user = user;
    }
    public void setInfo(String info) {
        this.info = info;
    }
}
