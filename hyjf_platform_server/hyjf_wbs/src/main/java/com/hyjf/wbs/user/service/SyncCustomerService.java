package com.hyjf.wbs.user.service;

import com.hyjf.wbs.qvo.CustomerSyncQO;

import java.io.UnsupportedEncodingException;

/**
 * 客户信息回调
 * @author cui
 * @version SyncCustomerService, v0.1 2019/4/16 17:03
 */
public interface SyncCustomerService {

    public void sync(CustomerSyncQO customerSyncQO);

}
