/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.mobilesynchronize;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.exception.MobileSynchronizeService;
import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.vo.admin.MobileSynchronizeCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeController, v0.1 2018/8/13 11:41
 */
@Api(value = "异常中心-手机号同步",tags = "异常中心-手机号同步")
@RestController
@RequestMapping(value = "/hyjf-admin/exception/mobilesynchronize")
public class MobileSynchronizeController extends BaseController {

    @Autowired
    private MobileSynchronizeService mobileSynchronizeService;

    @ApiOperation(value = "获取手机号同步列表",notes = "获取手机号同步列表")
    @PostMapping(value = "/searchAction")
    public AdminResult<ListResult<MobileSynchronizeCustomizeVO>> searchAction(@RequestBody MobileSynchronizeRequest request){
        // 已开户用户数量
        int count = mobileSynchronizeService.countBankOpenAccountUser(request);
        // 异常列表list
        List<MobileSynchronizeCustomizeVO> mobileSynchronizeCustomizeVOList = mobileSynchronizeService.selectBankOpenAccountUserList(request);
        return new AdminResult<>(ListResult.build(mobileSynchronizeCustomizeVOList,count));
    }

    @ApiOperation(value = "同步手机号",notes = "同步手机号")
    @PostMapping(value = "/modifyAction")
    public AdminResult modifyAction(HttpServletRequest request, @RequestBody MobileSynchronizeRequest mobileSynchronizeRequest){
        Integer userId = Integer.valueOf(getUser(request).getId());
        JSONObject jsonObject = mobileSynchronizeService.updateMobile(userId,mobileSynchronizeRequest);
        String status = jsonObject.getString("status");
        String statusDesc = jsonObject.getString("result");
        return new AdminResult(status,statusDesc);
    }
}
