package com.hyjf.cs.trade.controller.web.assetmanage;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;
import com.hyjf.cs.trade.bean.PlanAjaxBean;
import com.hyjf.cs.trade.bean.RepaymentPlanAjaxBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.assetmanage.AssetManageService;
import com.hyjf.cs.trade.vo.WebGetRepayMentRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pangchengchao
 * @version WebAssetManageController, v0.1 2018/6/20 17:19
 */
@Api(tags = "web端-资产管理页面")
@RestController
@RequestMapping("/hyjf-web/assetmanage")
public class WebAssetManageController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(WebAssetManageController.class);

    @Autowired
    private AssetManageService assetManageService;
    /**
     * @Description 资产管理页面初始化
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户资产信息", notes = "获取用户资产信息")
    @PostMapping(value = "/init")
    public Map<String,Object> init(@RequestHeader(value = "userId") Integer userId,
                                   @RequestBody @Valid AssetManageBeanRequest form) {
        logger.info("web获取用户资产信息, userId is :{}", userId);
        Map<String,Object> result = new HashMap<>();

        String currentTab = form.getCurrentTab();
        AccountVO account = assetManageService.getAccount(userId);
        result.put("data", account);
        result.put("currentTab", currentTab);
        result.put("status", BaseResult.SUCCESS);
        result.put("statusDesc", BaseResult.SUCCESS_DESC);
        return result;
    }

    /**
     * @Description 获取用户当前持有债权列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户当前持有债权列表分页数据", notes = "获取用户当前持有债权列表分页数据")
    @PostMapping(value = "/getCurrentHoldObligatoryRight", produces = "application/json;charset=utf-8")
    public WebResult<Object> getCurrentHoldObligatoryRight(@RequestHeader(value = "userId") Integer userId,
                                                                 @RequestBody @Valid AssetManageBeanRequest form) {
        logger.info("web获取用户当前持有债权列表分页数据, userId is :{}", userId);
        WebResult<Object> result = new WebResult<Object>();
        form.setUserId(userId);
        ObligatoryRightAjaxBean bean  = assetManageService.createCurrentHoldObligatoryRightListPage(form);
        result.setData(bean);
        result.setPage(bean.getPage());
        return result;
    }

    /**
     * @Description 获取用户已回款债权页面json数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date  
     */
    @ApiOperation(value = "获取用户已回款债权列表分页数据", notes = "获取用户已回款债权列表分页数据")
    @PostMapping(value = "/getRepayMent", produces = "application/json;charset=utf-8")
    public WebResult<Object> getRepayMent(@RequestHeader(value = "userId") Integer userId,
                                          @RequestBody @Valid AssetManageBeanRequest form,
                                                HttpServletRequest request) {

        logger.info("web获取用户已回款债权列表分页数据, userId is :{}", userId);
        WebResult<Object> result = new WebResult<Object>();
        // 用户ID
        form.setUserId(userId);
        ObligatoryRightAjaxBean bean  =assetManageService.createRepayMentListPage(form);

        result.setData(bean);
        result.setPage(bean.getPage());
        return result;
    }
    /**
     * @Description 获取用户转让记录债权页面json数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户转让记录债权页面json数据", notes = "获取用户转让记录债权页面json数据")
    @PostMapping(value = "/getCreditRecordList", produces = "application/json;charset=utf-8")
    public WebResult<Object> getCreditRecordList(@RequestHeader(value = "userId") Integer userId,
                                                 @RequestBody @Valid AssetManageBeanRequest form) {
        logger.info("web获取用户转让记录债权页面json数据, userId is :{}", userId);
        WebResult<Object> result = new WebResult<Object>();
        // 用户ID
        form.setUserId(userId);
        ObligatoryRightAjaxBean bean  =assetManageService.getCreditRecordList(form);

        result.setData(bean);
        result.setPage(bean.getPage());
        return result;
    }
    /**
     * @Description 获取用户当前持有计划列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户当前持有计划列表分页数据", notes = "获取用户当前持有计划列表分页数据")
    @PostMapping(value = "/getCurrentHoldPlan", produces = "application/json;charset=utf-8")
    public WebResult<Object> getCurrentHoldPlan(@RequestHeader(value = "userId") Integer userId,
                                           @RequestBody @Valid AssetManageBeanRequest form,
                                           HttpServletRequest request) {

        logger.info("web获取用户当前持有计划列表分页数据, userId is :{}", userId);
        WebResult<Object> result = new WebResult<Object>();
        PlanAjaxBean bean = new PlanAjaxBean();
        form.setUserId(userId);
        bean=assetManageService.getCurrentHoldPlan(form);
        result.setData(bean);
        result.setPage(bean.getPage());
        return result;
    }

    /**
     * @Description 获取用户当前持有计划列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户已回款计划页面json数据", notes = "获取用户已回款计划页面json数据")
    @PostMapping(value = "/getRepayMentPlan", produces = "application/json;charset=utf-8")
    public WebResult<Object> getRepayMentPlan(@RequestHeader(value = "userId") Integer userId,
                                           @RequestBody @Valid AssetManageBeanRequest form,
                                           HttpServletRequest request) {

        logger.info("web获取用户已回款计划页面json数据, userId is :{}", userId);
        WebResult<Object> result = new WebResult<Object>();
        PlanAjaxBean bean = new PlanAjaxBean();

        form.setUserId(userId);
        bean=assetManageService.getRepayMentPlan(form);
        result.setData(bean);
        result.setPage(bean.getPage());
        return result;
    }

    @ApiOperation(value = "获取用户还款计划页面json数据", notes = "获取用户还款计划页面json数据")
    @PostMapping(value = "/getRepayPlanInfo", produces = "application/json;charset=utf-8")
    public WebResult<Object> getRepayPlanInfo(@RequestBody @Valid WebGetRepayMentRequestVO requestVO){
        logger.info("web端获取用户还款计划页面json数据, borrowNid:{}, nid:{}, type:{}", requestVO.getBorrowNid(),requestVO.getNid(),requestVO.getTypeStr());
        WebResult<Object> result = new WebResult<Object>();
        RepaymentPlanAjaxBean repaymentPlanAjaxBean = assetManageService.getRepayPlanInfo(requestVO);
        result.setData(repaymentPlanAjaxBean);
        return result;
    }
}
