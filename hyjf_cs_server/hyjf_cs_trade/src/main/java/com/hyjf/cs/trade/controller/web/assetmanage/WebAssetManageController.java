package com.hyjf.cs.trade.controller.web.assetmanage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.AssetManagePlanRequest;
import com.hyjf.am.vo.config.DebtConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.MyCreditDetailBean;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;
import com.hyjf.cs.trade.bean.PlanAjaxBean;
import com.hyjf.cs.trade.bean.RepayPlanInfoBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.assetmanage.AssetManageService;
import com.hyjf.cs.trade.service.assetmanage.DebtConfigService;
import com.hyjf.cs.trade.vo.WebGetRepayMentRequestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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

    @Autowired
    private DebtConfigService debtConfigService;

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
        DebtConfigVO config = debtConfigService.getDebtConfig();
        if(config!=null){
            result.put("toggle",config.getToggle());
            result.put("closeDes",config.getCloseDes());
        }
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
        logger.info("bean:{}", JSONObject.toJSONString(bean));
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

    /**
     * 获取用户还款计划页面数据
     * @author wangjun
     * @return
     */
    @ApiOperation(value = "获取用户还款计划页面数据", notes = "获取用户还款计划页面数据")
    @PostMapping(value = "/getRepayPlanInfo", produces = "application/json;charset=utf-8")
    public WebResult<Object> getRepayPlanInfo(@RequestBody @Valid WebGetRepayMentRequestVO requestVO){
        logger.info("web端获取用户还款计划页面数据, borrowNid:{}, nid:{}, type:{}", requestVO.getBorrowNid(), requestVO.getNid(), requestVO.getTypeStr());
        WebResult<Object> result = new WebResult<Object>();
        RepayPlanInfoBean repayPlanInfo = assetManageService.getRepayPlanInfo(requestVO);
        //初始化list
        if (repayPlanInfo.getCurrentHoldRepayMentPlanList() == null){
            repayPlanInfo.setCurrentHoldRepayMentPlanList(new ArrayList<>());
        }
        result.setData(repayPlanInfo);
        return result;
    }

    /**
     * 获取用户散标转让记录详情
     * @author wangjun
     * @return
     */
    @ApiOperation(value = "获取用户散标转让记录详情", notes = "获取用户散标转让记录详情")
    @GetMapping(value = "/getMyCreditAssignDetail/{creditNid}")
    public WebResult<Object> getMyCreditAssignDetail(@RequestHeader(value = "userId") Integer userId, @PathVariable String creditNid){
        logger.info("web端获取用户散标转让记录详情, userId:{}, creditNid:{}", userId, creditNid);
        WebResult<Object> result = new WebResult<Object>();
        MyCreditDetailBean myCreditDetailBean = assetManageService.getMyCreditAssignDetail(creditNid);
        //初始化list
        if(myCreditDetailBean.getRecordList() == null){
            myCreditDetailBean.setRecordList(new ArrayList<>());
        }
        result.setData(myCreditDetailBean);
        return result;
    }


    /**
     * 获取我加入的计划详情信息
     * @author zhangyk
     * @date 2018/8/18 15:59
     */
    @ApiOperation(value = "获取我加入的计划详情信息" , notes = "获取我加入的计划详情信息")
    @PostMapping(value = "/getMyPlanInfoDetail",produces = "application/json;charset=utf-8")
    public WebResult<Object> getMyPlanInfoDetail(@RequestHeader(value = "userId" ,required = true) Integer userId , @RequestBody AssetManagePlanRequest request){
        WebResult result = assetManageService.getMyHjhPlanInfoDetail(request,userId);
        return result;
    }

    /**
     *  原接口：com.hyjf.web.user.assetmanage.AssetManageControllersearchBorrowList()
     * @author zhangyk
     * @date 2018/11/22 16:58
     */
    @ApiOperation(value = "计划订单详情持有项目列表", notes = "计划订单详情持有项目列表")
    @PostMapping(value = "getOrderInvestList",produces = "application/json;charset=utf-8")
    public WebResult<Object> getOrderInvestList(@RequestHeader(value = "userId" ,required = true) Integer userId , @RequestBody AssetManagePlanRequest request){
        WebResult result = assetManageService.getOrderInvestList(request,userId);
        return result;
    }


    @ApiOperation(value = "汇添金详情", notes = "汇添金详情")
    @PostMapping(value = "getHtjDetail", produces = "application/json;charset=utf-8")
    public WebResult<Object> getHtjDetail(@RequestHeader(value = "userId") Integer userId,AssetManagePlanRequest request){
        WebResult result = assetManageService.getHtjDetail(request, userId);
        return result;
    }

}
