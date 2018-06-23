/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.HjhLabelResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.trade.service.HjhPlanService;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version HjhPlanController, v0.1 2018/6/13 17:21
 */
@RestController
@RequestMapping("/am-trade/hjhPlan")
public class HjhPlanController {

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
    @RequestMapping("/selectHjhInstConfigByInstCode{instCode}")
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
    public HjhPlanResponse getHjhPlanByPlanNid(@PathVariable String planNid) {
        HjhPlanResponse response = new HjhPlanResponse();
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


}
