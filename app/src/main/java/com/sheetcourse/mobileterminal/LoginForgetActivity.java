package com.sheetcourse.mobileterminal;

import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sheetcourse.mobileterminal.DAO.LoginInfoDao;
import com.sheetcourse.mobileterminal.activity.BaseActivity;
import com.sheetcourse.mobileterminal.database.LoginDBdatabase;
import com.sheetcourse.mobileterminal.network.Login;
import com.sheetcourse.mobileterminal.pojo.LoginInfo;
import com.sheetcourse.mobileterminal.utils.CountDownTimerUtils;
import com.sheetcourse.mobileterminal.utils.HideTextWatcher;

import java.util.regex.Pattern;

public class LoginForgetActivity extends BaseActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    private EditText verifycode;
    private EditText affirmPsd;
    private String netVerifyCode;
    private String upusername;
    private TextView getVerifycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        username = findViewById(R.id.re_username);
        password = findViewById(R.id.re_password);
        verifycode = findViewById(R.id.re_verifycode);
        affirmPsd = findViewById(R.id.re_affirm);
        getVerifycode = findViewById(R.id.getVerifycode);
        Button resetPsd=findViewById(R.id.resetPsd);
        // 给phone(username)添加文本变更监听器，用于键盘自动隐藏功能
        username.addTextChangedListener(new HideTextWatcher(username, 11, LoginForgetActivity.this));
        // 给password添加文本变更监听器，用于键盘自动隐藏功能
        password.addTextChangedListener(new HideTextWatcher(password, 6, LoginForgetActivity.this));
        // 给affirmPsd添加文本变更监听器，用于键盘自动隐藏功能
        affirmPsd.addTextChangedListener(new HideTextWatcher(affirmPsd, 6, LoginForgetActivity.this));
        // 给verifycode添加文本变更监听器，用于键盘自动隐藏功能
        verifycode.addTextChangedListener(new HideTextWatcher(verifycode, 4, LoginForgetActivity.this));

        upusername = getIntent().getStringExtra("username");
        if(!upusername.equals("")){
            username.setText(upusername);
        }
        getVerifycode.setOnClickListener(this);
        resetPsd.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
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
            case R.id.resetPsd:
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            Looper.prepare();
                            resetPassword(inputUsername);
                            Looper.loop();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }
    public void getIdentifyAndUpdateLocalPsd(String username){
        LoginDBdatabase db = Room.databaseBuilder(getApplicationContext(),
                LoginDBdatabase.class, "logink").build();
        LoginInfoDao lDAO= db.loginInfoDao();
        LoginInfo info= lDAO.getUserByUsername(username);
        if(info!=null){
            lDAO.updateUser(info);
        }
    }
    //验证是否为合格的手机号
    public Boolean isTruePhone(String phone){

//        ^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\d{8}$
//        String PHONE_PATTERN="^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\d{8}$";
        String PHONE_PATTERN = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147)|(19[0-9]))\\d{8}$";
        boolean isPhone = Pattern.compile(PHONE_PATTERN).matcher(phone).matches();
        if(isPhone)return true;
        else return false;
    }
    //重置密码逻辑
    public void resetPassword(String inputUsername){
        String inputPassword = password.getText().toString();
        if (inputPassword.length() < 6) {
            Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        String inputAffirm = affirmPsd.getText().toString();
        String inputVerifycode = verifycode.getText().toString();
        if (inputVerifycode.length() < 4) {
            Toast.makeText(this, "请输入4位验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        // 点击了“确定”按钮
        if (!inputPassword.equals(inputAffirm)) {
            Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        //netVerifyCode="1231";  //测试写死
        if(netVerifyCode==null){
            Toast.makeText(this, "请发送并获取验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(netVerifyCode.equals("网络请求失败")){
            Toast.makeText(this, "验证码发送延时，优化网络试试？", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!netVerifyCode.equals(inputVerifycode)) {
            Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        String info = "";
        try {
            info = new Login().ResetPsd(inputUsername,inputPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(info.equals("密码已重置")){
            getIdentifyAndUpdateLocalPsd(inputUsername);
            Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(LoginForgetActivity.this, info, Toast.LENGTH_LONG).show();
        }
        // 以下把修改好的新密码返回给上一个页面
        Intent intent = new Intent();
        intent.putExtra("password", inputPassword);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}