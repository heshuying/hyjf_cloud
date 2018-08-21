package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
public class AdminAccountBalanceMonitoringResponse extends Response<MerchantAccountVO>  {
    //子账号类型
    private List<ParamNameVO> paramNameList;
    private int recordTotal;

    public int getRecordTotal() {
            return recordTotal;
            }

    public void setRecordTotal(int recordTotal) {
            this.recordTotal = recordTotal;
            }

    public List<ParamNameVO> getParamNameList() {
        return paramNameList;
    }

    public void setParamNameList(List<ParamNameVO> paramNameList) {
        this.paramNameList = paramNameList;
    }
}
