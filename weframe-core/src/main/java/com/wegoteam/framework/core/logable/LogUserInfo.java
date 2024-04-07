package com.wegoteam.framework.core.logable;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @description: 用户信息
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
public class LogUserInfo {
    String userId;
    String userType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static LogUserInfo getUserInfo() {
        String userId = "NONE";
        String userType = "NONE";
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request != null) {
                userId = request.getHeader(Constants.HeadKey.USERID.code) == null ? "NONE" : request.getHeader(Constants.HeadKey.USERID.code);
                userType = request.getHeader(Constants.HeadKey.USERTYPE.code) == null ? "NONE" : request.getHeader(Constants.HeadKey.USERTYPE.code);
            }
        }
        LogUserInfo logUserInfo = new LogUserInfo();
        logUserInfo.setUserId(userId);
        logUserInfo.setUserType(userType);
        return logUserInfo;
    }

}
