/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.SubCommissionListConfigResponse;
import com.hyjf.am.response.admin.SubCommissionResponse;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.SubCommission;
import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;
import com.hyjf.am.trade.service.admin.finance.SubCommissionService;
import com.hyjf.am.vo.admin.SubCommissionListConfigVO;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: SubCommissionController, v0.1 2018/7/10 10:12
 */
@Api(value = "资金中心-平台账户分佣",tags ="资金中心-平台账户分佣")
@RestController
@RequestMapping(value = "/am-trade/subcommission")
public class SubCommissionController extends BaseController {

    @Autowired
    private SubCommissionService subCommissionService;

    /**
     * 查询发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "发起账户分佣",notes = "发起账户分佣所需的detail信息")
    @GetMapping(value = "/searchsubcommissionlistconfig")
    public SubCommissionListConfigResponse searchSubCommissionListConfig(){
        SubCommissionListConfigResponse response = new SubCommissionListConfigResponse();
        List<SubCommissionListConfig> subCommissionListConfigList = subCommissionService.searchSubCommissionListConfig();
        if(!CollectionUtils.isEmpty(subCommissionListConfigList)){
            List<SubCommissionListConfigVO> subCommissionListConfigVOList = CommonUtils.convertBeanList(subCommissionListConfigList,SubCommissionListConfigVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(subCommissionListConfigVOList);
        }
        return response;
    }

    /**
     * 插入发起账户分佣数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "插入发起账户分佣数据",notes = "插入发起账户分佣数据")
    @PostMapping(value = "/insertsubcommission")
    public Boolean insertSubCommission(@RequestBody SubCommissionVO subCommissionVO){
        return subCommissionService.insertSubCommission(subCommissionVO);
    }

    /**
     * 插入发起账户分佣数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "插入发起账户分佣数据",notes = "插入发起账户分佣数据")
    @GetMapping(value = "/searchsubcommissionbyorderid/{orderId}")
    public SubCommissionResponse searchSubCommissionByOrderId(@PathVariable String orderId){
        SubCommissionResponse response = new SubCommissionResponse();
        SubCommission subCommission = subCommissionService.searchSubCommissionByOrderId(orderId);
        if(subCommission != null){
            SubCommissionVO subCommissionVO = CommonUtils.convertBean(subCommission,SubCommissionVO.class);
            response.setResult(subCommissionVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据筛选条件查询分佣数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "平台账户分佣-查询count",notes = "平台账户分佣-查询count")
    @PostMapping(value = "/getsubcommissioncount")
    public Integer getSubCommissionCount(@RequestBody SubCommissionRequest request){
        return subCommissionService.getSubCommissionCount(request);
    }

    /**
     * 根据筛选条件查询分佣数据list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "平台转账-查询转账列表",notes = "平台转账-查询转账列表")
    @PostMapping(value = "/searchsubcommissionlist")
    public SubCommissionResponse searchSubCommissionList(@RequestBody SubCommissionRequest request){
        SubCommissionResponse response = new SubCommissionResponse();
        Integer count = subCommissionService.getSubCommissionCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchPlatformTransferList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<SubCommission> subCommissionList = subCommissionService.searchSubCommissionList(request);
        if(!CollectionUtils.isEmpty(subCommissionList)){
            List<SubCommissionVO> subCommissionVOList = CommonUtils.convertBeanList(subCommissionList,SubCommissionVO.class);
            response.setResultList(subCommissionVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 发起平台账户分佣
     * @auth sunpeikai
     * @param request 参数
     * @return
     */
    @ApiOperation(value = "平台转账-发起平台账户分佣",notes = "平台转账-发起平台账户分佣")
    @PostMapping(value = "/subCommission")
    public SubCommissionResponse subCommission(@RequestBody SubCommissionRequest request){
        SubCommissionResponse response = new SubCommissionResponse();
        JSONObject jsonObject = subCommissionService.subCommission(request);
        response.setJsonObject(jsonObject);
        response.setRtn(Response.SUCCESS);
        return response;
    }

}
