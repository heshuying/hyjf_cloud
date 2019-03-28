/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.trade.HjhPlanRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.customize.DebtPlanAccedeCustomize;
import com.hyjf.am.trade.dao.model.customize.DebtPlanBorrowCustomize;
import com.hyjf.am.trade.dao.model.customize.HjhPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.UserHjhInvistDetailCustomize;
import com.hyjf.am.trade.service.front.account.AccountService;
import com.hyjf.am.trade.service.front.hjh.HjhPlanService;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.DebtPlanBorrowCustomizeVO;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version HjhPlanController, v0.1 2018/6/13 17:21
 */
@RestController
@RequestMapping("/am-trade/hjhPlan")
public class HjhPlanController extends BaseController {

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
     * @Description 根据计划编号查询计划(主库查)
     * @Author liubin
     * @Version v0.1
     * @Date 2018/6/19 14:04
     */
    @RequestMapping("/doGetHjhPlanByPlanNid/{planNid}")
    public com.hyjf.am.response.user.HjhPlanResponse doGetHjhPlanByPlanNid(@PathVariable String planNid) {
        com.hyjf.am.response.user.HjhPlanResponse response = new com.hyjf.am.response.user.HjhPlanResponse();
        HjhPlan plan = hjhPlanService.doGetHjhPlanByNid(planNid);
        HjhPlanVO result = CommonUtils.convertBean(plan, HjhPlanVO.class);
        response.setResult(result);
        return response;
    }

    /**
     * 插入计划明细表
     * @param planAccede
     * @return
     */
    @PostMapping("/insertHJHPlanAccede")
    public IntegerResponse insertHJHPlanAccede(@RequestBody HjhAccedeVO planAccede) {
        logger.info("加入计划开始进行插入表操作，userid{}  计划编号{}  ",planAccede.getUserId(),planAccede.getPlanNid());
        Account userAccount  = accountService.getAccount(planAccede.getUserId());
        Integer result = hjhPlanService.insertHJHPlanAccede(planAccede,userAccount);
        IntegerResponse response = new IntegerResponse();
        response.setResultInt(result);
        logger.info("加入计划结束插入表操作，userid{}  计划编号{}  结果 {}",planAccede.getUserId(),planAccede.getPlanNid(),result);
        return response;
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
        List<BorrowAndInfoVO> list =  hjhPlanService.getPlanBorrowList(params);
        response.setResultList(list);
        return response;
    }


    /**
     * 查询汇计划标的组成count和总加入金额
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

    /**
     * 查询汇计划加入记录count
     * @author zhangyk
     * @date 2018/7/23 10:41
     */
    @RequestMapping("/getPlanAccedeCount")
    public HjhAccedeResponse getPlanAccedeCount(@RequestBody Map<String,Object> params){
        HjhAccedeResponse response = new HjhAccedeResponse();
        Map<String,Object> totalData =  hjhPlanService.getPlanAccecdeTotal(params);
        response.setTotalData(totalData);
        return response;
    }



    /**
     * 汇计划加入记录list
     * @author zhangyk
     * @date 2018/7/24 19:29
     */
    @RequestMapping("/getPlanAccedeList")
    public HjhAccedeListResponse  getPlanAccedeList(@RequestBody Map<String,Object> params) {
        HjhAccedeListResponse response = new HjhAccedeListResponse();
        List<HjhAccedeCustomizeVO> list = hjhPlanService.getPlanAccecdeList(params);
        response.setResultList(list);
        return response;
    }


    /**
     * 统计相应的计划加入记录总数
     * @param params
     * @return
     */
    @RequestMapping("/countPlanBorrowRecordTotal")
    public int countPlanBorrowRecordTotal(@RequestBody Map<String, Object> params){
        return hjhPlanService.countPlanBorrowRecordTotal(params);
    }

    /**
     * 查询相应的计划标的记录列表
     */
    @RequestMapping("/selectPlanBorrowList")
    public DebtPlanBorrowCustomizeResponse selectPlanBorrowList(@RequestBody Map<String, Object> params){
        DebtPlanBorrowCustomizeResponse response = new DebtPlanBorrowCustomizeResponse();
        List<DebtPlanBorrowCustomize> planAccedeList=hjhPlanService.selectPlanBorrowList(params);
        if(CollectionUtils.isNotEmpty(planAccedeList)){
            response.setResultList(CommonUtils.convertBeanList(planAccedeList,DebtPlanBorrowCustomizeVO.class));
        }
        return response;
    }

    /**
     * 统计相应的计划总数
     * @param params
     * @return
     */
    @PostMapping("/selectPlanAccedeSum")
    public Long selectPlanAccedeSum(@RequestBody Map<String, Object> params){
        return hjhPlanService.selectPlanAccedeSum(params);
    }



    /**
     * 查询相应的计划的加入列表
     */
    @PostMapping("/selectPlanAccedeList")
    public DebtPlanAccedeCustomizeResponse selectPlanAccedeList(@RequestBody Map<String, Object> params){
        DebtPlanAccedeCustomizeResponse response = new DebtPlanAccedeCustomizeResponse();
        List<DebtPlanAccedeCustomize> list = hjhPlanService.selectPlanAccedeList(params);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,DebtPlanAccedeCustomizeVO.class));
        }
        return response;
    }

    /**
     * 统计最后三天的服务记录 add by nxl
     * app和微信端统计服务数量
     */
    @GetMapping("/countPlanAccedeRecord/{planNid}")
    public IntegerResponse countPlanAccedeRecord(@PathVariable String planNid){
        IntegerResponse response = new IntegerResponse();
        Integer countPlanAccedeRecord = hjhPlanService.countPlanAccedeRecord(planNid);
        response.setResultInt(countPlanAccedeRecord);
        return response;
    }

}
