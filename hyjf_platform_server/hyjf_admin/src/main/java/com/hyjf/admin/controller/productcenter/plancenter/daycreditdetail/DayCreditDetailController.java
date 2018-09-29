package com.hyjf.admin.controller.productcenter.plancenter.daycreditdetail;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.DayCreditDetailRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DayCreditDetailService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划-转让详情",tags ="产品中心-汇计划-资金计划-转让详情")
@RestController
@RequestMapping(value = "/dayCreditDetail")
public class DayCreditDetailController extends BaseController {

    @Autowired
    private DayCreditDetailService dayCreditDetailService;

    /**
     * 产品中心  -  汇计划 --  转让详情 -- 检索下拉框
     * @return
     */
    @ApiOperation(value = "转让详情检索下拉框", notes = "转让详情权检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        // 转让状态 List
        List<Object> transferStatusList = new ArrayList<>();

        Map<String, Object> transferStatusMap = new HashedMap();
        Map<String, Object> transferStatusMap1 = new HashedMap();
        Map<String, Object> transferStatusMap2 = new HashedMap();
        Map<String, Object> transferStatusMap3 = new HashedMap();

        transferStatusMap.put("key", 0);
        transferStatusMap.put("value", "承接中");
        transferStatusMap1.put("key", 1);
        transferStatusMap1.put("value", "部分承接");
        transferStatusMap2.put("key", 2);
        transferStatusMap2.put("value", "完全承接");
        transferStatusMap3.put("key", 3);
        transferStatusMap3.put("value", "承接终止");

        transferStatusList.add(transferStatusMap);
        transferStatusList.add(transferStatusMap1);
        transferStatusList.add(transferStatusMap2);
        transferStatusList.add(transferStatusMap3);

        // 还款方式 List
        List<Object> repaymentList = new ArrayList<>();

        // 还款方式Map
        Map<String, Object> repaymentMap = new HashedMap();
        Map<String, Object> repaymentMap1 = new HashedMap();
        Map<String, Object> repaymentMap2 = new HashedMap();
        Map<String, Object> repaymentMap3 = new HashedMap();

        repaymentMap.put("key", "month");
        repaymentMap.put("value", "等额本息");
        repaymentMap1.put("key", "end");
        repaymentMap1.put("value", "按月计息，到期还本还息");
        repaymentMap2.put("key", "endmonth");
        repaymentMap2.put("value", "先息后本");
        repaymentMap3.put("key", "endday");
        repaymentMap3.put("value", "按天计息，到期还本息");

        repaymentList.add(repaymentMap);
        repaymentList.add(repaymentMap1);
        repaymentList.add(repaymentMap2);
        repaymentList.add(repaymentMap3);

        // Map 集
        Map<String, Object> allMap = new HashedMap();
        allMap.put("transferStatusList", transferStatusList);
        allMap.put("repaymentList", repaymentList);

        jsonObject.put("status", BaseResult.SUCCESS);
        jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        jsonObject.put("data", allMap);

        return jsonObject;
    }

    /**
     * 资金计划 - 汇计划按天转让记录列表
     * @param request
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "汇计划按天转让记录列表")
    @PostMapping(value = "hjhDayCreditDetailList")
    @ResponseBody
    public AdminResult<ListResult<DayCreditDetailVO>> init(@RequestBody @Valid DayCreditDetailRequestBean request){

        DayCreditDetailRequest copyRequest = new DayCreditDetailRequest();
        BeanUtils.copyProperties(request, copyRequest);

        //默认应传入清算日期和转让人planNid
        if (StringUtils.isEmpty(request.getDate())){
            return new AdminResult<>(FAIL, "清算日期不能为空!");
        }
        if (StringUtils.isEmpty(request.getPlanNid())){
            return new AdminResult<>(FAIL, "转让人PlanNid不能为空!");
        }

        //初始化返回List
        List<DayCreditDetailVO> returnList = new ArrayList<>();

        //类表查询
        DayCreditDetailResponse response = this.dayCreditDetailService.hjhDayCreditDetailList(copyRequest);

        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }

        if (CollectionUtils.isNotEmpty(response.getResultList())){
            returnList = CommonUtils.convertBeanList(response.getResultList(), DayCreditDetailVO.class);
            return new AdminResult<ListResult<DayCreditDetailVO>>(ListResult.build(returnList, response.getCount()));
        }else {
            return new AdminResult<ListResult<DayCreditDetailVO>>(ListResult.build(returnList, 0));
        }
    }
}
