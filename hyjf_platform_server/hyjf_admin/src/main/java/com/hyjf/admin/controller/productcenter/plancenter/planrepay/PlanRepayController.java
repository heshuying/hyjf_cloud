package com.hyjf.admin.controller.productcenter.plancenter.planrepay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.HjhRepayRequestBean;
import com.hyjf.admin.beans.response.HjhRepayResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanRepayService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 订单退出  Warning : 汇计划三期 订单退出改为计划还款
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-计划还款",tags ="产品中心-汇计划-计划还款")
@RestController
@RequestMapping(value = "/planrepay")
public class PlanRepayController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PlanRepayService planRepayService;

    /**
     * 资金中心 - 订单退出 检索下拉框
     * @return
     */
    @ApiOperation(value = "计划还款检索下拉框", notes = "计划还款检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox() {
        JSONObject jsonObject = new JSONObject();

        // 类型List
        List<Object> orderStatusList = new ArrayList<>();

        // 初始Map
        Map<String, Object> orderStatusMap = new HashedMap();
        Map<String, Object> orderStatusMap1 = new HashedMap();
        Map<String, Object> orderStatusMap2 = new HashedMap();
        //设置对应键值对
        orderStatusMap.put("key", 8);
        orderStatusMap.put("value", "未还款");
        orderStatusMap1.put("key", 10);
        orderStatusMap1.put("value", "还款中");
        orderStatusMap2.put("key", 11);
        orderStatusMap2.put("value", "还款完成");

        orderStatusList.add(orderStatusMap);
        orderStatusList.add(orderStatusMap1);
        orderStatusList.add(orderStatusMap2);

        // 初始组装所有数据的Map
        Map<String, Object> allMap = new HashedMap();

        allMap.put("orderStatusList", orderStatusList);

        if (orderStatusList != null){
            jsonObject.put("status", BaseResult.SUCCESS);
            jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
            jsonObject.put("data", allMap);
        }else {
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG);
            jsonObject.put("data", "");
        }

        return jsonObject;
    }

    /**
     * 订单退出初始化列表
     * @param repayRequestBean
     * @return
     */
    @ApiOperation(value = "订单退出", notes = "订单退出初始化列表")
    @PostMapping(value = "/init")
    public AdminResult<ListResult<HjhRepayVO>> init(@RequestBody HjhRepayRequestBean repayRequestBean){
        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        //初始化时应默认清算时间不能为空
//        if (StringUtils.isEmpty(repayRequest.getRepayTimeStart()) && StringUtils.isEmpty(repayRequest.getRepayTimeEnd())){
//            return new AdminResult<>(FAIL, "请输入清算开始和结束时间!");
//        }

        //实际退出时间不能为空
//        if (StringUtils.isNotEmpty(repayRequest.getActulRepayTimeStart()) && StringUtils.isNotEmpty(repayRequest.getActulRepayTimeEnd())){
//            return new AdminResult<>(FAIL, "请输入实际退出开始和结束时间!");
//        }

        //防止前端将起始时间传错误.
//        try {
//            Date timeStart = dateFormat.parse(repayRequest.getRepayTimeStart());
//            Date timeEnd = dateFormat.parse(repayRequest.getRepayTimeEnd());
//
//            if (timeStart.getTime() > timeEnd.getTime()){
//                return new AdminResult<>(FAIL, "清算结束时间应大于等于开始时间!");
//            }
//
//            Date actuTimeStart = dateFormat.parse(repayRequest.getActulRepayTimeStart());
//            Date actuTimeEnd = dateFormat.parse(repayRequest.getActulRepayTimeEnd());
//            if (actuTimeStart.getTime() > actuTimeEnd.getTime()){
//                return new AdminResult<>(FAIL, "实际退出结束时间应大于等于开始时间!");
//            }
//        }catch (ParseException e){
//            return new AdminResult<>(FAIL, e.getMessage());
//        }

        //初始化返回List
        List<HjhRepayVO> returnList = null;
        // 查询列表
        HjhRepayResponse hjhRepayList = this.planRepayService.selectHjhRepayList(repayRequest);
        if (hjhRepayList == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(hjhRepayList)){
            return new AdminResult<>(FAIL, hjhRepayList.getMessage());
        }

        if (CollectionUtils.isNotEmpty(hjhRepayList.getResultList())){
            returnList = CommonUtils.convertBeanList(hjhRepayList.getResultList(), HjhRepayVO.class);
            return new AdminResult<ListResult<HjhRepayVO>>(ListResult.build(returnList, hjhRepayList.getCount()));
        }else {
            return new AdminResult<ListResult<HjhRepayVO>>(ListResult.build(returnList, 0));
        }
    }

    @ApiOperation(value = "还款明细", notes = "订单退出->还款明细")
    @PostMapping(value = "/repaymentDetails")
    public AdminResult repaymentDetails(@RequestBody HjhRepayRequestBean repayRequestBean){

        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        HjhRepayResponseBean repayResponseBean = planRepayService.selectByAccedeOrderId(repayRequestBean.getAccedeOrderIdSrch());

        return new AdminResult(repayResponseBean);
    }

}
