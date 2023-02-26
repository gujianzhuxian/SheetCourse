package com.example.springbootmybatis.pojo;

public class User {
    private Integer id;
    private String username;
    private String password;
    //private boolean sex;  //1(true表示男性)  2（false表示女性）
    private String identify;

    public User() {
    }

    public User(Integer id, String username, String password, boolean sex, String identify) {
        this.id = id;
        this.username = username;
        this.password = password;
        //this.sex = sex;
        this.identify = identify;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

//    public boolean getSex() {
//        return sex;
//    }
//
//    public void setSex(boolean sex) {
//        this.sex = sex;
//    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identify='" + identify + '\'' +
                '}';
    }
}
