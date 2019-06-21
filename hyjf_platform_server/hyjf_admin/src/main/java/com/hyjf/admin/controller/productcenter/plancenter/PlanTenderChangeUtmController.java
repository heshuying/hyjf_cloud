/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import com.google.common.base.Strings;
import com.hyjf.admin.beans.request.SyncTenderRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.PlanTenderChangeUtmService;
import com.hyjf.am.response.admin.TenderUpdateUtmHistoryResponse;
import com.hyjf.am.resquest.trade.UpdateTenderUtmExtRequest;
import com.hyjf.am.resquest.trade.UpdateTenderUtmRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeExtVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author cui
 * @version PlanTenderChangeUtmController, v0.1 2019/6/18 14:44
 */
@Api(value = "产品中心-汇计划-加入明细2",tags = "产品中心-汇计划-加入明细2")
@RestController
@RequestMapping(value = "/hyjf-admin/planutm")
public class PlanTenderChangeUtmController extends BaseController {

    @Autowired
    public CommonProducer commonProducer;

    @Autowired
    private PlanTenderChangeUtmService planTenderChangeUtmService;

    @ApiOperation(value = "加入计划订单详情",notes = "加入计划订单详情")
    @GetMapping(value = "/plan_tender_info/{planOrderId}")
    public AdminResult<HjhPlanAccedeCustomizeVO> getPlanTenderInfo(@PathVariable(value = "planOrderId") String planOrderId){

        HjhPlanAccedeCustomizeVO vo=planTenderChangeUtmService.getPlanTenderInfo(planOrderId);

        return new AdminResult<>(vo);

    }

    @ApiOperation(value = "加入计划订单修改渠道",notes = "加入计划订单修改渠道")
    @PostMapping(value = "/update_utm")
    public AdminResult updateTenderUtm(HttpServletRequest request, @RequestBody UpdateTenderUtmRequest updateTenderUtmRequest) {

        String nid = updateTenderUtmRequest.getNid();
        if (Strings.isNullOrEmpty(nid)) {
            throw new IllegalArgumentException("订单ID为空！");
        }

        UpdateTenderUtmExtRequest extRequest = new UpdateTenderUtmExtRequest();

        HjhPlanAccedeCustomizeVO hjhPlanAccedeCustomizeVO = planTenderChangeUtmService.getPlanTenderInfo(nid);

        extRequest.setNid(updateTenderUtmRequest.getNid());
        extRequest.setTopDeptName(hjhPlanAccedeCustomizeVO.getInviteUserRegionname());
        extRequest.setSecondDeptName(hjhPlanAccedeCustomizeVO.getInviteUserBranchname());
        extRequest.setThirdDeptName(hjhPlanAccedeCustomizeVO.getInviteUserDepartmentname());
        extRequest.setUpdateTime(Calendar.getInstance().getTime());

        extRequest.setTenderUtmId(updateTenderUtmRequest.getTenderUtmId());

        extRequest.setOperator(Integer.valueOf(getUser(request).getId()));

        return planTenderChangeUtmService.updateTenderUtm(extRequest);
    }

    @ApiOperation(value = "出借明细-修改渠道-修改记录",notes = "出借明细-修改渠道-修改记录")
    @GetMapping("/update_tender_utm_history/{nid}")
    public AdminResult<TenderUpdateUtmHistoryResponse> getTenderUtmChangeLog(@PathVariable(name = "nid") String nid){
        TenderUpdateUtmHistoryResponse response=planTenderChangeUtmService.getPlanTenderChangeLog(nid);
        return new AdminResult(response);
    }

    @ApiOperation(value = "订单渠道同步",notes ="订单渠道同步")
    @PostMapping("/sync_tender")
    public AdminResult syncTender(@RequestBody SyncTenderRequest syncTenderRequest){

        try {
            commonProducer.messageSend((new MessageContent(MQConstant.WBS_TENDER_SYNC_TOPIC, UUID.randomUUID().toString(),syncTenderRequest)));
        } catch (MQException e) {
            logger.error(e.getMessage());
            logger.error("订单渠道修改同步， 发送订单渠道同步消息MQ失败...", e);
        }

        return new AdminResult();

    }
}
