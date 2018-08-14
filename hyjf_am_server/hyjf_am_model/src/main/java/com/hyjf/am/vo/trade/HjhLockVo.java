package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/8/8
 * @Description: 汇计划提成计算需要用到的类
 */
public class HjhLockVo extends BaseVO implements Serializable{

    private String accedeOrderId;

    private UserInfoVO inverestUserInfo;

    private UserInfoVO commissioUserInfoVO;

    private BankOpenAccountVO bankOpenAccountVO;

    private List<UserInfoCustomizeVO> userInfoCustomizeVOS;

    public List<UserInfoCustomizeVO> getUserInfoCustomizeVOS() {
        return userInfoCustomizeVOS;
    }

    public void setUserInfoCustomizeVOS(List<UserInfoCustomizeVO> userInfoCustomizeVOS) {
        this.userInfoCustomizeVOS = userInfoCustomizeVOS;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public UserInfoVO getInverestUserInfo() {
        return inverestUserInfo;
    }

    public void setInverestUserInfo(UserInfoVO inverestUserInfo) {
        this.inverestUserInfo = inverestUserInfo;
    }

    public UserInfoVO getCommissioUserInfoVO() {
        return commissioUserInfoVO;
    }

    public void setCommissioUserInfoVO(UserInfoVO commissioUserInfoVO) {
        this.commissioUserInfoVO = commissioUserInfoVO;
    }

    public BankOpenAccountVO getBankOpenAccountVO() {
        return bankOpenAccountVO;
    }

    public void setBankOpenAccountVO(BankOpenAccountVO bankOpenAccountVO) {
        this.bankOpenAccountVO = bankOpenAccountVO;
    }
}
