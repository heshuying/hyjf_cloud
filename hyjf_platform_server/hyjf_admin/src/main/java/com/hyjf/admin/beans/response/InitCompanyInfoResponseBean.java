/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.user.*;

import java.util.List;

/**
 * @author nxl
 * @version InitCompanyInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class InitCompanyInfoResponseBean {
    //开户信息
    private UserBankOpenAccountVO bankOpenAccount;
    //公司信息
    private CompanyInfoVO companyInfo;
    //用户信息
    private UserVO userVO;

    public UserBankOpenAccountVO getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(UserBankOpenAccountVO bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public CompanyInfoVO getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfoVO companyInfo) {
        this.companyInfo = companyInfo;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }
}
