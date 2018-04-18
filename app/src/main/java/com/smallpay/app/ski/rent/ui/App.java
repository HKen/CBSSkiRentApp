package com.smallpay.app.ski.rent.ui;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.smallpay.app.ski.rent.BuildConfig;
import com.zhouyou.http.EasyHttp;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * @author ken
 * @date 2018/3/15.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();

        Utils.init(this);

        EasyHttp.init(this);
        EasyHttp.getInstance().setBaseUrl(BuildConfig.BASE_URL)
                // 打开该调试开关并设置TAG,不需要就不要加入该行
                // 最后的true表示是否打印内部异常，一般打开方便调试错误
                .debug("EasyHttp", true);

    }

}
