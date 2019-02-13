package com.yaheen.geturlchange.data;

import com.yaheen.geturlchange.util.okhttp.OkhttpUtils;
import com.yaheen.geturlchange.util.okhttp.StringCallback;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yaheen.geturlchange.data.GlobalConstant.OKHTTP_ERROR;
import static com.yaheen.geturlchange.data.GlobalConstant.SERVER_TIME_OUT;

/**
 * Created by Administrator on 2017/9/25.
 */
public class RemoteDataSource implements DataSource {
    private static RemoteDataSource INSTANCE;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    private RemoteDataSource() {

    }

    /**
     * 有参数的post请求
     *
     * @param url
     * @param params
     * @param dataCallback
     */
    @Override
    public void doStringPost(final String url, HashMap<String, String> params, final DataCallback dataCallback) {
        String token = "";
        OkhttpUtils.post().url(url).addHeader("content-type", "application/x-www-form-urlencoded").addParams(params).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) { // 请求异常，服务器异常，解析异常
                e.printStackTrace();
                if (e.getClass().equals(SocketTimeoutException.class)) {
                    dataCallback.onDataNotAvailable(SERVER_TIME_OUT, null);
                } else {
                    dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                }
            }

            @Override
            public void onError(Request request, Exception e, int code) {
                e.printStackTrace();
                if (code >= 400 && code < 500) {
                    dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                } else if (code >= 500) {
                    dataCallback.onDataNotAvailable(GlobalConstant.SERVER_ERROR, null);
                } else {
                    dataCallback.onDataNotAvailable(GlobalConstant.JSON_ERROR, null);
                }
            }

            @Override
            public void onResponse(String response) {
//                LogUtils.i("urll===" + url + ",,,,onResponse" + response);
                dataCallback.onDataLoaded(response);
            }

        });
        if (params != null) {
//            LogUtils.i("head   x-auth-token===" + token);
            String strParams = "";
            for (String key : params.keySet()) {
                strParams = strParams + key + "=" + params.get(key) + "&";
            }
//            LogUtils.i("传参==" + url + "?" + strParams);
        }
    }

    /**
     * 无参数的post请求
     *
     * @param url
     * @param dataCallback
     */
    @Override
    public void doStringPost(final String url, final DataCallback dataCallback) {
        String token = "";
        OkhttpUtils.post().url(url).addHeader("x-auth-token", token).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
                if (e.getClass().equals(SocketTimeoutException.class)) {
                    dataCallback.onDataNotAvailable(SERVER_TIME_OUT, null);
                } else {
                    dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                }
            }

            @Override
            public void onError(Request request, Exception e, int code) {
                e.printStackTrace();
                if (code >= 400 && code < 500) {
                    dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                } else if (code >= 500) {
                    dataCallback.onDataNotAvailable(GlobalConstant.SERVER_ERROR, null);
                } else {
                    dataCallback.onDataNotAvailable(GlobalConstant.JSON_ERROR, null);
                }
            }

            @Override
            public void onResponse(String response) {
//                LogUtils.i("urll===" + url + ",,,,onResponse" + response);
                dataCallback.onDataLoaded(response);
            }

        });
//        LogUtils.i("传参==" + url);
    }

    /**
     * get请求
     *
     * @param params
     * @param dataCallback
     */
    @Override
    public void doStringGet(String params, final DataCallback dataCallback) {
        String token = "";
        OkhttpUtils.get().url(params).addHeader("x-auth-token", token).build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                if (e.getClass().equals(SocketTimeoutException.class)) {
                    dataCallback.onDataNotAvailable(SERVER_TIME_OUT, null);
                } else {
                    dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                }
            }

            @Override
            public void onError(Request request, Exception e, int code) {
                e.printStackTrace();
                if (code >= 400 && code < 500) {
                    dataCallback.onDataNotAvailable(OKHTTP_ERROR, null);
                } else if (code >= 500) {
                    dataCallback.onDataNotAvailable(GlobalConstant.SERVER_ERROR, null);
                } else {
                    dataCallback.onDataNotAvailable(GlobalConstant.JSON_ERROR, null);
                }
            }

            @Override
            public void onResponse(String response) {
//                LogUtils.i("onResponse：" + response.toString());
                dataCallback.onDataLoaded(response);
            }

        });
    }

    /**
     * 下载
     */
    @Override
    public void doDownload(String url, final DataCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onDataNotAvailable(OKHTTP_ERROR, null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onDataLoaded(response);
            }
        });
    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     */
    @Override
    public void doUploadFile(String url, File file, final DataCallback dataCallback) {
        String token = "";
//        if (MyApplication.getApp().getCurrentUser() != null)
//            token = MyApplication.getApp().getCurrentUser().getToken() == null ? "" : MyApplication.getApp().getCurrentUser().getToken();
//        LogUtils.i("传参 url==" + url);
//        LogUtils.i("传参 x-auth-token==" + token);
        RequestParams params = new RequestParams(url);
        params.addHeader("x-auth-token", token);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("file", file));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setConnectTimeout(60000);
        params.setReadTimeout(60000);
        params.setRequestBody(body);
        x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }

            @Override
            public void onSuccess(String result) {
//                LogUtils.i("result==" + result);
                dataCallback.onDataLoaded(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dataCallback.onDataNotAvailable(OKHTTP_ERROR, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dataCallback.onDataNotAvailable(OKHTTP_ERROR, cex.toString());
            }

            @Override
            public void onFinished() {
            }
        });
    }

}
