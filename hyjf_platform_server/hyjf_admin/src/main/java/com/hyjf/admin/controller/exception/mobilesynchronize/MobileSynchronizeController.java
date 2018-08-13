/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.mobilesynchronize;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.controller.BaseController;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.exception.MobileSynchronizeService;
import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.vo.admin.MobileSynchronizeCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeController, v0.1 2018/8/13 11:41
 */
@Api(value = "手机号同步",tags = "手机号同步")
@RestController
@RequestMapping(value = "/exception/mobilesynchronize")
public class MobileSynchronizeController extends BaseController {

    @Autowired
    private MobileSynchronizeService mobileSynchronizeService;

    @ApiOperation(value = "获取手机号同步列表",notes = "获取手机号同步列表")
    @PostMapping(value = "/searchAction")
    public AdminResult searchAction(@RequestBody MobileSynchronizeRequest request){
        Map<String,Object> map = new HashMap<>();
        // 已开户用户数量
        int count = mobileSynchronizeService.countBankOpenAccountUser(request);
        map.put("count",count);
        // 异常列表list
        List<MobileSynchronizeCustomizeVO> mobileSynchronizeCustomizeVOList = mobileSynchronizeService.selectBankOpenAccountUserList(request);
        map.put("mobileSynchronizeCustomizeVOList",mobileSynchronizeCustomizeVOList);
        return new AdminResult(map);
    }

    @ApiOperation(value = "同步手机号",notes = "同步手机号")
    @PostMapping(value = "/modifyAction")
    public AdminResult modifyAction(@RequestHeader(value = "userId")Integer userId, @RequestBody MobileSynchronizeRequest request){
        JSONObject jsonObject = new JSONObject();
        jsonObject = mobileSynchronizeService.updateMobile(userId,request);
        return new AdminResult(jsonObject);
    }
}
