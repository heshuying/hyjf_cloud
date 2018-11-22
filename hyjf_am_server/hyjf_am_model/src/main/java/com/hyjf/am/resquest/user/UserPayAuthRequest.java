/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;

/**
 * @author NXL
 * @version UserMemberParamVO, v0.1 2018/6/19 17:41
 *          会员中心->服务费授权(请求参数）
 */
public class UserPayAuthRequest extends BasePage {
    public String authTimeStart;
    public String authTimeEnd;
    public String userName;
    public String authType;
    public String bankid;
    public String signTimeStart;
    public String signTimeEnd;
    //默认为true ,获取全部数据，为false时，获取部分数据
    public boolean limitFlg = false;

    public String getAuthTimeStart() {
        return authTimeStart;
    }

    public void setAuthTimeStart(String authTimeStart) {
        this.authTimeStart = authTimeStart;
    }

    public String getAuthTimeEnd() {
        return authTimeEnd;
    }

    public void setAuthTimeEnd(String authTimeEnd) {
        this.authTimeEnd = authTimeEnd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getSignTimeStart() {
        return signTimeStart;
    }

    public void setSignTimeStart(String signTimeStart) {
        this.signTimeStart = signTimeStart;
    }

    public String getSignTimeEnd() {
        return signTimeEnd;
    }

    public void setSignTimeEnd(String signTimeEnd) {
        this.signTimeEnd = signTimeEnd;
    }

    public boolean isLimitFlg() {
        return limitFlg;
    }

    public void setLimitFlg(boolean limitFlg) {
        this.limitFlg = limitFlg;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }
}
