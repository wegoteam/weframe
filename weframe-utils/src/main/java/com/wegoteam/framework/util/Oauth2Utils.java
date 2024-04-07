package com.wegoteam.framework.util;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import java.util.Map;


/**
 * @description:
 * @author: XUCHANG
 */
public class Oauth2Utils {

    public static Map<String,String> getJwtClaimsFromHeader(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        //取出头信息
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || authorization.indexOf("Bearer") < 0) {
            return null;
        }
        //从Bearer 后边开始取出token
        String token = authorization.substring(7);
        Map<String,String> map = null;
        try {
            //解析jwt
            Jwt decode = JwtHelper.decode(token);
            //得到 jwt中的用户信息
            String claims = decode.getClaims();
            //将jwt转为Map
            map = JSON.parseObject(claims, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
