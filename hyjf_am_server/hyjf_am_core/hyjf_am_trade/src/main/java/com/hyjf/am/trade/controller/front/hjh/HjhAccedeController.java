/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhPlanDetailResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.customize.trade.PlanDetailCustomize;
import com.hyjf.am.trade.service.front.hjh.HjhAccedeService;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author PC-LIUSHOUYI
 * @version HjhAccedeController, v0.1 2018/6/25 10:09
 */
@Api(value = "汇计划加入明细表")
@RestController
@RequestMapping("/am-trade/hjhAccede")
public class HjhAccedeController extends BaseController {

    @Autowired
    HjhAccedeService hjhAccedeService;

    /**
     * 查询准备退出计划和准备进入锁定期的标的
     * @return
     */
    @RequestMapping("/selectWaitQuitHjhList")
    public HjhAccedeResponse selectWaitQuitHjhList() {
        HjhAccedeResponse response = new HjhAccedeResponse();
        List<HjhAccede> hjhAccedeResponse = hjhAccedeService.selectWaitQuitHjhList();
        if (hjhAccedeResponse != null) {
            response.setResultList(CommonUtils.convertBeanList(hjhAccedeResponse,HjhAccedeVO.class));
        }
        return response;
    }
    /**
     * 判断用户是否有持有中的计划。如果有，则不能解除投资授权和债转授权
     * @return
     */
    @GetMapping("/canCancelAuth/{userId}")
    public HjhAccedeResponse selectByAssignNidAndUserId(@PathVariable Integer userId) {
        HjhAccedeResponse response = new HjhAccedeResponse();
        if(hjhAccedeService.canCancelAuth(userId)) {
            response.setRtn(HjhAccedeResponse.SUCCESS);
        }else {
            response.setRtn(HjhAccedeResponse.FAIL);
        }
        return response;
    }

    @GetMapping("/getHjhAccedeByAccedeOrderId/{accedeOrderId}")
    public HjhAccedeResponse getHjhAccedeListByAccedeOrderId(@PathVariable String accedeOrderId){
        HjhAccedeResponse response = new HjhAccedeResponse();
        HjhAccede hjhAccede=hjhAccedeService.getHjhAccedeByAccedeOrderId(accedeOrderId);
        if (Validator.isNotNull(hjhAccede)){
            response.setResult(CommonUtils.convertBean(hjhAccede,HjhAccedeVO.class));
        }
        return response;
    }

    @GetMapping("/countAccede/{planNid}")
    public HjhAccedeResponse selectByAssignNidAndUserId(@PathVariable String planNid) {
        HjhAccedeResponse response = new HjhAccedeResponse();
        int count = hjhAccedeService.countAccede(planNid);
        response.setAccedeCount(count);
        return response;
    }

    @GetMapping("/getPlanDetail/{planNid}")
    public HjhPlanDetailResponse getPlanDetail(@PathVariable String planNid) {
        HjhPlanDetailResponse response = new HjhPlanDetailResponse();
        List<PlanDetailCustomize> list = hjhAccedeService.getPlanDetail(planNid);
        if (CollectionUtils.isNotEmpty(list)){
            List<PlanDetailCustomizeVO> result = CommonUtils.convertBeanList(list,PlanDetailCustomizeVO.class);
            response.setResultList(result);
        }
        return response;
    }


    @RequestMapping("/updateHjhAccedeByPrimaryKey")
    public Response<Integer> updateHjhAccedeByPrimaryKey(@RequestBody HjhAccedeVO hjhAccedeVO) {
        HjhAccede hjhAccede = new HjhAccede();
        BeanUtils.copyProperties(hjhAccedeVO,hjhAccede);
        return new Response(this.hjhAccedeService.updateHjhAccedeByPrimaryKey(hjhAccede));
    }
}
