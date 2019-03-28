/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageService, v0.1 2018/6/29 11:54
 */
public interface BankAccountManageService extends BaseService {

    /**
     * 账户管理页面查询列表
     *
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 资金明细（列表）
     *
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomizeVO> queryAccountDetails(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 查询用户是否开户
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccount(Integer userId);

    /**
     * 更新用户账户信息
     *
     * @param userId
     * @param balance
     * @param frost
     * @return
     */
    Integer updateAccount(Integer userId, BigDecimal balance, BigDecimal frost);

    /**
     * 线下充值对账
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @param ip
     * @return
     */
    String updateAccountCheck(Integer userId, String startTime, String endTime, String ip);

    /**
     * 银行账户管理页面数据条数
     *
     * @param request
     * @return
     */
    Integer selectAccountInfoCount(BankAccountManageRequest request);

    /**
     * 获取部门列表
     *
     * @return
     */
    List<OADepartmentCustomizeVO> queryDepartmentInfo();

    /**
     * 获取部门列表
     *
     * @param list
     * @return
     */
    JSONArray getCrmDepartmentList(String[] list);

    /**
     * 根据用户id获取用户账户信息
     *
     * @param userId
     * @return
     */
    AccountVO getAccountByUserId(Integer userId);

    /**
     * 部门查询条件处理
     *
     * @param combotreeListSrch
     * @return
     */
    String[] getDeptId(String[] combotreeListSrch);
}
