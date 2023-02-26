package com.example.springbootmybatis.mapper;

import com.example.springbootmybatis.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
public interface UserMapper {
    List<User> findAllUser();
    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUserByUsername(@Param("username") String username);

    @Select("SELECT count(*) FROM user WHERE username = #{username}")
    int getUserCountByUsername(@Param("username") String username);

    @Insert("insert into user values(#{user.id},#{user.username}, #{user.password}, #{user.identify})")
    int addUser(@Param("user") User user);

    @Update("UPDATE user SET password = #{user.password} WHERE username = #{user.username}")
    void updatePsd(@Param("user") User user);



}
