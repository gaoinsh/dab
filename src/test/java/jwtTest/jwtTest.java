/**
 * ymm56.com Inc.
 * Copyright (c) 2013-2018 All Rights Reserved.
 */
package jwtTest;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.gaoinsh.util.JwtHelper;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author xiang.gao
 * @version $Id: jwtTest.java, v 0.1 2018-05-31 16:51 xiang.gao Exp $$
 */
public class jwtTest {

    @Test
    public void testSign() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", "xiang.gao");
        String sign = null;
        try {
            sign = JwtHelper.sign(map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(sign);
    }

    @Test
    public void testVerify() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ4aWFuZy5nYW8iLCJ1c2VyTmFtZSI6InhpYW5nLmdhbyIsImV4cCI6MTUyNzc1Njc5NH0.zeQc-bTYTbZCBFroH-yokiKDYEKHSOun8kXQDfbNzMw";
        DecodedJWT verify = null;
        try {
            verify = JwtHelper.verify(token);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (verify != null) {
            System.out.println(verify.getHeader() + " , " + verify.getPayload());
        }
    }
}