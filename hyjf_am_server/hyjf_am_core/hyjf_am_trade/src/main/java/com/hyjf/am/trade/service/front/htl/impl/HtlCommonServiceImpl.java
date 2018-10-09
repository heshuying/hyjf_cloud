/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.htl.impl;

import com.hyjf.am.trade.dao.mapper.customize.HtlCommonCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.ProductSearchForPage;
import com.hyjf.am.trade.service.front.htl.HtlCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version HtlCommonServiceImpl, v0.1 2018/7/19 12:27
 */
@Service
public class HtlCommonServiceImpl implements HtlCommonService {

    @Autowired
    HtlCommonCustomizeMapper htlCommonCustomizeMapper;

    /**
     * 获取投资人本金信息
     * @param productSearchForPage
     * @return
     */
    @Override
    public ProductSearchForPage selectUserPrincipal(ProductSearchForPage productSearchForPage) {
        productSearchForPage = htlCommonCustomizeMapper.selectUserPrincipal(productSearchForPage);
        return productSearchForPage;

    }

}
