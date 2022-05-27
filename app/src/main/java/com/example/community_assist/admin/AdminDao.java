package com.example.community_assist.admin;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AdminDao {
    @Insert//(onConflict = OnConflictStrategy.FAIL)
    void insertAdmin(Admin admin);

    @Query("select * from adminInfo where adminName=(:name) and adminPwd=(:pwd)")
    public Admin loadAdmin(String name,String pwd);

    @Query("SELECT address FROM adminInfo")
    public List<String> loadAddressInfo();

    @Query("delete from adminInfo")
    void delete();

}
