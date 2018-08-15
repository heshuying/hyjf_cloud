/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.accountmobilesynch;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.ModifyInfoService;
import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
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
        return adminResult;
    }
}
