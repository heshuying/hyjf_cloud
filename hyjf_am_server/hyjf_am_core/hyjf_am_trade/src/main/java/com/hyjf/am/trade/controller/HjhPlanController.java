/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.trade.HjhPlanRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.customize.trade.HjhPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.UserHjhInvistDetailCustomize;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.trade.service.HjhPlanService;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version HjhPlanController, v0.1 2018/6/13 17:21
 */
@RestController
@RequestMapping("/am-trade/hjhPlan")
public class HjhPlanController extends BaseController{

    @Autowired
    private HjhPlanService hjhPlanService;

    @Autowired
    private AccountService accountService;

    /**
     * 获取现金贷资产方信息配置
     *
     * @param instCode
     * @return
     */
    @RequestMapping("/selectHjhInstConfigByInstCode/{instCode}")
    public HjhInstConfigResponse selectHjhInstConfigByInstCode(@PathVariable String instCode) {
        HjhInstConfigResponse response = new HjhInstConfigResponse();
        List<HjhInstConfig> hjhInstConfigList = hjhPlanService.selectHjhInstConfigByInstCode(instCode);
        if (!CollectionUtils.isEmpty(hjhInstConfigList)) {
            List<HjhInstConfigVO> voList = CommonUtils.convertBeanList(hjhInstConfigList, HjhInstConfigVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取标签
     *
     * @param borrowStyle
     * @return
     */
    @RequestMapping("/seleHjhLabel/{borrowStyle}")
    public HjhLabelResponse seleHjhLabel(@PathVariable String borrowStyle) {
        HjhLabelResponse response = new HjhLabelResponse();
        List<HjhLabel> list = hjhPlanService.seleHjhLabelByBorrowStyle(borrowStyle);
        if (!CollectionUtils.isEmpty(list)) {
            List<HjhLabelVO> voList = CommonUtils.convertBeanList(list, HjhLabelVO.class);
            response.setResultList(voList);
            return response;
        }
        return response;
    }

    /**
     * @Description 根据计划编号查询计划
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 14:04
     */
    @RequestMapping("/getHjhPlanByPlanNid/{planNid}")
    public com.hyjf.am.response.user.HjhPlanResponse getHjhPlanByPlanNid(@PathVariable String planNid) {
        com.hyjf.am.response.user.HjhPlanResponse response = new com.hyjf.am.response.user.HjhPlanResponse();
        HjhPlan plan = hjhPlanService.getHjhPlanByNid(planNid);
        HjhPlanVO result = CommonUtils.convertBean(plan, HjhPlanVO.class);
        response.setResult(result);
        return response;
    }

    /**
     * 插入计划明细表
     * @param planAccede
     * @return
     */
    @RequestMapping("/insertHJHPlanAccede")
    public int insertHJHPlanAccede(@RequestBody HjhAccedeVO planAccede) {
        Account useraccount  = accountService.getAccount(planAccede.getUserId());
        return hjhPlanService.insertHJHPlanAccede(planAccede,useraccount);
    }


    @PostMapping("/selectUserHjhInvistDetail")
    public UserHjhInvistDetailCustomizeResponse selectUserHjhInvistDetail(@RequestBody Map<String, Object> params){
        UserHjhInvistDetailCustomizeResponse response = new UserHjhInvistDetailCustomizeResponse();
        UserHjhInvistDetailCustomize userHjhInvistDetailCustomize = hjhPlanService.selectUserHjhInvistDetail(params);
        if (Validator.isNotNull(userHjhInvistDetailCustomize)){
            response.setResult(CommonUtils.convertBean(userHjhInvistDetailCustomize,UserHjhInvistDetailCustomizeVO.class));
        }
        return response;
    }

    @PostMapping("/selectAppHjhPlanList")
    public HjhPlanResponse selectAppHjhPlanList(@RequestBody HjhPlanRequest request){
        HjhPlanResponse response = new HjhPlanResponse();
        List<HjhPlanCustomize> list= hjhPlanService.selectAppHomeHjhPlan(request);
        if (!CollectionUtils.isEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,HjhPlanCustomizeVO.class));
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据plannid获取计划标的
     * @Date 11:25 2018/7/17
     * @Param planNid
     * @return
     */
    @RequestMapping("/gethjhplan/{planNid}")
    public com.hyjf.am.response.user.HjhPlanResponse getHjhPlan(@PathVariable String planNid) {
        com.hyjf.am.response.user.HjhPlanResponse response = new com.hyjf.am.response.user.HjhPlanResponse();
        HjhPlanVO hjhPlanVO = hjhPlanService.getHjhPlan(planNid);
        response.setResult(hjhPlanVO);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据plannid获取计划标的
     * @Date 11:25 2018/7/17
     * @Param planNid
     * @return
     */
    @RequestMapping("/gethjhaccede/{orderId}")
    public HjhAccedeResponse getHjhAccede(@PathVariable String orderId) {
        HjhAccedeResponse response = new HjhAccedeResponse();
        HjhAccedeVO hjhAccede = hjhPlanService.getHjhAccede(orderId);
        response.setResult(hjhAccede);
        return response;
    }



    /**
     * 查询汇计划标的组成List
     * @author zhangyk
     * @date 2018/7/23 10:41
     */
    @RequestMapping("/getPlanBorrowList")
    public BorrowResponse getPlanBorrowList(@RequestBody Map<String,Object> params){
        BorrowResponse response = new BorrowResponse();
        List<BorrowVO> list =  hjhPlanService.getPlanBorrowList(params);
        response.setResultList(list);
        return response;
    }


    /**
     * 查询汇计划标的组成count
     * @author zhangyk
     * @date 2018/7/23 10:41
     */
    @RequestMapping("/getPlanBorrowListCount")
    public HjhAccedeResponse getPlanBorrowListCount(@RequestBody Map<String,Object> params){
        HjhAccedeResponse response = new HjhAccedeResponse();
        Integer count =  hjhPlanService.getPlanBorrowListCount(params);
        response.setAccedeCount(count);
        return response;
    }

}
