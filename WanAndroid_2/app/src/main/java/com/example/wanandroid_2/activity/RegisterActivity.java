package com.example.wanandroid_2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wanandroid_2.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mRegisterLeftBack;
    /**
     * 邮箱/手机号
     */
    private EditText mRegisterEdtextAdmin;
    /**
     * 输入密码
     */
    private EditText mRegisterEdtextPwd;
    /**
     * 确认密码
     */
    private EditText mRegisterEdtextPwd2;
    /**
     * 注册
     */
    private Button mRegisterBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        mRegisterLeftBack = (ImageView) findViewById(R.id.register_left_back);
        mRegisterLeftBack.setOnClickListener(this);
        mRegisterEdtextAdmin = (EditText) findViewById(R.id.register_edtext_admin);
        mRegisterEdtextPwd = (EditText) findViewById(R.id.register_edtext_pwd);
        mRegisterEdtextPwd2 = (EditText) findViewById(R.id.register_edtext_pwd2);
        mRegisterBtnRegister = (Button) findViewById(R.id.register_btn_register);
        mRegisterBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.register_left_back:
                finish();
                break;
            case R.id.register_btn_register:
                judge();
                break;
        }
    }

    private void judge() {
        String admin = mRegisterEdtextAdmin.getText().toString();
        String psw = mRegisterEdtextPwd.getText().toString();
        String psw2 = mRegisterEdtextPwd2.getText().toString();

        if (TextUtils.isEmpty(admin)){
            Toast.makeText(this, "注册账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(psw)){
            Toast.makeText(this, "注册密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(psw2)){
            Toast.makeText(this, "注册确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!(mRegisterEdtextPwd2.equals(mRegisterEdtextPwd))){
            Toast.makeText(this, "两次密码不一致请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }else {
            finish();
        }
    }
}
