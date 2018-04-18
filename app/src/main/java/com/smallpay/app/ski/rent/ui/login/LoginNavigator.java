package com.smallpay.app.ski.rent.ui.login;

/**
 * @author ken
 * @date 2018/4/16.
 */
public interface LoginNavigator {
    void login();

    void handleError(Throwable throwable);

    void openMainActivity();
}
