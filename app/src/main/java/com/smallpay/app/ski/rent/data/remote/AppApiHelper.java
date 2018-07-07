package com.smallpay.app.ski.rent.data.remote;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.PostRequest;

/**
 * @author ken
 * @date 2018/5/9.
 */
public class AppApiHelper implements ApiHelper {

    @Override
    public PostRequest doPostRequest() {
        return EasyHttp.post();
    }


}
