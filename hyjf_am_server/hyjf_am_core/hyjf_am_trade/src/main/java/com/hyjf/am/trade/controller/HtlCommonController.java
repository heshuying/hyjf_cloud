/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.ProductSearchForPageResponse;
import com.hyjf.am.trade.dao.model.customize.ProductSearchForPage;
import com.hyjf.am.trade.service.admin.htl.HtlCommonService;
import com.hyjf.am.vo.trade.ProductSearchForPageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingqing
 * @version HtlCommonController, v0.1 2018/7/19 12:31
 */
@RestController
@RequestMapping("/am-trade/htlCommonController")
public class HtlCommonController {

    @Autowired
    HtlCommonService htlCommonService;

    @PostMapping("/selectUserPrincipal")
    public ProductSearchForPageResponse selectUserPrincipal(ProductSearchForPage productSearchForPage){
        ProductSearchForPageResponse response = new ProductSearchForPageResponse();
        ProductSearchForPage result = htlCommonService.selectUserPrincipal(productSearchForPage);
        if (null!=result){
            ProductSearchForPageVO productSearchForPageVO = new ProductSearchForPageVO();
            BeanUtils.copyProperties(result,productSearchForPageVO);
            response.setResult(productSearchForPageVO);
        }
        return response;
    }
}
