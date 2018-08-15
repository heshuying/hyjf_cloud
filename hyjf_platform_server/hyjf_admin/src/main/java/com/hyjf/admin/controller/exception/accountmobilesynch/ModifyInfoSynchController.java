/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.accountmobilesynch;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.service.ModifyInfoService;
import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import com.hyjf.common.enums.MsgEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: ModifyInfoSynch, v0.1 2018/8/15 11:45
 */
@RestController
@RequestMapping("/hyjf-admin/exception/accountmobilesynch")
@Api(value = "异常中心-线下修改信息同步",tags = "异常中心-线下修改信息同步")
public class ModifyInfoSynchController {

    @Autowired
    private ModifyInfoService modifyInfoService;

    @ApiOperation(value = "修改信息列表查询",notes = "修改信息列表查询")
    @PostMapping(value = "/searchlist")
    public AdminResult mobileList(@RequestBody AccountMobileSynchRequest request){
        AdminResult adminResult = new AdminResult();
        Map<String,Object> map = new HashMap<>();
        // 数据总数
        Integer count = modifyInfoService.getModifyInfoCount(request);
        map.put("count",count);
        // 异常列表list
        List<AccountMobileSynchVO> accountMobileSynchVOList = modifyInfoService.searchModifyInfoList(request);
        map.put("accountMobileSynchVOList",accountMobileSynchVOList);
        adminResult.setData(map);
        return adminResult;
    }

    @ApiOperation(value = "修改信息列表查询",notes = "修改信息列表查询")
    @PostMapping(value = "/addaction")
    public AdminResult addAction(@RequestBody AccountMobileSynchRequest request){
        AdminResult adminResult = new AdminResult();
        Integer flag = modifyInfoService.insertAccountMobileSynch(request);
        if(flag==0){
            adminResult.setStatusInfo(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        }else if(flag==1){
            adminResult.setStatusInfo(BaseResult.FAIL,"当前用户已有未同步数据，无法重复添加");
        }else if(flag==2){
            adminResult.setStatusInfo(MsgEnum.ERR_USER_INFO_GET);
        }else if(flag==3){
            adminResult.setStatusInfo(MsgEnum.STATUS_CE000007);
        }else if(flag==4){
            adminResult.setStatusInfo(MsgEnum.ERR_AMT_RECHARGE_BANK_CARD_GET);
        }else{
            adminResult.setStatusInfo(BaseResult.FAIL,"添加失败");
        }
        return adminResult;
    }

    /**
     * 根据主键id删除一条信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "修改信息列表查询",notes = "修改信息列表查询")
    @PostMapping(value = "/deleteaction")
    public AdminResult deleteAction(@RequestBody AccountMobileSynchRequest request){
        AdminResult adminResult = new AdminResult();
        Integer delete = modifyInfoService.deleteAccountMobileSynch(request);
        if(delete>0){
            adminResult.setStatusInfo(BaseResult.SUCCESS,BaseResult.SUCCESS_DESC);
        }else{
            adminResult.setStatusInfo(BaseResult.FAIL,"删除失败");
        }
        return adminResult;
    }
}