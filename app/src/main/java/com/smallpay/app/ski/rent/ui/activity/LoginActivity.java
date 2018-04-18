package com.smallpay.app.ski.rent.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smallpay.app.ski.rent.R;
import com.smallpay.app.ski.rent.api.CBSProgressDialogCallBack;
import com.smallpay.app.ski.rent.data.local.Constants;
import com.smallpay.app.ski.rent.data.remote.ApiEndPoint;
import com.smallpay.app.ski.rent.data.remote.ApiParams;
import com.smallpay.app.ski.rent.util.Base64RSA;
import com.smallpay.app.ski.rent.util.RSAEncrypt;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.RequestBodyUtils;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * @author ken
 * @date 2018/3/20.
 */

public class LoginActivity extends Activity {

    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        etUserName = findViewById(R.id.et_username);
        String username = SPUtils.getInstance().getString(Constants.LOGIN_USER_NAME);
        if (!TextUtils.isEmpty(username)) {
            etUserName.setText(username);
            etUserName.setSelection(username.length());
        }
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    ToastUtils.showShort("请输入用户名");
                } else if (TextUtils.isEmpty(password)) {
                    ToastUtils.showShort("请输入密码");
                } else {
                    requestLogin(username, password);
                }
            }
        });

    }

    private void requestLogin(String username, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiParams.PARAMS_USERNAME, username);
        try {
            params.put(ApiParams.PARAMS_PASSWORD, Base64RSA.encode(RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(Constants.PUBLIC_KEY), password.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(params);
        EasyHttp.post(ApiEndPoint.ENDPOINT_ACCOUNT_LOGIN)
                .upJson(jsonObject.toString())
                .execute(new CBSProgressDialogCallBack<String>(LoginActivity.this) {
                    @Override
                    public void onSuccess(String s) {
                        super.onSuccess(s);
                        SPUtils.getInstance().put(Constants.SHARE_KEY_LOGIN_SUCCESS, s);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        SPUtils.getInstance().put(Constants.LOGIN_USER_NAME, etUserName.getText().toString());
                        LoginActivity.this.finish();
                    }
                });
    }

}
