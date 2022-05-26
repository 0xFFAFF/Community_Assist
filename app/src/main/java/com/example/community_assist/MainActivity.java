package com.example.community_assist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText userName, userPwd;
    Button loginButton, regButton;
    String strUserName;
    String strUserPwd;
    Spinner mySpinner;
    String[] spinnerChooses = {"管理员登录","用户登录"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            init();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putString("strUserName", strUserName);
        savedInstanceState.putString("strUserPwd", strUserPwd);
        super.onSaveInstanceState(savedInstanceState);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        String currentUserName = savedInstanceState.getString("strUserName");
        String currentUserPwd = savedInstanceState.getString("strUserPwd");

        userName.setText(currentUserName);
        userPwd.setText(currentUserPwd);


    }
    public void init(){
        userName = findViewById(R.id.userNameEdit);
        userPwd = findViewById(R.id.userPwdEdit);
        loginButton = findViewById(R.id.loginButton);
        regButton = findViewById(R.id.regButton);
        mySpinner = findViewById(R.id.planets_spinner);

        loginButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, R.layout.my_spinner);

        adapter.setDropDownViewResource(R.layout.dropline_spinner);

        mySpinner.setAdapter(adapter);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginButton:
                strUserName = userName.toString().trim();
                strUserPwd = userPwd.toString().trim();
                break;
            case R.id.regButton:
                Intent intent = new Intent(MainActivity.this, RegisterChoose.class);
                startActivity(intent);
                break;
        }
    }
}