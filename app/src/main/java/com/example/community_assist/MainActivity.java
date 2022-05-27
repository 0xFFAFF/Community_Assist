package com.example.community_assist;

import androidx.annotation.NonNull;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.community_assist.admin.Admin;
import com.example.community_assist.admin.AdminDao;
import com.example.community_assist.user.User;
import com.example.community_assist.user.UserDao;

public class MainActivity extends Activity implements View.OnClickListener{
    EditText userName, userPwd;
    Button loginButton, regButton;
    String strUserName;
    String strUserPwd;
    Spinner mySpinner;

    AppDatabase db;
    AdminDao adminDao;
    UserDao userDao;

    Handler workHandler;
    HandlerThread mHandlerThread;

    String[] spinnerChooses = {"管理员登录","用户登录"};
    final private int ERROR_INFO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_main);
            init();

            mHandlerThread = new HandlerThread("handlerThread");
            mHandlerThread.start();

            workHandler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                    super.handleMessage(msg);
                    if (msg.what==ERROR_INFO){
                        Toast.makeText(MainActivity.this,"账号密码有误，检查后重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
            };
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

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "community-database").build();

        adminDao = db.AdminDao();
        userDao = db.UserDao();

        userName = findViewById(R.id.userNameEdit);
        userPwd = findViewById(R.id.userPwdEdit);

        userPwd.setText("123456");
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
                String whoLogin = mySpinner.getSelectedItem().toString();
                strUserName = userName.getText().toString().trim();
                strUserPwd = userPwd.getText().toString().trim();
                if(whoLogin.equals("管理员登录")){
                    new Thread(()->{
                        try {
                            Admin temp;
                            temp = adminDao.loadAdmin(strUserName,strUserPwd);
                            if(temp==null){
                                Message message = Message.obtain();
                                message.what = ERROR_INFO;
                                workHandler.sendMessage(message);
                            }
                            else{
                                Intent intent = new Intent(MainActivity.this, AdminHandler.class);
                                intent.putExtra("admin",  temp);
                                startActivity(intent);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }).start();
                }else{
                    new Thread(()->{
                        try {
                            User temp;
                            temp = userDao.loadUser(strUserName,strUserPwd);
                            if(temp==null){
                                Message message = Message.obtain();
                                message.what = ERROR_INFO;
                                workHandler.sendMessage(message);
                            }
                            else{
                                Intent intent = new Intent(MainActivity.this, UserHandler.class);
                                intent.putExtra("user",  temp);
                                startActivity(intent);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }).start();

                }
                break;
            case R.id.regButton:
                Intent intent = new Intent(MainActivity.this, RegisterChoose.class);
                startActivity(intent);
                break;
        }
    }
}