/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.resquest.admin.MerchantTransferListRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.dao.model.auto.MerchantTransfer;
import com.hyjf.am.trade.service.BaseService;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountService, v0.1 2018/7/5 13:43
 */
public interface MerchantAccountService extends BaseService {

    /**
     * 获取商户子账户列表
     * @return
     * @param request
     * @param offset
     * @param limit
     */
    List<MerchantAccount> selectRecordList(MerchantAccountListRequest request, int offset, int limit);

    Integer updateByPrimaryKeySelective(MerchantAccount merchantAccount);

    int countByExample();

    /**
     * 查询平台账户记录总数
     * @param request
     * @return
     */
    Integer getMerchantAccountListCountByPage(AdminMerchantAccountRequest request);

    /**
     * 分页查询平台账户记录
     * @param request
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<MerchantAccount> getMerchantAccountListByPage(AdminMerchantAccountRequest request, int limitStart, int limitEnd);

    /**
     * 根据id查询平台账户记录
     * @param id
     * @return
     */
    public MerchantAccount selectAccountConfigInfo(Integer id);
    /**
     * 插入平台账户设置操作
     * @param adminRequest
     * @return
     */
    int saveAccountConfig(AdminMerchantAccountRequest adminRequest);
    /**
     * 修改平台账户设置操作
     * @param adminRequest
     * @return
     */
    int updateAccountConfig(AdminMerchantAccountRequest adminRequest);

    /**
     *
     * 根据子账户名称检索
     * @return
     */
    int countAccountListInfoBySubAccountName(HashMap<String,String> map);
    /**
     *
     * 根据子账户代号检索
     * @return
     */
    int countAccountListInfoBySubAccountCode(HashMap<String,String> map);

    /**
     * 查询账户信息
     * @param status
     * @return
     */
    List<MerchantAccount> selectMerchantAccountList(Integer status);

    /**
     * 转账记录总数
     * @param form
     * @return
     */
    int queryRecordTotal(MerchantTransferListRequest form);

    /**
     * 账户转账列表
     * @param form
     * @param offset
     * @param limit
     * @return
     */
    List<MerchantTransfer> selectMerchantTransfer(MerchantTransferListRequest form, int offset, int limit);

    /**
     * 根据id获取转账信息
     * @param id
     * @return
     */
    MerchantAccount selectMerchantAccountById(Integer id);

    /**
     * 插入转账信息
     * @param form
     * @return
     */
    boolean insertTransfer(MerchantTransferListRequest form);

    /**
     * 更新转账信息
     * @param orderId
     * @param status
     * @param message
     */
    int updateMerchantTransfer(String orderId, Integer status, String message);
}
