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

}
