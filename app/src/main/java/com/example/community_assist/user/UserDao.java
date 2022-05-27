package com.example.community_assist.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.community_assist.admin.Admin;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("select * from userInfo where userName=(:name) and userPwd=(:pwd)")
    public User loadUser(String name,String pwd);

}
