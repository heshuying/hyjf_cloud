/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.CompanyInfoCompanyInfoVO;
import com.hyjf.admin.beans.vo.UserBankOpenAccountCustomizeVO;
import com.hyjf.admin.beans.vo.UserCustomizeShowVO;


/**
 * @author nxl
 * @version InitCompanyInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class InitCompanyInfoResponseBean {
    //开户信息
    private UserBankOpenAccountCustomizeVO bankOpenAccount;
    //公司信息
    private CompanyInfoCompanyInfoVO companyInfo;
    //用户信息
    private UserCustomizeShowVO userVO;

    public UserBankOpenAccountCustomizeVO getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(UserBankOpenAccountCustomizeVO bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public CompanyInfoCompanyInfoVO getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfoCompanyInfoVO companyInfo) {
        this.companyInfo = companyInfo;
    }

    public UserCustomizeShowVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserCustomizeShowVO userVO) {
        this.userVO = userVO;
    }
}
