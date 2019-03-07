/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.repair.bindcardrepair;


import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.exception.BindCardExceptionService;
import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.vo.admin.BindCardExceptionCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_MODIFY;
import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_VIEW;

/**
 * @author: sunpeikai
 * @version: BindCardExceptionController, v0.1 2018/10/9 10:52
 */
@Api(value = "异常中心-江西银行卡异常",tags = "异常中心-江西银行卡异常")
@RestController
@RequestMapping(value = "/hyjf-admin/exception/bindcardexception")
public class BindCardRepairController extends BaseController {

    @Autowired
    private BindCardExceptionService bindCardExceptionService;
    private static final String PERMISSIONS = "bindcardexception";


    @ApiOperation(value = "银行卡异常列表",notes = "银行卡异常列表")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<BindCardExceptionCustomizeVO>> searchAction(@RequestBody BindCardExceptionRequest request){
        // 数据总数
        Integer count = bindCardExceptionService.getBindCardExceptionCount(request);
        // 异常列表list
        List<BindCardExceptionCustomizeVO> bindCardExceptionCustomizeVOList = bindCardExceptionService.searchBindCardExceptionList(request);
        return new AdminResult<>(ListResult.build(bindCardExceptionCustomizeVOList,count));
    }

    @ApiOperation(value = "银行卡更新",notes = "银行卡更新")
    @PostMapping(value = "/modifyAction")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_MODIFY)
    public AdminResult modifyAction(@RequestBody BindCardExceptionRequest request){
        Integer userId = request.getUserId();
        String accountId = request.getAccountId();
        if(userId == null || userId == 0){
            return new AdminResult(BaseResult.FAIL,"userId不能为空");
        }
        if(StringUtils.isBlank(accountId)){
            return new AdminResult(BaseResult.FAIL,"accountId不能为空");
        }
        try{
            bindCardExceptionService.updateBindCard(userId,accountId);
        }catch(Exception e){
            logger.error("更新银行卡出错,e -> [{}]",e.getMessage());
            return new AdminResult(BaseResult.FAIL,"银行卡同步失败");
        }
        return new AdminResult(BaseResult.SUCCESS,"银行卡同步操作成功");
    }

}
