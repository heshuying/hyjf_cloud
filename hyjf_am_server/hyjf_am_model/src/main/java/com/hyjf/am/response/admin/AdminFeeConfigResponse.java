package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/11.
 */
public class AdminFeeConfigResponse  extends Response<FeeConfigVO> {
    //银行列表
    private List<BankConfigVO> bankConfig;

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public List<BankConfigVO> getBankConfig() {
        return bankConfig;
    }

    public void setBankConfig(List<BankConfigVO> bankConfig) {
        this.bankConfig = bankConfig;
    }
}
