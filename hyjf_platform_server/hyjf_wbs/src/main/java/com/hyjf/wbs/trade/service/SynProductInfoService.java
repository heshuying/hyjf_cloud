package com.hyjf.wbs.trade.service;

import com.hyjf.wbs.qvo.ProductInfoQO;

/**
 * @Auther: wxd
 * @Date: 2019-04-18 10:11
 * @Description:
 */
public interface SynProductInfoService extends BaseService {
    public void sync(ProductInfoQO productInfoQO);
}
