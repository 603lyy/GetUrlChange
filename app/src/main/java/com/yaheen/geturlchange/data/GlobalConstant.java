package com.yaheen.geturlchange.data;

/**
 * Created by Administrator on 2017/5/5.
 */

public class GlobalConstant {
    public static final boolean isDebug = true; // 是否代开log日志
    public static final boolean isOPenGoogle = false; // 是否打开谷歌验证
    public static final boolean isUpLoadFile = false; // 上传图片是否为文件上传

    //TOKEN失效错误码
    public static final int TOKEN_DISABLE1 = 4000;
    public static final int TOKEN_DISABLE2 = -1;

    //自定义错误码
    public static final int JSON_ERROR = -9999;
    public static final int VOLLEY_ERROR = -9998;
    public static final int TOAST_MESSAGE = -9997;
    public static final int OKHTTP_ERROR = -9996;
    public static final int NO_DATA = -9995;
    public static final int SERVER_ERROR = -9994;
    public static final int SERVER_TIME_OUT = -9993;

    ///////////////////permission
    public static final int PERMISSION_CONTACT = 0;
    public static final int PERMISSION_CAMERA = 1;
    public static final int PERMISSION_STORAGE = 2;

    //常用常量
    public static final int TAKE_PHOTO = 10;
    public static final int CHOOSE_ALBUM = 11;

    /**
     * k线图对应tag值
     */
    public static final int TAG_DIVIDE_TIME = 0; // 分时图
    public static final int TAG_ONE_MINUTE = 1; // 1分钟
    public static final int TAG_FIVE_MINUTE = 2; // 5分钟
    public static final int TAG_AN_HOUR = 3; // 1小时
    public static final int TAG_DAY = 4; // 1天
    public static final int TAG_FIFTEEN_MINUTES = 5; // 15分钟
    public static final int TAG_THIRTY_MINUTE = 6; // 30分钟
    public static final int TAG_WEEK = 7; // 1周
    public static final int TAG_MONTH = 8; // 1月


    /**
     * 应用该自定义常量
     */
    public static final String LIMIT_PRICE = "LIMIT_PRICE"; // 限价
    public static final String MARKET_PRICE = "MARKET_PRICE"; // 市价
    public static final String BUY = "BUY"; // 买
    public static final String SELL = "SELL"; // 卖
    public static final String CNY = "CNY"; // 人民币
    public static final String HK = "HK";
    public static final String USD = "USD";
    public static final String JPY = "JPY";
    public static final int PageSize = 10;
    public static final String UserSaveFileName = "user.info";
    public static final String LOGIN_LANGUAGE = "LOGIN_LANGUAGE";
}
