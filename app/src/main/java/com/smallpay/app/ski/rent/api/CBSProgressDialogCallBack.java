package com.smallpay.app.ski.rent.api;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.HttpException;

/**
 * @author ken
 * @date 2018/3/23.
 */

public class CBSProgressDialogCallBack<T> extends ProgressDialogCallBack<T> {

    public CBSProgressDialogCallBack(final Context context) {
        super(new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(context);
                dialog.setMessage("请求中，请稍后...");
                return dialog;
            }
        }, true, false);
    }

    @Override
    public void onSuccess(T t) {

    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
        if (e.getCause() instanceof HttpException) {
            HttpException httpException = (HttpException) e.getCause();
            try {
                String errorBody = httpException.response().errorBody().string();
                ToastUtils.showShort(JSON.parseObject(errorBody).getString("message"));
            } catch (Exception e1) {
                ToastUtils.showShort(e1.getMessage());
            }
        }
    }

}
