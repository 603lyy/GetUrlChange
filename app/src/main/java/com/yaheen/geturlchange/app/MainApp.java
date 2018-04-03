package com.yaheen.geturlchange.app;

import android.app.Application;

import org.xutils.x;

public class MainApp extends Application {

    /**应用实例**/
    private static MainApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能
        instance = this;
    }

    /**
     *  获得实例
     * @return
     */
    public static MainApp getInstance(){
        return instance;
    }
}
