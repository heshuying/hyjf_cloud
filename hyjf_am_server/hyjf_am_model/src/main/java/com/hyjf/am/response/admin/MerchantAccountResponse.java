/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.admin.coupon.ParamName;

import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountResponse, v0.1 2018/7/5 13:52
 */
public class MerchantAccountResponse extends Response<MerchantAccountVO> {
    //子账号类型
    private List<ParamName> paramNameList;
    private int recordTotal;

    public List<ParamName> getParamNameList() {
        return paramNameList;
    }

    public void setParamNameList(List<ParamName> paramNameList) {
        this.paramNameList = paramNameList;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
