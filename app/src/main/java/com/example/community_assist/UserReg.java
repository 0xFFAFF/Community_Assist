package com.example.community_assist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.community_assist.admin.Admin;
import com.example.community_assist.admin.AdminDao;
import com.example.community_assist.user.User;
import com.example.community_assist.user.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserReg extends Activity implements View.OnClickListener {
    Button backButton;
    Button regButton;

    EditText  userAddress,userAccount, userPwd, userPwd2;

    AppDatabase db;
    UserDao userDao;
    AdminDao adminDao;

    Handler workHandler;
    HandlerThread mHandlerThread;

    private final int CONFLICT_INFO = 1;
    private final int SUCCESS_INFO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_menu);
        init();
        mHandlerThread = new HandlerThread("handlerThread");
        mHandlerThread.start();
        workHandler = new Handler(Looper.getMainLooper()){
            @Override
            // 消息处理的操作
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                //设置了两种消息处理操作,通过msg来进行识别
                switch (msg.what){
                    case CONFLICT_INFO:
                        if(msg.arg1==1)
                        Toast.makeText(UserReg.this, "账号被占用，注册失败", Toast.LENGTH_SHORT).show();
                        if(msg.arg1==0)
                            Toast.makeText(UserReg.this, "地址不存在，注册失败", Toast.LENGTH_SHORT).show();

                        break;
                    case SUCCESS_INFO:
                        Toast.makeText(UserReg.this, "注册成功", Toast.LENGTH_SHORT).show();
                        break;

                }


//                Bundle bundle = msg.getData();
//                String strAdminAddress = bundle.getString("address");
//                System.out.println("address:"+strAdminAddress);
//                String strAdminAccount = bundle.getString("account");
//                String strAdminPwd = bundle.getString("pwd");
//                Admin temp = new Admin(strAdminAccount, strAdminPwd, strAdminAddress);
//                adminDao.insertAdmin(temp);


            }
        };

    }
    public void init(){
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "community-database").build();

        userDao = db.UserDao();
        adminDao = db.AdminDao();


        backButton = findViewById(R.id.back_button_inUserReg);
        regButton = findViewById(R.id.reg_button_of_user);

        userAddress = findViewById(R.id.edit_user_address);
        userAccount = findViewById(R.id.user_name_edit);
        userPwd = findViewById(R.id.user_pwd_edit);
        userPwd2 = findViewById(R.id.user_pwd2_edit);



        backButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.back_button_inUserReg:
                finish();
                break;
            case R.id.reg_button_of_user:
                String strUserAddress = userAddress.getText().toString().trim();
                String strUserAccount =userAccount.getText().toString().trim();
                String strUserPwd = userPwd.getText().toString().trim();
                String strUserPwd2 = userPwd2.getText().toString().trim();

                boolean judgeEmpty = (strUserAddress.isEmpty()) || (strUserAccount.isEmpty()) ||
                        (strUserPwd.isEmpty()) || (strUserPwd2.isEmpty());

                if(judgeEmpty){
                    Toast.makeText(UserReg.this, "不能有空白项呢", Toast.LENGTH_SHORT).show();
                }
                else if(!strUserPwd.equals(strUserPwd2)){
                    Toast.makeText(UserReg.this, "两次输入密码不一致呢", Toast.LENGTH_SHORT).show();
                }


                else {
                    new Thread(() -> {
                        int i;
                        try {
                            String[] list = adminDao.loadAddressInfo().toArray(new String[0]);
                            for(i=0;i<list.length;i++){
                                if(list[i].equals(strUserAddress)){
                                    User temp = new User(strUserAccount, strUserPwd, strUserAddress);
                                    userDao.insertUser(temp);
                                    Message message = Message.obtain();
                                    message.what = SUCCESS_INFO;
                                    workHandler.sendMessage(message);
                                }
                            }
                            if(i==list.length){
                                Message message = Message.obtain();
                                message.what = CONFLICT_INFO;
                                message.arg1 = 0;
                                workHandler.sendMessage(message);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Message message = Message.obtain();
                            message.what = CONFLICT_INFO;
                            message.arg1 =1;
                            workHandler.sendMessage(message);

                        }


                    }).start();

//                Admin temp = new Admin(strAdminAccount,strAdminPwd,strAdminAddress);
//                adminDao.insertAdmin(temp);
//                    Toast.makeText(AdminReg.this, "注册成功", Toast.LENGTH_SHORT).show();
                    userAddress.setText("");
                    userAccount.setText("");
                    userPwd.setText("");
                    userPwd2.setText("");
                    break;

        }
    }
}
}