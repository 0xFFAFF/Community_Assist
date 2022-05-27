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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.community_assist.admin.Admin;
import com.example.community_assist.admin.AdminDao;

public class AdminReg extends Activity implements View.OnClickListener {
    Button backButton;
    Button regButton;

    EditText adminAddress, adminAccount, adminPwd,adminPwd2;

    AppDatabase db;
    AdminDao adminDao;

    Handler workHandler;
    HandlerThread mHandlerThread;

    private final int CONFLICT_INFO = 1;
    private final int SUCCESS_INFO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_reg_menu);
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
                        Toast.makeText(AdminReg.this, "账号被占用，注册失败", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS_INFO:
                        Toast.makeText(AdminReg.this, "注册成功", Toast.LENGTH_SHORT).show();
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

        adminDao = db.AdminDao();


        backButton = findViewById(R.id.back_button_inAdminReg);
        regButton = findViewById(R.id.reg_button_of_admin);

        adminAddress = findViewById(R.id.address_of_admin_edit);
        adminAccount = findViewById(R.id.edit_admin);
        adminPwd = findViewById(R.id.edit_admin_pwd);
        adminPwd2 = findViewById(R.id.edit_configure_admin_pwd);

        backButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_button_inAdminReg:
                finish();
                break;
            case R.id.reg_button_of_admin:
                String strAdminAddress = adminAddress.getText().toString().trim();
                String strAdminAccount =adminAccount.getText().toString().trim();
                String strAdminPwd = adminPwd.getText().toString().trim();
                String strAdminPwd2 = adminPwd2.getText().toString().trim();

                boolean judgeEmpty = (strAdminAddress.isEmpty()) || (strAdminAccount.isEmpty()) ||
                        (strAdminPwd.isEmpty()) || (strAdminPwd2.isEmpty());

                if(judgeEmpty){
                    Toast.makeText(AdminReg.this, "不能有空白项呢", Toast.LENGTH_SHORT).show();
                }
                else if(!strAdminPwd.equals(strAdminPwd2)){
                    Toast.makeText(AdminReg.this, "两次输入密码不一致呢", Toast.LENGTH_SHORT).show();
                }

//                Bundle bundle = new Bundle();
//                bundle.putString("address",strAdminAddress);
//                bundle.putString("account",strAdminAccount);
//                bundle.putString("pwd",strAdminPwd);
//                Message msg = Message.obtain();
//                msg.arg1 = 5;
//                msg.what = REG_INFO; //消息的标识
//                // b. 通过Handler发送消息到其绑定的消息队列
//                msg.setData(bundle);
//                workHandler.sendMessage(msg);
                else {
                    new Thread(() -> {
                        boolean error = false;
                        try {
                            Admin temp = new Admin(strAdminAccount, strAdminPwd, strAdminAddress);
                            adminDao.insertAdmin(temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message message = Message.obtain();
                            message.what = CONFLICT_INFO;
                            workHandler.sendMessage(message);
                            error = true;
                        }
                        if(!error){
                            Message message = Message.obtain();
                            message.what = SUCCESS_INFO;
                            workHandler.sendMessage(message);
                        }

                    }).start();

//                Admin temp = new Admin(strAdminAccount,strAdminPwd,strAdminAddress);
//                adminDao.insertAdmin(temp);
//                    Toast.makeText(AdminReg.this, "注册成功", Toast.LENGTH_SHORT).show();
                    adminAddress.setText("");
                    adminAccount.setText("");
                    adminPwd.setText("");
                    adminPwd2.setText("");
                    break;
                }
        }
    }


}