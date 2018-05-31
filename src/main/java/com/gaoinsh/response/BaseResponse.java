package com.gaoinsh.response;

/**
 * @author xiang.gao
 * @version $Id: BaseResponse.java, v 0.1 2018-05-31 15:17 xiang.gao Exp $$
 */
public class BaseResponse {
    private String errorMsg;
    private int result;
    private String token;


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}