/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowRegistExceptionVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionClient, v0.1 2018/7/3 14:59
 */
public interface BorrowRegistExceptionClient {
    /**
     * 获取项目类型
     * @return
     */
    List<BorrowProjectTypeVO> selectBorrowProjectList();

    /**
     * 获取还款方式
     * @return
     */
    List<BorrowStyleVO> selectBorrowStyleList();

    /**
     * 获取标的备案列表count
     * @param borrowRegistListRequest
     * @return
     */
    Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的备案列表
     * @param borrowRegistListRequest
     * @return
     */
    List <BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);
    /**
     * 根据borrowNid查询出来异常标
     * @auth 孙沛凯
     * @param borrowNid 借款编号
     * @return
     */
    BorrowVO searchBorrowByBorrowNid(String borrowNid);
    /**
     * 利用borrowNid查询出来的异常标的借款人查询银行账户
     * @auth sunpeikai
     * @param
     * @return
     */
    BankOpenAccountVO searchBankOpenAccount(Integer userId);
    /**
     *
     * @auth sunpeikai
     * @param
     * @return
     */
    AdminSystemVO getUserInfoById(Integer loginUserId);

    String getStAccountIdByEntrustedUserId(Integer entrustedUserId);

    boolean updateBorrowRegist(BorrowVO borrowVO, Integer type);
    /**
     * 备案成功看标的是否关联计划，如果关联则更新对应资产表
     * @param borrowVO
     * @return
     */
    boolean updateBorrowAsset(BorrowVO borrowVO,Integer status);
}
