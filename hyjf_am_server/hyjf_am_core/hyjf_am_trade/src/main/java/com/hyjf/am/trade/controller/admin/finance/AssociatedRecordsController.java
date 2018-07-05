/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.admin.AssociatedRecordListResponse;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: AssociatedRecordsController, v0.1 2018/7/5 14:37
 */
@Api(value = "资金中心-定向转账-定向转账")
@RestController
@RequestMapping("/am-trade/associatedrecords")
public class AssociatedRecordsController {

    @ApiOperation(value = "查询定向转账列表",notes = "查询定向转账列表")
    @PostMapping("/getassociatedrecordscount")
    public Integer getAssociatedRecordsCount(@RequestBody AssociatedRecordListRequest request){

        return null;
    }
    @ApiOperation(value = "查询定向转账列表",notes = "查询定向转账列表")
    @PostMapping("/searchassociatedrecordlist")
    public AssociatedRecordListResponse searchAssociatedRecordList(@RequestBody AssociatedRecordListRequest request){

        return null;
    }
}
