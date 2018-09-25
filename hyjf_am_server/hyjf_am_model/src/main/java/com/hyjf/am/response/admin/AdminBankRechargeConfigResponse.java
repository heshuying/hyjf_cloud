package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.BankRechargeLimitConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/11.
 */
public class AdminBankRechargeConfigResponse extends Response<BankRechargeLimitConfigVO> {
    private int recordTotal;
    //    获取银行列表（快捷卡）
    private List<BankConfigVO> bankConfigs;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public List<BankConfigVO> getBankConfigs() {
        return bankConfigs;
    }

    public void setBankConfigs(List<BankConfigVO> bankConfigs) {
        this.bankConfigs = bankConfigs;
    }
}
