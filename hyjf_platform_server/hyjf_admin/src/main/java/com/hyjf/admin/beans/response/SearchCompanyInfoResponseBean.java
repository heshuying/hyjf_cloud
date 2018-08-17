/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.CompanyInfoCompanyInfoVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 * @version InitCompanyInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class SearchCompanyInfoResponseBean {

    //公司信息
    @ApiModelProperty(value = "公司信息")
    private CompanyInfoCompanyInfoVO company;

    @ApiModelProperty(value = "是否开户,0未开户,1已开户")
    private int isOpenAccount;

    public CompanyInfoCompanyInfoVO getCompany() {
        return company;
    }

    public void setCompany(CompanyInfoCompanyInfoVO company) {
        this.company = company;
    }

    public int getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(int isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }
}
