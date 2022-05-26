package com.example.community_assist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterChoose extends AppCompatActivity implements View.OnClickListener{
    Button backButton;
    Button adminRegButton, userRegAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_choose);
        init();

    }
    public void init(){
        backButton = findViewById(R.id.backButtonInChooseMenu);
        adminRegButton = findViewById(R.id.admin_reg_button);
        userRegAdmin = findViewById(R.id.user_reg_button);

        backButton.setOnClickListener(this);
        adminRegButton.setOnClickListener(this);
        userRegAdmin.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.backButtonInChooseMenu:
                finish();
                break;
            case R.id.admin_reg_button:
                Intent intent = new Intent(RegisterChoose.this,AdminReg.class);
                startActivity(intent);
                break;
            case R.id.user_reg_button:
                Intent intent2 = new Intent(RegisterChoose.this,UserReg.class);
                startActivity(intent2);
                break;
        }
    }

}