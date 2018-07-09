/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.UserTransferVO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version TransferResponse, v0.1 2018/7/6 18:38
 */
public class TransferResponse {
    private Map<String, String> transferStatus;
    private Map<String, String> transferTypes;
    private List<UserTransferVO> userTransferVOList;
    private int total;

    public Map<String, String> getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Map<String, String> transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Map<String, String> getTransferTypes() {
        return transferTypes;
    }

    public void setTransferTypes(Map<String, String> transferTypes) {
        this.transferTypes = transferTypes;
    }

    public List<UserTransferVO> getUserTransferVOList() {
        return userTransferVOList;
    }

    public void setUserTransferVOList(List<UserTransferVO> userTransferVOList) {
        this.userTransferVOList = userTransferVOList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
