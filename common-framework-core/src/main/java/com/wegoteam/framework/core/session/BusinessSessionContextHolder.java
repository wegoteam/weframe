package com.wegoteam.framework.core.session;

/**
 * @description:
 * @author: XUCHANG
 * @time: 2019/12/4 10:51
 */
public final class BusinessSessionContextHolder {

    /**
     * 线程本地变量缓存session
     */
    private ThreadLocal<BusinessSessionObject> sessionObject = new ThreadLocal<>();

    /**
     * 获取session信息
     *
     * @return session会话信息
     */
    public BusinessSessionObject getSession() {
        return sessionObject.get();
    }

    /**
     * 设置session信息
     *
     * @param businessSessionObject session会话信息
     */
    public void putSession(BusinessSessionObject businessSessionObject) {
        this.sessionObject.set(businessSessionObject);
    }

    /**
     * 清空session信息
     */
    public void clearSession() {
        this.sessionObject.remove();
    }
}
