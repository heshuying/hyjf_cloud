package com.hyjf.admin.controller.promotion.appReconcliation;

import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.AppChannelReconciliationRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * app渠道统计明细
 * @author lisheng
 * @version AppChannelStatisticsDetailController, v0.1 2018/9/21 17:22
 */
@Api(tags ="app渠道统计明细")
@RestController
@RequestMapping("/hyjf-admin/promotion/app/channel/detail")
public class AppChannelStatisticsDetailController {

    @ApiOperation(value = "app渠道统计明细-画面初始化", notes = "app渠道统计明细-画面初始化")
    @PostMapping("/init")
    public AppChannelReconciliationResponse init(HttpServletRequest request, @RequestBody AppChannelReconciliationRequest form){
        AppChannelReconciliationResponse response = new AppChannelReconciliationResponse();
        
        return response;
    }
}