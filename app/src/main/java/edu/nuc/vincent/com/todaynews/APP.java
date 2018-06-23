package edu.nuc.vincent.com.todaynews;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

import edu.nuc.vincent.com.todaynews.utils.Constant;

/**
 * Created by Vincent on 2018/6/21.
 */

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this, Constant.APPID,Constant.APPKEY);
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);//开启调试日志
    }
}
