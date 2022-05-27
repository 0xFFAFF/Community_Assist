package com.example.community_assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHandler extends AppCompatActivity implements View.OnClickListener {
    Button addButton,queryButton,deleteButton;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    AdminAdd fragmentAdd = null;
    AdminQuery fragmentQuery = null;
    AdminDelete fragmentDelete = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_handler);
        init();
    }
    public void init(){
        addButton = findViewById(R.id.add_button_inAdminHandler);
        queryButton = findViewById(R.id.query_button_inAdminHandler);
        deleteButton = findViewById(R.id.delete_button_inAdminHandler);

        addButton.setOnClickListener(this);
        queryButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentAdd = new AdminAdd();
        fragmentQuery = new AdminQuery();
        fragmentDelete = new AdminDelete();

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.add_button_inAdminHandler:
                if(fragmentAdd == null){
                    fragmentAdd = (AdminAdd) fragmentManager.findFragmentByTag("Tag");
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.fra_admin, fragmentAdd, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();
                break;
            case R.id.query_button_inAdminHandler:
                fragmentManager.beginTransaction()
                        .replace(R.id.fra_admin, fragmentQuery, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();
                break;
            case R.id.delete_button_inAdminHandler:
                fragmentManager.beginTransaction()
                        .replace(R.id.fra_admin, fragmentDelete, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();
                break;
        }

    }

}