/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.promotion;

import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;
import com.hyjf.am.resquest.admin.PlatformCountRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.PlatformCountCustomize;
import com.hyjf.am.trade.service.admin.promotion.PlatformCountService;
import com.hyjf.am.vo.admin.PlatformCountCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fq
 * @version PlatformCountController, v0.1 2018/8/9 15:22
 */
@RestController
@RequestMapping("/am-admin/platform_count")
public class PlatformCountController extends BaseController {
    @Autowired
    private PlatformCountService platformCountService;

    /**
     * 获取列表
     * @param request
     * @return
     */
    @RequestMapping("/search_action")
    public PlatformCountCustomizeResponse searchAction(@RequestBody PlatformCountRequest request) {
            PlatformCountCustomizeResponse response = new PlatformCountCustomizeResponse();
            List<PlatformCountCustomize> list = platformCountService.searchAction(request);
            if (!CollectionUtils.isEmpty(list)) {
                    List<PlatformCountCustomizeVO> voList = CommonUtils.convertBeanList(list, PlatformCountCustomizeVO.class);
                    response.setResultList(voList);
            }
            return response;
    }
}






