/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.repair.mobilesynchronize;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.exception.MobileSynchronizeService;
import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.vo.admin.MobileSynchronizeCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.AsteriskProcessUtil;
import com.hyjf.common.validator.CheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_MODIFY;
import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_VIEW;

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
    private static final String PERMISSIONS = "mobilesynchronize";

    @ApiOperation(value = "获取手机号同步列表",notes = "获取手机号同步列表")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<MobileSynchronizeCustomizeVO>> searchAction(@RequestBody MobileSynchronizeRequest synchronizeRequest,HttpServletRequest request){
        // 已开户用户数量
        int count = mobileSynchronizeService.countBankOpenAccountUser(synchronizeRequest);
        // 异常列表list
        List<MobileSynchronizeCustomizeVO> mobileSynchronizeCustomizeVOList = mobileSynchronizeService.selectBankOpenAccountUserList(synchronizeRequest);
        boolean isShow = this.havePermission(request,PERMISSIONS + ":" + ShiroConstants.PERMISSION_HIDDEN_SHOW);
        if(!isShow){
            for(MobileSynchronizeCustomizeVO mobileSynchronizeCustomizeVO:mobileSynchronizeCustomizeVOList){
                mobileSynchronizeCustomizeVO.setMobile(AsteriskProcessUtil.getAsteriskedValue(mobileSynchronizeCustomizeVO.getMobile()));
            }
        }
        return new AdminResult<>(ListResult.build(mobileSynchronizeCustomizeVOList,count));
    }

    @ApiOperation(value = "同步手机号",notes = "同步手机号")
    @PostMapping(value = "/modifyAction")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_MODIFY)
    public AdminResult modifyAction(HttpServletRequest request, @RequestBody MobileSynchronizeRequest mobileSynchronizeRequest){
        //检查必要参数
        CheckUtil.check(StringUtils.isNotBlank(mobileSynchronizeRequest.getUserId()), MsgEnum.ERR_OBJECT_REQUIRED,"用户id");
        CheckUtil.check(StringUtils.isNotBlank(mobileSynchronizeRequest.getAccountId()), MsgEnum.ERR_OBJECT_REQUIRED,"电子账户号");
        Integer userId = Integer.valueOf(getUser(request).getId());
        JSONObject jsonObject = mobileSynchronizeService.updateMobile(userId,mobileSynchronizeRequest);
        String status = jsonObject.getString("status");
        String statusDesc = jsonObject.getString("result");
        if("success".equals(status)){
            return new AdminResult(SUCCESS,statusDesc);
        }else{
            return new AdminResult(FAIL,statusDesc);
        }
    }
}
