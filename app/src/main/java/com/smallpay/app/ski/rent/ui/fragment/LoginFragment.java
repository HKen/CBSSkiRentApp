package com.smallpay.app.ski.rent.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smallpay.app.ski.rent.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 * 登录
 *
 * @author ken
 * @date 2018/3/14
 *
 */

public class LoginFragment extends SupportFragment {

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        return view;
    }
}
