/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.promotion;

import com.hyjf.am.response.admin.promotion.PlatformUserCountCustomizeResponse;
import com.hyjf.am.resquest.admin.PlatformCountRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.PlatformUserCountCustomize;
import com.hyjf.am.user.service.admin.promotion.PlatformCountService;
import com.hyjf.am.vo.admin.PlatformUserCountCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fq
 * @version PlatformCountController, v0.1 2018/8/13 9:22
 */
@RestController
@RequestMapping("/am-user/platform_count")
public class PlatformCountController extends BaseController {

    @Autowired
    private PlatformCountService platformCountService;

    /**
     * 获取平台用户信息
     * @return
     */
    @RequestMapping("/get_info")
    PlatformUserCountCustomizeResponse getUserInfo(PlatformCountRequest request) {
        PlatformUserCountCustomizeResponse response = new PlatformUserCountCustomizeResponse();
        // 获取用户相关信息
        List<PlatformUserCountCustomize> list = platformCountService.getUserInfo(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<PlatformUserCountCustomizeVO> voList = CommonUtils.convertBeanList(list, PlatformUserCountCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
