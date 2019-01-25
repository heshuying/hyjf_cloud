/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.borrow;

import com.hyjf.cs.trade.bean.assetpush.PushRequestBean;
import com.hyjf.cs.trade.bean.assetpush.PushResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author fuqiang
 * @version ApiAssetPushService, v0.1 2018/6/11 18:06
 */
public interface ApiAssetPushService extends BaseTradeService {

	/**
	 * 个人资产推送
	 *
	 * @param pushRequestBean
	 */
	PushResultBean assetPush(PushRequestBean pushRequestBean);

	/**
	 * 企业资产推送
	 *
	 * @param pushRequestBean
	 */
	PushResultBean companyAssetPush(PushRequestBean pushRequestBean);

}
