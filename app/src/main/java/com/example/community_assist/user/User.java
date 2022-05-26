package com.example.community_assist.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "userInfo",primaryKeys = {"userName"})
public class User {
    @NonNull
    public String userName;

    @NonNull
    public String userPwd;

    @ColumnInfo(name = "address")
    public String userAddress;

    @Ignore
    public  User(){
        userName = "";
        userPwd = "";
    };

    public User(@NonNull String userName, @NonNull String userPwd, @NonNull String userAddress){
        this.userName = userName;
        this.userPwd = userPwd;
        this.userAddress = userAddress;
    }

    public String getAddress(){
        return userAddress;
    }

    @NonNull
    public String getAdminName(){
        return userName;
    }

    @NonNull
    public String getAdminPwd(){
        return userPwd;
    }

    public void setAddress(String address){
        this.userAddress = address;
    }

    public void setAdminName(@NonNull String adminName){
        this.userName = adminName;
    }

    public void setAdminPwd(@NonNull String adminPwd){
        this.userPwd = adminPwd;
    }
}
