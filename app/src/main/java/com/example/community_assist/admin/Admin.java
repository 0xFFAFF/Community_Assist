package com.example.community_assist.admin;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/*
* 定义存放管理员信息的数据库
* 存放的信息为账号，密码，管理员地址
* 主键为管理员的账号密码，即adminName，adminPwd
*
* */
@Entity(tableName = "adminInfo",primaryKeys = {"adminName"})
public class Admin implements Serializable {
    @NonNull
    public String adminName;
    @NonNull
    public String adminPwd;

    @ColumnInfo(name = "address")
    public String address;
    
    @Ignore
    public Admin(){
        adminName = "";
        adminPwd = "";
    }
    public Admin(@NonNull String adminName, @NonNull String adminPwd, String address){
        this.adminName = adminName;
        this.adminPwd = adminPwd;
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    @NonNull
    public String getAdminName(){
        return adminName;
    }

    @NonNull
    public String getAdminPwd(){
        return adminPwd;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setAdminName(@NonNull String adminName){
        this.adminName = adminName;
    }

    public void setAdminPwd(@NonNull String adminPwd){
        this.adminPwd = adminPwd;
    }
}
