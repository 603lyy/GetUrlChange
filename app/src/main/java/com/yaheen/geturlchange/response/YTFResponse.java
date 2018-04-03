package com.yaheen.geturlchange.response;

import com.yaheen.geturlchange.bean.YTFBean;

import java.io.Serializable;
import java.util.ArrayList;

public class YTFResponse implements Serializable {

    private static final long serialVersionUID = 1107151455075051059L;

    public String status;

    public String message;

    public ArrayList<YTFBean> result;
}
