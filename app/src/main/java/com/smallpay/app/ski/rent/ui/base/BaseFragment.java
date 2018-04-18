package com.smallpay.app.ski.rent.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.smallpay.app.ski.rent.R;
import com.zhouyou.http.EasyHttp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author ken
 * @date 2018/3/30.
 */
public class BaseFragment extends SupportFragment {

    public List<Disposable> disposableList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResId = getLayoutResId();
        View view = new View(getContext());
        if (layoutResId != 0) {
            view = inflater.inflate(getLayoutResId(), container, false);
            initView(view);
        }
        return view;
    }

    public int getLayoutResId() {
        return 0;
    }

    public void initView(View view) {
    }


    public void showNfcCardNo(String cardNo) {

        boolean isRequest = false;

        for (Disposable disposable : disposableList) {
            if (disposable.isDisposed()) {
                isRequest = true;
            }
        }

        if(!isRequest){
            request(cardNo);
        }

        ToastUtils.showShort("NFC扫描卡号为：" + cardNo);

    }

    public void request(String cardNo) {

    }

}
