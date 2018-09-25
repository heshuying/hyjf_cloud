/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.promotion.ChannelReconciliationService;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.vo.admin.UtmVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fq
 * @version ChannelReconciliationController, v0.1 2018/9/21 9:20
 */
@Api(tags = "推广中心-PC渠道对账")
@RestController
@RequestMapping("/hyjf-admin/channelreconciliation")
public class PcChannelReconciliationController extends BaseController {
    @Autowired
    private ChannelReconciliationService channelService;

    @ApiOperation(value = "散标列表查询", notes = "散标列表查询")
    @RequestMapping("/search")
    public AdminResult searchAction(@RequestBody ChannelReconciliationRequest request) {
        ChannelReconciliationResponse response = channelService.searchAction(request);
        return new AdminResult(response);
    }

    @ApiOperation(value = "计划列表查询", notes = "计划列表查询")
    @RequestMapping("/search_hjh")
    public AdminResult searchHJHAction(@RequestBody ChannelReconciliationRequest request) {
        ChannelReconciliationResponse response = channelService.searchHJHAction(request);
        return new AdminResult(response);
    }

    @ApiOperation(value = "查询所有渠道", notes = "查询所有渠道")
    @RequestMapping("/search_utmlist")
    public AdminResult searchUtmList() {
        // 0:pc
        List<UtmVO> list = channelService.searchUtmList(0);
        return new AdminResult(list);
    }
}
