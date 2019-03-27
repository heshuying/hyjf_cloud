/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.finance.merchant.transfer.TransferCustomizeBean;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.response.admin.UserTransferResponse;
import com.hyjf.am.response.trade.account.MerchantTransferResponse;
import com.hyjf.am.resquest.admin.MerchantTransferListRequest;
import com.hyjf.am.resquest.admin.TransferListRequest;

/**
 * @author zhangqingqing
 * @version TransferService, v0.1 2018/7/6 17:58
 */
public interface TransferService extends BaseAdminService{

    void checkTransfer(String outUserName);

    String searchBalance(String outUserName);

    /**
     * 插入订单
     * @param form
     * @return
     */
    boolean insertTransfer(MerchantTransferListRequest form);

    /**
     * 获取账户信息
     * @param status
     * @return
     */
    MerchantAccountResponse selectMerchantAccountList(Integer status);

    /**
     * 获取转账列表
     * @param form
     * @return
     */
    MerchantTransferResponse selectMerchantTransfer(MerchantTransferListRequest form);

    /**
     * 校验用户转账的结果
     * @param outAccountId
     * @param transferAmount
     * @param ret
     */
    void checkMerchantTransfer(String outAccountId, String transferAmount, JSONObject ret);

    /**
     * 校验
     * @param form
     */
    JSONObject checkMerchantTransferParam(MerchantTransferListRequest form);

    /**
     * 修改转账列表
     * @param orderId
     * @param status
     * @param message
     * @return
     */
    int updateMerchantTransfer(String orderId, int status,String message);
}
