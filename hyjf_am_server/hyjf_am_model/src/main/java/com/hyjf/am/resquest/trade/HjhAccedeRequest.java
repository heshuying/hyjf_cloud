package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

/**
 * 汇计划加入标请求参数
 * @author zhangyk
 * @date 2018/6/28 13:39
 */
public class HjhAccedeRequest extends Request {

    private String planNid;
    private String userId;

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
