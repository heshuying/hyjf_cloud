/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditClient, v0.1 2018/6/26 13:39
 */
public interface HjhDebtCreditClient {

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



}
