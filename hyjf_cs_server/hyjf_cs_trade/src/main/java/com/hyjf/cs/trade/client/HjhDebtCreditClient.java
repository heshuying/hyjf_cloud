/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.HjhDebtCreditResponse;
import com.hyjf.am.response.trade.HjhDebtCreditTenderResponse;
import com.hyjf.am.resquest.trade.DebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.common.validator.Validator;

import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditClient, v0.1 2018/6/26 13:39
 */
public interface HjhDebtCreditClient {
    /**
     *
     * @param accedeOrderId
     * @return
     */
    List<HjhDebtCreditVO> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId);

    /**
     *
     * @param accedeOrderId
     * @param borrowNid
     * @return
     */
    List<HjhDebtCreditVO> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId,String borrowNid);


    /**
     * 汇计划债转协议下载
     * @return
     */
    public List<HjhDebtCreditTenderVO> selectHjhCreditTenderListByAssignOrderId(String assignOrderId);


    /**
     * 获取债转信息
     * @param request1
     * @return
     */
    public List<HjhDebtCreditVO> getHjhDebtCreditList(HjhDebtCreditRequest request1) ;



    /**
     * 根据borrowNid和orderStatus查询债转列表
     * String borrowNid   不可空
     * List<Integer> creditStatus;  可空
     * @author zhangyk
     * @date 2018/6/29 14:15
     */
    List<HjhDebtCreditVO> selectHjhDebtCreditListByBorrowNidAndStatus(DebtCreditRequest request);


    /**
     * 查询债转投资数目
     * @author zhangyk
     * @date 2018/6/29 14:36
     */
    Integer countCreditTenderByBorrowNidAndUserId(Map<String,Object> map);


    /**
     * 根据债转编号查询债转信息
     * @author zhangyk
     * @date 2018/6/30 11:04
     */
    AppCreditDetailCustomizeVO selectHjhCreditByCreditNid(String creditNid);
}
