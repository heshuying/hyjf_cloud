/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.resquest.trade.DebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.trade.dao.model.customize.UserHjhInvistListCustomize;
import com.hyjf.am.vo.trade.borrow.ProjectUndertakeListVO;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;

import java.util.List;
import java.util.Map;

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
	 * @param assignOrderId
	 * @return
	 */
    HjhDebtCreditTenderVO getHjhDebtCreditTenderByAssignOrderId(String assignOrderId);

    /**
     *  查询汇计划的出借记录
     * @date 2018/8/1 14:00
     */
    List<UserHjhInvistListCustomize> getUserHjhInvestList(Map<String,Object> params);

    /**
     * 查询汇计划的出借记录数目
     * @author zhangyk
     * @date 2018/11/22 16:08
     */
    int getUserHjhInvestCount(Map<String,Object> params);

    /**
     * 根据borrowNid和creditStatus查询债转列表
     * @author zhangyk
     * @date 2018/8/8 9:54
     */
    List<HjhDebtCredit> selectHjhDebtCreditListByBorrowNidAndStatus(DebtCreditRequest request);

    /**
     * 查询承接记录数
     * @author zhangyk
     * @date 2018/8/9 11:08
     */
    int  countCreditTenderByBorrowNidAndUserId(Map<String,Object> params);

    /**
     * 查询承接中的总额
     * @author zhangyk
     * @date 2018/8/9 11:55
     */
    String sumUnderTakeAmountByBorrowNid(String borrowNid);

    /**
     * 承接总的列表
     * @author zhangyk
     * @date 2018/8/9 14:04
     */
    List<ProjectUndertakeListVO> selectProjectUndertakeList(Map<String,Object> params);

    /**
     * 根据债转编号查询债转信息(从写库)
     * @author liubin
     * @date 2019/1/31 11:04
     */
    HjhDebtCredit doSelectHjhDebtCreditByCreditNid(String creditNid);

    List<HjhDebtCredit> getHjhDebtCreditListByCreditNid(String creditNid);

    List<HjhDebtCredit> getHjhDebtCreditListByBorrowNid(String borrowNid);
    /**
     * 根据原投资订单号查找转让信息
     * @param sellOrderId
     * @return
     * add by nxl
     */
    List<HjhDebtCredit> selectCreditBySellOrderId(String sellOrderId);
}
