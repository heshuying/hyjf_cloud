/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.htl;

import com.hyjf.am.trade.dao.model.customize.ProductSearchForPage;

/**
 * @author zhangqingqing
 * @version HtlCommonService, v0.1 2018/7/19 12:27
 */
public interface HtlCommonService {
    /**
     * 获取汇天利用户本金
     * @param productSearchForPage
     * @return
     */
    ProductSearchForPage selectUserPrincipal(ProductSearchForPage productSearchForPage);
}
