package com.gaoinsh.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gaoinsh.request.VerifyRequest;
import com.gaoinsh.response.BaseResponse;
import com.gaoinsh.response.VerifyResponse;
import com.gaoinsh.service.UserService;
import com.gaoinsh.util.JwtHelper;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


/**
 * @author xiang.gao
 * @version $Id: UserController.java, v 0.1 2018-05-31 15:13 xiang.gao Exp $$
 */
@Controller
public class UserController {
    protected static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse login(@PathVariable("userName") String userName) {

        BaseResponse resp = new BaseResponse();
        resp.setResult(1);
        HashMap<String, String> claims = new HashMap<>();
        userService.findByName(userName);
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
            logger.info("header-> " + header);
            String payload = new String(Base64.decode(jwt.getPayload()));
            resp.setPayLoad(payload);
            logger.info("payload-> " + payload);
            logger.info("signature-> " + jwt.getSignature());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (TokenExpiredException e) {
            logger.info("token超时");
        }
        resp.setResult(jwt == null ? 0 : 1);
        resp.setToken(req.getToken());

        return resp;

    }

}