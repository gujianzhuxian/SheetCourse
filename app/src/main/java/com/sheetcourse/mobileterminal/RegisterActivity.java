package com.sheetcourse.mobileterminal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sheetcourse.mobileterminal.activity.BaseActivity;
import com.sheetcourse.mobileterminal.network.Login;
import com.sheetcourse.mobileterminal.pojo.UserData;
import com.sheetcourse.mobileterminal.network.Register;
import com.sheetcourse.mobileterminal.utils.CountDownTimerUtils;
import com.sheetcourse.mobileterminal.utils.HideTextWatcher;

import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    private RadioGroup radioGroup;
    private EditText passwordAffirm;
    private EditText password;
    private EditText username;
    private final UserData user=new UserData();;
    private EditText verifycode;
    private String info = "";
    private String netVerifyCode;
    private TextView getVerifycode;
    private Handler mHandler;//与子线程关联的Handler
    private Handler handler;//与主线程关联的Handler


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.re_username);
        password = findViewById(R.id.re_password);
        passwordAffirm = findViewById(R.id.re_affirm);
        radioGroup = findViewById(R.id.re_identify);
        verifycode =findViewById(R.id.re_verifycode);
        Button re_register = findViewById(R.id.re_register);
        getVerifycode = findViewById(R.id.getVerifycode);

        // 给phone(username)添加文本变更监听器，用于键盘自动隐藏功能
        username.addTextChangedListener(new HideTextWatcher(username, 11, RegisterActivity.this));
        // 给password添加文本变更监听器，用于键盘自动隐藏功能
        password.addTextChangedListener(new HideTextWatcher(password, 6, RegisterActivity.this));
        // 给passwordAffirm添加文本变更监听器，用于键盘自动隐藏功能
        passwordAffirm.addTextChangedListener(new HideTextWatcher(passwordAffirm, 6, RegisterActivity.this));
        // 给verifycode添加文本变更监听器，用于键盘自动隐藏功能
        verifycode.addTextChangedListener(new HideTextWatcher(verifycode, 4, RegisterActivity.this));

        // 给re_identify设置单选监听器
        radioGroup.setOnCheckedChangeListener(this);
        re_register.setOnClickListener(this);
        getVerifycode.setOnClickListener(this);
        user.setUserIdentify("teacher");
    }

    @Override
    public void onClick(View v) {
        String inputUsername = username.getText().toString();
        if (inputUsername.length() < 11 && isTruePhone(inputUsername)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()){
            case R.id.getVerifycode:
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getVerifycode, 60000, 1000);
                mCountDownTimerUtils.start();
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            Looper.prepare();
                            netVerifyCode = new Login().getVerifycode(inputUsername);
                            System.out.println(netVerifyCode);
                            Looper.loop();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.re_register:
                String inputPassword = password.getText().toString();
                if (inputPassword.length() < 6) {
                    Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String inputAffirm = passwordAffirm.getText().toString();
                String inputVerifycode = verifycode.getText().toString();
                if (inputVerifycode.length() < 4) {
                    Toast.makeText(this, "请输入4位验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //netVerifyCode="5397"; //用于测试
                if(netVerifyCode==null){
                    Toast.makeText(this, "请发送并获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!netVerifyCode.equals(inputVerifycode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!inputPassword.equals(inputAffirm)) {
                    Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (inputAffirm.equals(inputPassword)) {
//                    //存储账号密码
//                    user.setUsername(inputUsername);
//                    user.setPassword(inputPassword);
//                    //传回账号
//                    Intent intent = new Intent();
//                    intent.putExtra("username", inputUsername);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                } else {
//                    Toast.makeText(RegisterActivity.this,"两次密码不一致或验证码错误", Toast.LENGTH_SHORT).show();
//                }
                user.setId(0);
                user.setUsername(inputUsername);
                user.setPassword(inputPassword);
                Message msg=new Message();
                msg.obj=user;
                Bundle bundle=new Bundle();
//                System.out.println(user.toString());
//                System.out.println(user.getId()+user.getUsername()+user.getPassword());
                new Thread(() -> {
                    try {
                        Looper.prepare();
//                        mHandler = new Handler(){
//                            // 定义处理消息的方法
//                            @Override
//                            public void handleMessage(Message msg){
//                                if(msg.obj==user){}
//                            }
//
//                        };
//                        System.out.println(user);
//                        System.out.println(user.getUserIdentify());
                        info = new Register().UserRegister(user);
                        System.out.println("info1 = " + info);
                        Thread.sleep(1500);
                        if ("注册成功,请登录".equals(info)){
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(RegisterActivity.this, info, Toast.LENGTH_LONG).show();
                        }
                        Looper.loop();//增加部分
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            // 选择了教师身份
            case R.id.re_teacher:
                RadioButton teacher=findViewById(R.id.re_teacher);
                user.setUserIdentify(teacher.getText().toString());
                break;
            // 选择了学生身份
            case R.id.re_student:
                RadioButton stu=findViewById(R.id.re_student);
                user.setUserIdentify(stu.getText().toString());
                break;
        }
    }
    public Boolean isTruePhone(String phone){
//        ^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\d{8}$
//        String PHONE_PATTERN="^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\d{8}$";
        String PHONE_PATTERN = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147)|(19[0-9]))\\d{8}$";
        boolean isPhone = Pattern.compile(PHONE_PATTERN).matcher(phone).matches();
        if(isPhone)return true;
        else return false;
    }
}
