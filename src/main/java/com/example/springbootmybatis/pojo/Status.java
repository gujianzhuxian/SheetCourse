package com.example.springbootmybatis.pojo;

public class Status {
    private String info;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public void setInfo(String info) {
        this.info = info;
    }

    public Status(String info, User user) {
        this.info = info;
        this.user = user;
    }
}
