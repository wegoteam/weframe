package com.wegoteam.framework.core.context;


import com.wegoteam.framework.core.session.BusinessSessionContextHolder;
import com.wegoteam.framework.core.session.BusinessSessionObject;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
public final class CommonCallContextBuilder {
    /**
     * 构造请求上下文
     *
     * @param sessionContextHolder session会话信息
     * @return 会话信息
     */
    public static CommonCallContext buildContext(BusinessSessionContextHolder sessionContextHolder) {

        BusinessSessionObject session = sessionContextHolder.getSession();
        CommonCallContext context = new CommonCallContext();

        context.setUser(session.getUser());
        context.setCompany(session.getCompany());

        context.setCallTime(session.getCallTime());
        context.setApplicationId(session.getApplicationId());

        return context;
    }

    /**
     * 清除 session会话信息
     * @param sessionContextHolder session会话信息
     */
    public static void cleanContext(BusinessSessionContextHolder sessionContextHolder) {
        sessionContextHolder.clearSession();
    }
}
