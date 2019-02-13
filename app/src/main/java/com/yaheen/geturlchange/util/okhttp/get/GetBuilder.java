package com.yaheen.geturlchange.util.okhttp.get;

import com.yaheen.geturlchange.util.okhttp.RequestBuilder;
import com.yaheen.geturlchange.util.okhttp.RequestCall;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/11/13.
 */

public class GetBuilder extends RequestBuilder {
    @Override
    public RequestCall build() {
        return new GetRequest(url, params, headers).build();
    }

    private String appendParams(String url, Map<String, String> params) {
        //TODO
        return null;
    }

    @Override
    public GetBuilder url(String url) {
        this.url = url;
        return this;
    }


    @Override
    public GetBuilder addParams(String key, String val) {
        //TODO
        return this;
    }

    @Override
    public GetBuilder addHeader(String key, String value) {
        if (this.headers == null) headers = new HashMap<>();
        headers.put(key, value);
        return this;
    }

    @Override
    public RequestBuilder addParams(Map<String, String> map) {
        return this;
    }
}
