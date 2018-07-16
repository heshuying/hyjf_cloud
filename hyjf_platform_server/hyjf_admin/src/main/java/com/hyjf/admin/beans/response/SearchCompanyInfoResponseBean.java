/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.user.CompanyInfoVO;
import com.hyjf.am.vo.user.UserBankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author nxl
 * @version InitCompanyInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class SearchCompanyInfoResponseBean {

    //公司信息
    private CompanyInfoVO company;
    private int isOpenAccount;

    public CompanyInfoVO getCompany() {
        return company;
    }

    public void setCompany(CompanyInfoVO company) {
        this.company = company;
    }

    public int getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(int isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }
}
