package com.example.community_assist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.community_assist.admin.Admin;
import com.example.community_assist.admin.AdminDao;
import com.example.community_assist.user.User;
import com.example.community_assist.user.UserDao;

@Database(entities = { User.class,Admin.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AdminDao AdminDao();

    public abstract UserDao UserDao();
}
