/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.market.dao.model.auto.InviterReturnDetail;
import com.hyjf.am.market.dao.model.auto.PerformanceReturnDetail;
import com.hyjf.am.resquest.market.InviterReturnCashCustomize;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tyy
 * @version ReturnCashActivityService, v0.1 2018/12/29 15:48
 */
public interface ReturnCashActivityService {

    boolean saveReturnCash(Integer userId, String orderId, Integer productType, BigDecimal investMoney,InviterReturnCashCustomize inviterReturnCashCustomize);

    InviterReturnCashCustomize selectReturnCashList(Integer userId);
    List<InviterReturnDetail> selectInviterReturnDetailList(String borrowNid);
    List<PerformanceReturnDetail> selectPerformanceReturnDetailList(String borrowNid);
    void updateJoinTime(Integer nowTime,List<InviterReturnDetail> inviterReturnDetailList,List<PerformanceReturnDetail> performanceReturnDetailList);

}
