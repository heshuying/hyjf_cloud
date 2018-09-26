/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO; /**
 * @author yaoyong
 * @version CsMessageClient, v0.1 2018/8/14 19:46
 */
public interface CsMessageClient {
    Integer insertAccountWebList(AccountWebListVO accountWebList);

	AppChannelStatisticsDetailVO getAppChannelStatisticsDetailByUserId(Integer userId);
}
