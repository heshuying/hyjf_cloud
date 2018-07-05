/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionClient, v0.1 2018/7/3 14:59
 */
public interface BorrowRegistExceptionClient {
    /**
     * 获取项目类型list,用于筛选条件展示
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BorrowProjectTypeVO> selectBorrowProjectList();

    /**
     * 获取还款方式list,用于筛选条件展示
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BorrowStyleVO> selectBorrowStyleList();

    /**
     * 获取标的列表count,用于前端分页显示条数
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的备案异常列表
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    List <BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 根据borrowNid查询出来异常标
     * @auth sunpeikai
     * @param borrowNid 借款编号
     * @return
     */
    BorrowVO searchBorrowByBorrowNid(String borrowNid);

    /**
     * 利用borrowNid查询出来的异常标的借款人userId查询银行账户
     * @auth sunpeikai
     * @param
     * @return
     */
    BankOpenAccountVO searchBankOpenAccount(Integer userId);

    /**
     * 用loginUserId去am-config查询登录的用户信息
     * @auth sunpeikai
     * @param loginUserId 登录用户id
     * @return
     */
    AdminSystemVO getUserInfoById(Integer loginUserId);

    /**
     * 根据受托支付userId查询stAccountId
     * @auth sunpeikai
     * @param entrustedUserId 受托支付userId
     * @return stAccountId
     */
    String getStAccountIdByEntrustedUserId(Integer entrustedUserId);

    /**
     * 更新标
     * @auth sunpeikai
     * @param borrowVO 标信息
     * @param type 1更新标的备案 2更新受托支付标的备案
     * @return
     */
    boolean updateBorrowRegist(BorrowVO borrowVO, Integer type);

    /**
     * 备案成功看标的是否关联计划，如果关联则更新对应资产表
     * @auth sunpeikai
     * @param borrowVO 标信息
     * @return
     */
    boolean updateBorrowAsset(BorrowVO borrowVO,Integer status);
}
