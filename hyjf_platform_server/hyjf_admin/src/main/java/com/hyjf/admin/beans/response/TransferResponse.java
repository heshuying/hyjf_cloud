/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.trade.account.MerchantTransferVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version TransferResponse, v0.1 2018/7/6 18:38
 */
public class TransferResponse {
    private List transferStatus;
    private List transferTypes;
    private List<MerchantAccountVO> merchantAccountListOut;
    private List<MerchantAccountVO> merchantAccountListIn;
    private List<MerchantTransferVO> userTransferVOList;
    private int total;

    public List getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(List transferStatus) {
        this.transferStatus = transferStatus;
    }

    public List getTransferTypes() {
        return transferTypes;
    }

    public void setTransferTypes(List transferTypes) {
        this.transferTypes = transferTypes;
    }


    public List<MerchantTransferVO> getUserTransferVOList() {
        return userTransferVOList;
    }

    public void setUserTransferVOList(List<MerchantTransferVO> userTransferVOList) {
        this.userTransferVOList = userTransferVOList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MerchantAccountVO> getMerchantAccountListOut() {
        return merchantAccountListOut;
    }

    public void setMerchantAccountListOut(List<MerchantAccountVO> merchantAccountListOut) {
        this.merchantAccountListOut = merchantAccountListOut;
    }

    public List<MerchantAccountVO> getMerchantAccountListIn() {
        return merchantAccountListIn;
    }

    public void setMerchantAccountListIn(List<MerchantAccountVO> merchantAccountListIn) {
        this.merchantAccountListIn = merchantAccountListIn;
    }
}
