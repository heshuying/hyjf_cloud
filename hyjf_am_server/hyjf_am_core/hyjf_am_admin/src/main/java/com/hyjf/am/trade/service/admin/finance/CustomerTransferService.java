/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import java.util.List;

import com.hyjf.am.resquest.admin.CustomerTransferListRequest;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.resquest.admin.TransferListRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.UserTransfer;

/**
 * @author: sunpeikai
 * @version: CustomerTransferService, v0.1 2018/7/6 10:15
 */
public interface CustomerTransferService {

    /**
     * 根据筛选条件查询UserTransfer数据总数
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getUserTransferCount(CustomerTransferListRequest request);

    /**
     * 根据筛选条件查询UserTransfer列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<UserTransfer> searchUserTransferList(CustomerTransferListRequest request);

    /**
     * 根据userId查询账户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    List<Account> searchAccountByUserId(Integer userId);
    /**
     * 向ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 发起转账的参数
     * @return
     */
    Boolean insertUserTransfer(CustomerTransferRequest request);

    /**
     * 根据主键id查询userTransfer
     * @auth sunpeikai
     * @param id ht_user_transfer表的主键id
     * @return
     */
    UserTransfer searchUserTransferById(Integer id);

    /**
     * 根据筛选条件查询UserTransfer数据总数
     * @param request
     * @return
     */
    Integer getRecordCount(TransferListRequest request);

    /**
     * 根据筛选条件查询UserTransfer列表
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    List<UserTransfer> selectRecordList(TransferListRequest request, int offset, int limit);
}
