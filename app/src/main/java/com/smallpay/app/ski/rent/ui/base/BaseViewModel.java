package com.smallpay.app.ski.rent.ui.base;

import android.arch.lifecycle.ViewModel;

/**
 * @author ken
 * @date 2018/4/12.
 */
public class BaseViewModel<N> extends ViewModel {

    private N mNavigator;

    public N getNavigator() {
        return mNavigator;
    }

}
