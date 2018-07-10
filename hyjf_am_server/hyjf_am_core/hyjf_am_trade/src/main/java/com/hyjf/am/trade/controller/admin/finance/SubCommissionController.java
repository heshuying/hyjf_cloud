/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.SubCommissionListConfigResponse;
import com.hyjf.am.response.admin.SubCommissionResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.SubCommission;
import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;
import com.hyjf.am.trade.service.admin.finance.SubCommissionService;
import com.hyjf.am.vo.admin.SubCommissionListConfigVO;
import com.hyjf.am.vo.admin.SubCommissionVO;
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
@Api(value = "资金中心-平台账户分佣")
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
    @PostMapping(value = "/searchsubcommissionlistconfig")
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
        List<SubCommission> subCommissionList = subCommissionService.searchSubCommissionByOrderId(orderId);
        if(subCommissionList != null && subCommissionList.size() == 1){
            SubCommissionVO subCommissionVO = CommonUtils.convertBean(subCommissionList.get(0),SubCommissionVO.class);
            response.setResult(subCommissionVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 更新分佣数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "更新分佣数据",notes = "更新分佣数据")
    @PostMapping(value = "/updatesubcommission")
    public Integer updateSubCommission(@RequestBody SubCommissionVO subCommissionVO){
        return subCommissionService.updateSubCommission(subCommissionVO);
    }

    /**
     * 根据订单号查询是否存在重复的AccountWebList数据
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @ApiOperation(value = "根据订单号查询是否存在重复的AccountWebList数据",notes = "根据订单号查询是否存在重复的AccountWebList数据")
    @PostMapping(value = "/accountweblistbyorderid/{orderId}")
    public Integer accountWebListByOrderId(@PathVariable String orderId){
        return subCommissionService.accountWebListByOrderId(orderId);
    }

}
