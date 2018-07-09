/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.PlatformTransferResponse;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.service.admin.finance.PlatformTransferService;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
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
 * @version: PlatformTransferController, v0.1 2018/7/9 11:07
 */
@Api(value = "资金中心-转账管理-平台转账")
@RestController
@RequestMapping(value = "/am-trade/platformtransfer")
public class PlatformTransferController extends BaseController {

    @Autowired
    private PlatformTransferService platformTransferService;

    @ApiOperation(value = "平台转账-查询count",notes = "平台转账-查询count")
    @PostMapping(value = "/getplatformtransfercount")
    public Integer getPlatformTransferCount(PlatformTransferListRequest request){
        return platformTransferService.getPlatformTransferCount(request);
    }

    @ApiOperation(value = "平台转账-查询转账列表",notes = "平台转账-查询转账列表")
    @PostMapping(value = "/searchplatformtransferlist")
    public PlatformTransferResponse searchPlatformTransferList(PlatformTransferListRequest request){
        PlatformTransferResponse response = new PlatformTransferResponse();
        Integer count = platformTransferService.getPlatformTransferCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        List<AccountRecharge> accountRechargeList = platformTransferService.searchPlatformTransferList(request);
        if(!CollectionUtils.isEmpty(accountRechargeList)){
            List<AccountRechargeVO> accountRechargeVOList = CommonUtils.convertBeanList(accountRechargeList,AccountRechargeVO.class);
            response.setResultList(accountRechargeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
