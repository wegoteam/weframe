package com.wegoteam.framework.core.context;

import java.util.Date;

/**
 * @description:
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
public final class CommonCallContext implements java.io.Serializable {
    private static final long serialVersionUID = -6192221637110412715L;

    /**
     * 应用编码
     */
    private String applicationId;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * logId
     */
    private String logId;

    /**
     * 调用时间
     */
    private Date callTime;

    /**
     * 用户信息
     */
    private UserContext user;

    /**
     * 企业信息
     */
    private CompanyContext company;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public UserContext getUser() {
        return user;
    }

    public void setUser(UserContext user) {
        this.user = user;
    }

    public CompanyContext getCompany() {
        return company;
    }

    public void setCompany(CompanyContext company) {
        this.company = company;
    }
}
