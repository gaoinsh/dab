package com.gaoinsh.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gaoinsh.request.VerifyRequest;
import com.gaoinsh.response.BaseResponse;
import com.gaoinsh.response.VerifyResponse;
import com.gaoinsh.util.JwtHelper;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


/**
 * @author xiang.gao
 * @version $Id: UserController.java, v 0.1 2018-05-31 15:13 xiang.gao Exp $$
 */
@RestController
public class UserController {

    @RequestMapping(value = "/login/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse login(@PathVariable("userName") String userName) {

        BaseResponse resp = new BaseResponse();
        resp.setResult(1);
        HashMap<String, String> claims = new HashMap<>();
        System.out.println("userName" + userName);
        claims.put("userName", userName);
        String token = null;
        try {
            token = JwtHelper.sign(claims);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        resp.setToken(token);
        return resp;
    }


    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public VerifyResponse verify(@RequestBody VerifyRequest req) {
        VerifyResponse resp = new VerifyResponse();
        DecodedJWT jwt = null;
        try {
            jwt = JwtHelper.verify(req.getToken());
            String header = new String(Base64.decode(jwt.getHeader()));
            resp.setHeader(header);
            System.out.println("header-> " + header);
            String payload = new String(Base64.decode(jwt.getPayload()));
            resp.setPayLoad(payload);
            System.out.println("payload-> " + payload);
            System.out.println("signature-> " + jwt.getSignature());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (TokenExpiredException e) {
            System.out.println("token超时");
        }
        resp.setResult(jwt == null ? 1 : 0);
        resp.setToken(req.getToken());

        return resp;

    }

}