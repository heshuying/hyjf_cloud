package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.admin.coupon.ParamName;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
public class AdminAccountBalanceMonitoringResponse extends Response<MerchantAccountVO>  {
    //子账号类型
    private List<ParamName> paramNameList;
    private int recordTotal;

    public int getRecordTotal() {
            return recordTotal;
            }

    public void setRecordTotal(int recordTotal) {
            this.recordTotal = recordTotal;
            }

    public List<ParamName> getParamNameList() {
        return paramNameList;
    }

    public void setParamNameList(List<ParamName> paramNameList) {
        this.paramNameList = paramNameList;
    }
}
