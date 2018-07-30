/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditService, v0.1 2018/6/27 14:45
 */
public interface HjhDebtCreditService {

    List<HjhDebtCredit> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId);

    List<HjhDebtCredit> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId,String borrowNid);

    List<HjhDebtCreditTender> selectHjhCreditTenderListByAssignOrderId(String assignOrderId);

    List<HjhDebtCredit> getHjhDebtCreditList(HjhDebtCreditRequest request);

    /**
     *  根据债转编号查询债转信息
     * @author zhangyk
     * @date 2018/6/30 11:22
     */
    AppCreditDetailCustomizeVO selectCreditDetailByCreditNid(String creditNid);

    /**
     * 根据债转编号查询债转信息
     * @param creditNid
     * @author liubin
     * @return
     */
    HjhDebtCredit selectHjhDebtCreditByCreditNid(String creditNid);

    /**
     * 根据PK更新hjhDebtCredit
     * @param hjhDebtCredit
     * @author liubin
     * @return
     */
    int updateHjhDebtCreditByPK(HjhDebtCredit hjhDebtCredit);
    
	/**
	 * 获取债转承接信息
	 * by libin
	 * @param nid
	 * @return
	 */
    HjhDebtCreditTenderVO getHjhDebtCreditTenderByPrimaryKey(Integer nid);
    
	/**
	 * 获取债转承接信息 by AssignOrderId
	 * by libin
	 * @param nid
	 * @return
	 */
    HjhDebtCreditTenderVO getHjhDebtCreditTenderByAssignOrderId(String assignOrderId);
}
