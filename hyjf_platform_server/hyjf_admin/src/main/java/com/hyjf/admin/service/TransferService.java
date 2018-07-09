/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.controller.finance.transfer.TransferCustomizeBean;
import com.hyjf.am.response.admin.UserTransferResponse;
import com.hyjf.am.resquest.admin.TransferListRequest;

/**
 * @author zhangqingqing
 * @version TransferService, v0.1 2018/7/6 17:58
 */
public interface TransferService extends BaseAdminService{

    /**
     * 根据筛选条件查询userTransfer列表
     * @param form
     * @return
     */
    UserTransferResponse getRecordList(TransferListRequest form);

    void checkTransfer(String outUserName);

    String searchBalance(String outUserName);

    void checkTransferParam(TransferCustomizeBean form);

    /**
     * 插入订单
     * @param form
     * @return
     */
    boolean insertTransfer(TransferCustomizeBean form);
}
