/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.trade.wbs.WbsHjhPlanInfoResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.service.front.batch.WbsHjhPlanInfoService;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * WBS系统智投推送
 *
 * @author liuyang
 * @version WbsHjhPlanInfoController, v0.1 2019/4/16 9:17
 */

@Api(value = "WBS系统智投推送")
@RestController
@RequestMapping("/am-trade/wbsHjhPlanInfo")
public class WbsHjhPlanInfoController extends BaseController {
    @Autowired
    private WbsHjhPlanInfoService wbsHjhPlanInfoService;

    /**
     * WBS系统智投推送获取智投列表
     *
     * @return
     */
    @RequestMapping(value = "/selectWbsSendHjhPlanList")
    public WbsHjhPlanInfoResponse selectWbsSendHjhPlanList() {
        List<HjhPlanVO> resultList = new ArrayList<HjhPlanVO>();
        WbsHjhPlanInfoResponse response = new WbsHjhPlanInfoResponse();
        List<HjhPlan> list = this.wbsHjhPlanInfoService.selectWbsSendHjhPlanList();
        if (!CollectionUtils.isEmpty(list)) {
            resultList = CommonUtils.convertBeanList(list, HjhPlanVO.class);
            response.setResultList(resultList);
        }
        return response;
    }
}
