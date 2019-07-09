/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.service;

import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.wbs.qvo.CustomerSyncQO;

/**
 * @author cui
 * @version SyncCustomerBaseService, v0.1 2019/4/30 14:25
 */
public interface SyncCustomerBaseService {

    CustomerSyncQO build(WbsRegisterMqVO wbsRegisterMqVO);

}
