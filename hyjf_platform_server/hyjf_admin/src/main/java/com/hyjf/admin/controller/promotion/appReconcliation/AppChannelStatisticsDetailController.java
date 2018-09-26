package com.hyjf.admin.controller.promotion.appReconcliation;

import com.hyjf.admin.service.promotion.AppChannelReconciliationService;
import com.hyjf.admin.service.promotion.AppChannelStatisticsDetailService;
import com.hyjf.am.response.admin.AppChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * app渠道统计明细
 * @author lisheng
 * @version AppChannelStatisticsDetailController, v0.1 2018/9/21 17:22
 */
@Api(tags ="app渠道统计明细")
@RestController
@RequestMapping("/hyjf-admin/promotion/app/channel/detail")
public class AppChannelStatisticsDetailController {
    @Autowired
    AppChannelStatisticsDetailService appChannelStatisticsDetailService;
    @Autowired
    private AppChannelReconciliationService appChannelReconciliationService;

    @ApiOperation(value = "app渠道统计明细-画面初始化", notes = "app渠道统计明细-画面初始化")
    @PostMapping("/init")
    public AppChannelStatisticsDetailResponse init(@RequestBody AppChannelStatisticsDetailRequest request, @RequestHeader(value="userId")Integer userId){
        AppChannelStatisticsDetailResponse response = new AppChannelStatisticsDetailResponse();
        AdminUtmReadPermissionsVO adminUtmReadPermissions = this.appChannelReconciliationService.selectAdminUtmReadPermissions(userId);
        if (adminUtmReadPermissions != null) {
            //form.setUtmIds(adminUtmReadPermissions.getUtmIds());// 封装到页面
        }
        AppChannelStatisticsDetailResponse appChannelStatisticsDetailResponse = appChannelStatisticsDetailService.getstatisticsList(request);

        List<UtmPlatVO> appUtm = appChannelStatisticsDetailService.getAppUtm();
        return response;
    }
}