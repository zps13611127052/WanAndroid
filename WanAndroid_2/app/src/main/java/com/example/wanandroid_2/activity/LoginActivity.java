package com.example.wanandroid_2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanandroid_2.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mLoginBackImg;
    /**
     * 请输入用户名
     */
    private EditText mLoginAdmin;
    /**
     * 请输入密码
     */
    private EditText mLoginPwd;
    /**
     * 登录
     */
    private Button mLoginBtnLogin;
    /**
     * 或
     */
    private TextView mOr;
    /**
     * 注册
     */
    private Button mLoginBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mLoginBackImg = (ImageView) findViewById(R.id.login_back_img);
        mLoginAdmin = (EditText) findViewById(R.id.login_admin);
        mLoginPwd = (EditText) findViewById(R.id.login_pwd);
        mLoginBtnLogin = (Button) findViewById(R.id.login_btn_login);
        mLoginBtnLogin.setOnClickListener(this);
        mOr = (TextView) findViewById(R.id.or);
        mLoginBtnRegister = (Button) findViewById(R.id.login_btn_register);
        mLoginBtnRegister.setOnClickListener(this);
        mLoginBackImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_btn_login:
                break;
            case R.id.login_btn_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_back_img:
                finish();
                break;
        }
    }
}
