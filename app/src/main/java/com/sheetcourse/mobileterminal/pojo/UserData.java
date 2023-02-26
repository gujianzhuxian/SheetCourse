package com.sheetcourse.mobileterminal.pojo;

public class UserData {
    private Integer id;
    private String username;
    private String password;
    private String identify;

    public UserData() {
    }

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserData(Integer id, String username, String password, String userIdentify) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.identify = userIdentify;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserIdentify() {
        return identify;
    }

    public void setUserIdentify(String userIdentify) {
        this.identify = userIdentify;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identify='" + identify + '\'' +
                '}';
    }
}
