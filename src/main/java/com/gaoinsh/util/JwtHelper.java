package com.gaoinsh.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

/**
 * @author xiang.gao
 * @version $Id: JwtHelper.java, v 0.1 2018-05-31 15:11 xiang.gao Exp $$
 */
public class JwtHelper {

    private static final String SECRET = "5WdMo0nTTZwY&9cM4k@XcmHcVnxqJBIym4nAo4TjGExINBVTVtX@Epds%j^hk79i";

    public static DecodedJWT verify(String token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer("xiang.gao").build();

        return verifier.verify(token);
    }


    public static String sign(Map<String, String> claims) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTCreator.Builder builder = JWT.create().withIssuer("xiang.gao");
        if (claims != null) {
            for (Map.Entry<String, String> entry : claims.entrySet()) {
                builder.withClaim(entry.getKey(), entry.getValue());
            }
        }
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 30);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(algorithm);
    }
}