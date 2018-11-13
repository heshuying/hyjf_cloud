/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.signatrues.client;

import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;

/**
 * @author yaoyong
 * @version CsMessageClient, v0.1 2018/8/14 19:46
 */
public interface CsMessageClient {
    Integer insertAccountWebList(AccountWebListVO accountWebList);

	AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId);
}
