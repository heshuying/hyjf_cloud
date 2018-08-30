/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminMerchantAccountSumCustomizeVO;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountResponse, v0.1 2018/7/5 13:52
 */
public class MerchantAccountResponse extends Response<MerchantAccountVO> {
    //子账号类型
    private List<ParamNameVO> paramNameList;
    private int recordTotal;

    private AdminMerchantAccountSumCustomizeVO accoutSum;

    public List<ParamNameVO> getParamNameList() {
        return paramNameList;
    }

    public void setParamNameList(List<ParamNameVO> paramNameList) {
        this.paramNameList = paramNameList;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public AdminMerchantAccountSumCustomizeVO getAccoutSum() {
        return accoutSum;
    }

    public void setAccoutSum(AdminMerchantAccountSumCustomizeVO accoutSum) {
        this.accoutSum = accoutSum;
    }
}
