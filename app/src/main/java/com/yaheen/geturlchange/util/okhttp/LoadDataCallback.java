package com.yaheen.geturlchange.util.okhttp;


public interface LoadDataCallback {
    void onDataLoaded(String response, int requstCode);

    void onDataNotAvailable(Integer code, String toastMessage);

}
