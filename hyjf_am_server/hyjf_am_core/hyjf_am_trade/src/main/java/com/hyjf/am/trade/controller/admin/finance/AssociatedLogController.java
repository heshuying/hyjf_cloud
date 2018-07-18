/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.admin.BindLogResponse;
import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.finance.AssociatedLogService;
import com.hyjf.am.vo.admin.BindLogVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AssociatedLogController, v0.1 2018/7/5 15:44
 */
@Api(value = "资金中心-定向转账-定向转账",description = "资金中心-定向转账-定向转账")
@RestController
@RequestMapping("/am-trade/associatedlog")
public class AssociatedLogController extends BaseController {

    @Autowired
    private AssociatedLogService associatedLogService;

    /**
     * 根据筛选条件查询绑定日志count
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据筛选条件查询绑定日志count",notes = "根据筛选条件查询绑定日志count")
    @PostMapping("/getbindlogcount")
    public Integer getBindLogCount(BindLogListRequest request){
        return associatedLogService.getBindLogCount(request);
    }

    /**
     * 根据筛选条件查询绑定日志list
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据筛选条件查询绑定日志list",notes = "根据筛选条件查询绑定日志list")
    @PostMapping("/searchbindloglist")
    public BindLogResponse searchBindLogList(BindLogListRequest request){
        BindLogResponse response = new BindLogResponse();
        Integer count = associatedLogService.getBindLogCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchBindLogList::::::::::limitStart=[{}],limitEnd=[{}]",request.getLimitStart(),request.getLimitEnd());
        List<BindLogVO> bindLogVOList = associatedLogService.searchBindLogList(request);
        if(!CollectionUtils.isEmpty(bindLogVOList)){
            response.setRtn("0");
            response.setResultList(bindLogVOList);
        }
        return response;
    }
}
