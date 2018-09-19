/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.ChinapnrBeanVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author zhangqingqing
 * @version ChinaPnrWithdrawRequest, v0.1 2018/9/8 11:30
 */
public class ChinaPnrWithdrawRequest extends Request{

    public ChinapnrBeanVO chinapnrBean;

    public UserVO user;

    public UserInfoVO userInfo;

    public Integer userId;

    public String ip;

    public ChinapnrBeanVO getChinapnrBean() {
        return chinapnrBean;
    }

    public void setChinapnrBean(ChinapnrBeanVO chinapnrBean) {
        this.chinapnrBean = chinapnrBean;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
    }
}
