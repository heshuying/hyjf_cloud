/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.response.StringResponse;
import com.hyjf.am.resquest.admin.ReturnCashRequest;
import com.hyjf.am.resquest.trade.InvitePrizeConfVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author DongZeShan
 * @version AmMarketClient.java, v0.1 2018年9月21日 下午2:52:38
 */
public interface AmMarketClient {

	List<InvitePrizeConfVO> getListByGroupCode(String groupCode);

	void updateJoinTime(String borrowNid, Integer nowTime);

	StringResponse checkActivityIfAvailable(Integer activityId);

	void saveReturnCash(ReturnCashRequest returnCashRequest);
}