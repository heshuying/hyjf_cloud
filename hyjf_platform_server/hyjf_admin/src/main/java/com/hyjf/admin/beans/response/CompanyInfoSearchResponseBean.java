/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.user.CompanyInfoVO;

/**
 * @author Administrator
 * @version CompanyInfoSearchVO, v0.1 2018/7/20 17:14
 */
public class CompanyInfoSearchResponseBean {
    //企业信息
    private CompanyInfoVO companyInfoVO;
    //返回信息
    private String returnMsg;
    //返回状态
    private String returnCode;

    public CompanyInfoVO getCompanyInfoVO() {
        return companyInfoVO;
    }

    public void setCompanyInfoVO(CompanyInfoVO companyInfoVO) {
        this.companyInfoVO = companyInfoVO;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
}
