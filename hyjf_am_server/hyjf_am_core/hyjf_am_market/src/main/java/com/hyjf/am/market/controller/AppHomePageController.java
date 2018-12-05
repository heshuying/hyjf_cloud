/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller;

import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;
import com.hyjf.am.market.service.AppHomePageService;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AppHomePageController, v0.1 2018/7/26 10:54
 */
@RestController
@RequestMapping("/am-market/homepage")
public class AppHomePageController {
    private static final Logger logger = LoggerFactory.getLogger(AppHomePageController.class);

    @Autowired
    private AppHomePageService appHomePageService;

    /**
     * 获取起始页广告信息
     * 查询首页的bannner列表
     */
    @PostMapping("/getStartPage")
    public AppAdsCustomizeResponse searchBannerList(@RequestBody Map<String, Object> ads) {
        logger.info(this.getClass().getName(), "获取起始页广告信息 start", "/am-market/homepage/getStartPage");
        AppAdsCustomizeResponse response = new AppAdsCustomizeResponse();
        List<AppAdsCustomize> list = appHomePageService.searchBannerList(ads);
        if (!CollectionUtils.isEmpty(list)) {
            List<AppAdsCustomizeVO> voList = CommonUtils.convertBeanList(list, AppAdsCustomizeVO.class);
            response.setResultList(voList);
            return response;
        }
        logger.info(this.getClass().getName(), "获取起始页广告信息 end", "/am-market/homepage/getStartPage");
        return response;
    }
}
