package com.yaheen.geturlchange.response;

import com.yaheen.geturlchange.bean.AccountBean;
import com.yaheen.geturlchange.bean.LTCBalance;

import java.io.Serializable;
import java.util.ArrayList;

public class LTCResponse implements Serializable {

    private static final long serialVersionUID = -9153142522435146903L;

    public String status;

    public LTCBalance data;
}
