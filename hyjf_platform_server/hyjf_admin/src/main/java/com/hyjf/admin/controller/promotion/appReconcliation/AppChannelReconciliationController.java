package com.hyjf.admin.controller.promotion.appReconcliation;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.promotion.AppChannelReconciliationService;
import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.response.admin.promotion.UtmResultResponse;
import com.hyjf.am.resquest.admin.AppChannelReconciliationRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/24 16:38
 * @Description: AppChannelReconciliationController
 */
@Api(value = "app渠道对账",tags ="app渠道对账")
@RestController
@RequestMapping("/hyjf-admin/promotion/app/channelreconciliation")
public class AppChannelReconciliationController extends BaseController {

    private AppChannelReconciliationService appChannelReconciliationService;

    @ApiOperation(value = "app渠道对账-画面初始化", notes = "app渠道对账-画面初始化")
    @PostMapping("/init")
    public AppChannelReconciliationResponse init(HttpServletRequest request, @RequestBody AppChannelReconciliationRequest form){
        AppChannelReconciliationResponse response = new AppChannelReconciliationResponse();
        // 获取登录用户的userId
        AdminSystemVO adminSystemVO = this.getUser(request);
        if(null != adminSystemVO.getId()){
            Integer userId = Integer.parseInt(adminSystemVO.getId());
            // 根据用户Id查询渠道账号管理
            AdminUtmReadPermissionsVO adminUtmReadPermissions = this.appChannelReconciliationService.selectAdminUtmReadPermissions(userId);
            if (adminUtmReadPermissions != null) {
                form.setUtmIds(adminUtmReadPermissions.getUtmIds());// 封装到页面
            }
            //TODO 此功能需要重构
            response = appChannelReconciliationService.getReconciliationPage(form);
        }
        return response;
    }
}
