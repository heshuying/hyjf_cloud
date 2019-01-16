/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.math.BigDecimal;

/**
 * @author tyy
 * @version ReturnCashActivityService, v0.1 2018/12/29 15:48
 */
public interface ReturnCashActivityService {

    boolean saveReturnCash(Integer userId, String orderId, Integer productType, BigDecimal investMoney);

    void  updateJoinTime(String borrowNid, Integer nowTime);

}
