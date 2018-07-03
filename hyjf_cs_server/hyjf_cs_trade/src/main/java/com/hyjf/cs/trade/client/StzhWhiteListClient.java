/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.STZHWhiteListVO;

/**
 * @author wangjun
 * @version StzhWhiteListClient, v0.1 2018/7/2 17:42
 */
public interface StzhWhiteListClient {
    STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId);
}
