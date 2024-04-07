package com.wegoteam.framework.core.session;

import com.wegoteam.framework.core.context.CompanyContext;
import com.wegoteam.framework.core.context.UserContext;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: XUCHANG
 * @time: 2019/12/4 10:51
 */
public final class BusinessSessionObject implements Serializable {
    private static final long serialVersionUID = -5960069764782340892L;

    /**
     * 应用编号
     */
    private String applicationId;

    /**
     * 请求时间
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

    /**
     * 构造函数
     *
     * @param values 会话数据
     */
    public BusinessSessionObject(String[] values) {

        Assert.notNull(values, "values is not null");
        if (values.length != 4) {
            throw new IllegalArgumentException("values is not vaild");
        }

        this.user = new UserContext();
        this.company = new CompanyContext();

        this.user.setCaller(values[0]);
        this.user.setCallerName(values[2]);
        this.company.setCompanyId(values[1]);

        this.setApplicationId(values[3]);
        this.setCallTime(new Date());
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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
