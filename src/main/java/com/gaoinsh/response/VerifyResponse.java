/**
 * ymm56.com Inc.
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
package com.gaoinsh.response;

/**
 * @author xiang.gao
 * @version $Id: VerifyResponse.java, v 0.1 2018-05-31 17:43 xiang.gao Exp $$
 */
public class VerifyResponse extends BaseResponse {
    private String header;
    private String payLoad;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }
}