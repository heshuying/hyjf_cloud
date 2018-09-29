/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;


/**
 * @author wenxin
 * @version IncreaseInterestRepayInfoListResponse.java, v0.1 2018年8月30日
 */
public class IncreaseInterestRepayInfoListResponse extends Response<AdminIncreaseInterestRepayCustomizeVO>{
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    /*-----add by LSY START------*/
    /** 应还本金合计 */
    private String sumRepayCapital;
    /** 应还加息收益合计 */
    private String sumLoanInterest;
    /** 应还加息收益合计 */
    private String sumRepayInterest;
    /*-----add by LSY END------*/

    public String getSumRepayCapital() {
        return sumRepayCapital;
    }

    public void setSumRepayCapital(String sumRepayCapital) {
        this.sumRepayCapital = sumRepayCapital;
    }

    public String getSumLoanInterest() {
        return sumLoanInterest;
    }

    public void setSumLoanInterest(String sumLoanInterest) {
        this.sumLoanInterest = sumLoanInterest;
    }

    public String getSumRepayInterest() {
        return sumRepayInterest;
    }

    public void setSumRepayInterest(String sumRepayInterest) {
        this.sumRepayInterest = sumRepayInterest;
    }
}
