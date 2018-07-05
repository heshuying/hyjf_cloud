/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.admin.AssociatedRecordListResponse;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.finance.AssociatedRecordsService;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AssociatedRecordsController, v0.1 2018/7/5 14:37
 */
@Api(value = "资金中心-定向转账-定向转账")
@RestController
@RequestMapping("/am-trade/associatedrecords")
public class AssociatedRecordsController extends BaseController {

    @Autowired
    private AssociatedRecordsService associatedRecordsService;

    @ApiOperation(value = "查询定向转账列表",notes = "查询定向转账列表")
    @PostMapping("/getassociatedrecordscount")
    public Integer getAssociatedRecordsCount(@RequestBody AssociatedRecordListRequest request){
        return associatedRecordsService.getAssociatedRecordsCount(request);
    }
    @ApiOperation(value = "查询定向转账列表",notes = "查询定向转账列表")
    @PostMapping("/searchassociatedrecordlist")
    public AssociatedRecordListResponse searchAssociatedRecordList(@RequestBody AssociatedRecordListRequest request){
        AssociatedRecordListResponse response = new AssociatedRecordListResponse();
        Integer count = associatedRecordsService.getAssociatedRecordsCount(request);
        Paginator paginator = new Paginator(request.getCurrPage(),count);
        request.setLimitStart(paginator.getOffset());
        request.setLimitEnd(paginator.getLimit());
        logger.info("searchDirectionalTransferList::::::::::limitStart=[{}],limitEnd=[{}]",request.getLimitStart(),request.getLimitEnd());
        List<AssociatedRecordListVo> associatedRecordListVoList = associatedRecordsService.searchAssociatedRecordList(request);
        if(!CollectionUtils.isEmpty(associatedRecordListVoList)){
            response.setRtn("00");
            response.setResultList(associatedRecordListVoList);
        }
        return response;
    }
}
