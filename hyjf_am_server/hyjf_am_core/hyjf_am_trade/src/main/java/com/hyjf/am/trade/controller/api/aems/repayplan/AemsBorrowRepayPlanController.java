/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.aems.repayplan;

import com.hyjf.am.response.trade.AemsBorrowRepayPlanCustomizeResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AemsBorrowRepayPlanCustomize;
import com.hyjf.am.trade.service.api.aems.repayplan.AemsBorrowRepayPlanService;
import com.hyjf.am.vo.trade.AemsBorrowRepayPlanCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * AEMS系统:还款计划查询Controller
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanController, v0.1 2018/12/12 14:45
 */
@RestController
@RequestMapping("/am-trade/aems/repayplan")
public class AemsBorrowRepayPlanController extends BaseController {

    @Autowired
    private AemsBorrowRepayPlanService aemsBorrowRepayPlanService;

    /**
     * 根据机构编号,查询还款计划数量
     *
     * @param params
     * @return
     */
    @RequestMapping("/selectBorrowRepayPlanCountsByInstCode")
    public AemsBorrowRepayPlanCustomizeResponse selectBorrowRepayPlanCountsByInstCode(@RequestBody Map<String, Object> params) {
        AemsBorrowRepayPlanCustomizeResponse response = new AemsBorrowRepayPlanCustomizeResponse();
        Integer count = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanCountsByInstCode(params);
        response.setCount(count);
        return response;
    }

    /**
     * 查询还款计划
     *
     * @param params
     * @return
     */
    @RequestMapping("/selectBorrowRepayPlanList")
    public AemsBorrowRepayPlanCustomizeResponse selectBorrowRepayPlanList(@RequestBody Map<String, Object> params) {
        AemsBorrowRepayPlanCustomizeResponse response = new AemsBorrowRepayPlanCustomizeResponse();
        List<AemsBorrowRepayPlanCustomize> resultList = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanList(params);
        if (CollectionUtils.isNotEmpty(resultList)) {
            List<AemsBorrowRepayPlanCustomizeVO> voList = CommonUtils.convertBeanList(resultList, AemsBorrowRepayPlanCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

}
