package com.sheetcourse.mobileterminal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sheetcourse.mobileterminal.DAO.LoginInfoDao;
import com.sheetcourse.mobileterminal.activity.BaseActivity;
import com.sheetcourse.mobileterminal.database.LoginDBdatabase;
import com.sheetcourse.mobileterminal.pojo.LoginInfo;
import com.sheetcourse.mobileterminal.network.Login;
import com.sheetcourse.mobileterminal.pojo.Status;
import com.sheetcourse.mobileterminal.utils.HideTextWatcher;

import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private CheckBox rememberPsd;
    //private LoginDBHelper mHelper; // 声明一个用户数据库的帮助器对象
    private TextView forgetPsd;
    private TextView toRegisterPage;
    private ActivityResultLauncher<Intent> ResetPsd;

    LoginInfoDao lDAO;
    LoginDBdatabase db;
    LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.lg_username);
        password =findViewById(R.id.lg_password);
        rememberPsd = findViewById(R.id.lg_rememberPsd);//记住密码
        forgetPsd = findViewById(R.id.lg_forgetPsd);//忘记密码
        toRegisterPage = findViewById(R.id.lg_register);
        Button login=findViewById(R.id.lg_login);
        // 给phone(username)添加文本变更监听器，用于键盘自动隐藏功能
        username.addTextChangedListener(new HideTextWatcher(username, 11, LoginActivity.this));
        // 给password添加文本变更监听器，用于键盘自动隐藏功能
        password.addTextChangedListener(new HideTextWatcher(password, 6, LoginActivity.this));
        login.setOnClickListener(this);
        forgetPsd.setOnClickListener(this);
        toRegisterPage.setOnClickListener(this);


        //A转B，B传数据给A
        ResetPsd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    // 用户密码已改为新密码，故更新密码变量
                    String newPassword = intent.getStringExtra("password");
                }
            }
        });
    }

    private void reload() {
//        LoginInfo info = mHelper.queryTop();
        System.out.println(lDAO.getTopUser());
        LoginInfo info=lDAO.getTopUser();
        if (info != null && info.remember) {
            username.setText(info.username);
            password.setText(info.password);
            rememberPsd.setChecked(true);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //数据库ROOM
        db = Room.databaseBuilder(getApplicationContext(),
                LoginDBdatabase.class, "logink").build();
        lDAO= db.loginInfoDao();
        new Thread(){
            @Override
            public void run(){
                try {
                    //创建looper
                    Looper.prepare();
                    //第二次登录的时候，如果上次保存了密码，则加载账号密码，同时，将选择框选中。
                    reload();
                    //Looper.loop()  负责对消息的分发
                    Looper.loop();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
//        mHelper = LoginDBHelper.getInstance(this);
//        mHelper.openReadLink();
//        mHelper.openWriteLink();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mHelper.closeLink();
//    }
    //登陆成功后，如果勾选了记住密码：
    private void loginSuccess(String identify) {
        Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
        // 保存到數據庫
        if(rememberPsd.isChecked()){
            LoginInfo info = new LoginInfo();
            info.username = username.getText().toString();
            info.password = password.getText().toString();
            info.identify=identify;
            info.remember = rememberPsd.isChecked();
            lDAO.insertUser(info);
            //mHelper.save(info);
        }
    }

    @Override
    public void onClick(View v){
        String inputUsername = username.getText().toString();
        if (inputUsername.length() < 11 && isTruePhone(inputUsername)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        String inputPassword = password.getText().toString();
        if (inputPassword.length() < 6) {
            Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()){
            case R.id.lg_login:
                new Thread(){
                    private String identify;
                    @Override
                    public void run() {
                        try {
                            //创建looper
                            Looper.prepare();
                            Login SignIn=new Login();
                            Status info = SignIn.userLogin(username.getText().toString(),password.getText().toString());
                            if ("登陆成功".equals(info.getInfo())) {
                                if(info.getUser()!=null){
                                    identify = info.getUser().getUserIdentify();
                                }
                                loginSuccess(identify);//登录成功，如勾选记住密码，需将UserData信息存入数据库
                                //System.out.println(lDAO.getTopUser());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
//  可能需要绑定身份                              String qq = new GetQQ().getQQ(username);
//                                bundle.putCharSequence("qq",qq);
//                                intent.putExtras(bundle);
                                startActivity(intent);
                                //LoginInfo kk= lDAO.getUserByUsername(inputUsername);
                                //System.out.println(kk);
                            }else {
                                Toast.makeText(LoginActivity.this, info.getInfo(), Toast.LENGTH_LONG).show();
                            }
                            //Looper.loop()  负责对消息的分发
                            Looper.loop();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.lg_forgetPsd:
                Intent intent = new Intent(LoginActivity.this, LoginForgetActivity.class);
                if(!username.getText().toString().equals("")){
                    intent.putExtra("username", username.getText().toString());
                    ResetPsd.launch(intent);
                }else{startActivity(intent);}
                break;
            case R.id.lg_register:
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
//    //接受传回来的账号
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    final EditText loginUsername = findViewById(R.id.lg_username);
//                    String returnUsername = data.getStringExtra("username");
//                    loginUsername.setText(returnUsername);
//                    loginUsername.setSelection(returnUsername.length());
//                }
//                break;
//            default:
//        }
//    }
}