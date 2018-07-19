/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter.promotion;

import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlatformCountService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;
import com.hyjf.am.vo.admin.PlatformCountCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuqiang
 * @version PlatformCountController, v0.1 2018/7/18 17:46
 */
@Api(description = "数据中心-平台统计")
@RestController
@RequestMapping("/hyjf-admin/promotion/platformcount")
public class PlatformCountController extends BaseController {
    @Autowired
    private PlatformCountService platformCountService;

    @ApiOperation(value = "数据中心-平台统计", notes = "数据中心-平台统计列表查询")
    @RequestMapping("/searchaction")
    public AdminResult<ListResult<PlatformCountCustomizeVO>> searchAction(@RequestBody PlatformCountRequestBean requestBean) {
        PlatformCountCustomizeResponse response = platformCountService.searchAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

}
