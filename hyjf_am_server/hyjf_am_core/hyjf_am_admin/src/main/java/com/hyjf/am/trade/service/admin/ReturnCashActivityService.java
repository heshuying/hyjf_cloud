/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.market.InviterReturnCashCustomize;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author tyy
 * @version ReturnCashActivityService, v0.1 2018/12/29 15:48
 */
public interface ReturnCashActivityService {

    boolean saveReturnCash(Integer userId, String orderId, Integer productType, BigDecimal investMoney,InviterReturnCashCustomize inviterReturnCashCustomize);

    InviterReturnCashCustomize selectReturnCashList(Integer userId);

    void  updateJoinTime(String borrowNid, Integer nowTime);

}
