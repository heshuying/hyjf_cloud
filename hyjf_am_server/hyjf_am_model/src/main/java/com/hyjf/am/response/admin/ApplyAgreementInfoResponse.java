package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.ApplyAgreementInfoVO;

import java.util.List;

/**
 * @version ApplyAgreementInfoResponse, v0.1 2018/8/23 15:36
 * @Author: Zha Daojian
 */
public class ApplyAgreementInfoResponse extends Response<ApplyAgreementInfoVO> {
    //子账号类型
    private List<ApplyAgreementInfoVO> applyAgreementInfoList;
    private int recordTotal;

    public List<ApplyAgreementInfoVO> getApplyAgreementInfoList() {
        return applyAgreementInfoList;
    }

    public void setApplyAgreementInfoList(List<ApplyAgreementInfoVO> applyAgreementInfoList) {
        this.applyAgreementInfoList = applyAgreementInfoList;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
