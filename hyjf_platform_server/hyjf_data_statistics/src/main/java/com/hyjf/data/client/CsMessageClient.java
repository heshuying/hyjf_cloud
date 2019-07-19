/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.client;

import com.hyjf.am.vo.admin.JcCustomerServiceVO;

/**
 * @author yaoyong
 * @version CsMessageClient, v0.1 2019/6/24 14:45
 */
public interface CsMessageClient {

    /**
     * 获取客户服务数据
     * @return
     */
    JcCustomerServiceVO getCustomerService();
}
