/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.poundage;

import com.hyjf.admin.common.controller.BaseController;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.service.PoundageService;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PoundageController, v0.1 2018/9/3 11:47
 */
@Api(value = "资金中心-手续费分账",tags = "资金中心-手续费分账")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/poundage")
public class PoundageController extends BaseController {

    @Autowired
    private PoundageService poundageService;

    /**
     * 按条件查询手续费分账列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "手续费分账列表",notes = "手续费分账列表")
    @PostMapping(value = "/poundagelist")
    public AdminResult<ListResult<PoundageCustomizeVO>> poundageList(@RequestBody PoundageListRequest request){
        Integer count = poundageService.getPoundageCount(request);
        count = (count == null)?0:count;
        List<PoundageCustomizeVO> poundageCustomizeVOList = poundageService.searchPoundageList(request);
        return new AdminResult<>(ListResult.build(poundageCustomizeVOList,count));
    }
}
