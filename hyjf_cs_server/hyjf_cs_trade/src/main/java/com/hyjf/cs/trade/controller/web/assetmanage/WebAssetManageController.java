package com.hyjf.cs.trade.controller.web.assetmanage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;
import com.hyjf.cs.trade.bean.PlanAjaxBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.assetmanage.AssetManageService;
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
@Api(tags = "Web资产管理页面")
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
    public Map<String,Object> init(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        logger.info("web获取用户资产信息, token is :{}", JSONObject.toJSONString(token));
        Map<String,Object> result = new HashMap<>();
        WebViewUserVO user=assetManageService.getUsersByToken(token);

        String currentTab = request.getParameter("currentTab");
        AccountVO account = assetManageService.getAccount(user.getUserId());
        result.put("account", account);
        result.put("currentTab", currentTab);

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
    public ObligatoryRightAjaxBean getCurrentHoldObligatoryRight(@RequestHeader(value = "token", required = true) String token,
                                                                 @RequestBody @Valid AssetManageBeanRequest form,
                                                                 HttpServletRequest request) {
        logger.info("web获取用户当前持有债权列表分页数据, token is :{}", JSONObject.toJSONString(token));
        WebViewUserVO user=assetManageService.getUsersByToken(token);
        form.setUserId(user.getUserId().toString());
        ObligatoryRightAjaxBean result  = assetManageService.createCurrentHoldObligatoryRightListPage(form);
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
    public ObligatoryRightAjaxBean getRepayMent(@RequestHeader(value = "token", required = true) String token,
                                                @RequestBody @Valid AssetManageBeanRequest form,
                                                HttpServletRequest request) {

        logger.info("web获取用户已回款债权列表分页数据, token is :{}", JSONObject.toJSONString(token));
        // 用户ID
        WebViewUserVO user=assetManageService.getUsersByToken(token);
        form.setUserId(user.getUserId().toString());
        ObligatoryRightAjaxBean result  =assetManageService.createRepayMentListPage(form);

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
    public ObligatoryRightAjaxBean getCreditRecordList(@RequestHeader(value = "token", required = true) String token,
                                                @RequestBody @Valid AssetManageBeanRequest form,
                                                HttpServletRequest request) {
        logger.info("web获取用户转让记录债权页面json数据, token is :{}", JSONObject.toJSONString(token));
        // 用户ID
        WebViewUserVO user=assetManageService.getUsersByToken(token);
        form.setUserId(user.getUserId().toString());
        ObligatoryRightAjaxBean result  =assetManageService.getCreditRecordList(form);

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
    public PlanAjaxBean getCurrentHoldPlan(@RequestHeader(value = "token", required = true) String token,
                                           @RequestBody @Valid AssetManageBeanRequest form,
                                           HttpServletRequest request) {

        logger.info("web获取用户当前持有计划列表分页数据, token is :{}", JSONObject.toJSONString(token));
        // 用户ID
        WebViewUserVO user=assetManageService.getUsersByToken(token);
        PlanAjaxBean result = new PlanAjaxBean();
        if(user == null){
            return result;
        }
        form.setUserId(user.getUserId().toString());
        result=assetManageService.getCurrentHoldPlan(form);
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
    public PlanAjaxBean getRepayMentPlan(@RequestHeader(value = "token", required = true) String token,
                                           @RequestBody @Valid AssetManageBeanRequest form,
                                           HttpServletRequest request) {

        logger.info("web获取用户已回款计划页面json数据, token is :{}", JSONObject.toJSONString(token));
        // 用户ID
        WebViewUserVO user=assetManageService.getUsersByToken(token);
        PlanAjaxBean result = new PlanAjaxBean();
        if(user == null){
            return result;
        }
        form.setUserId(user.getUserId().toString());
        result=assetManageService.getRepayMentPlan(form);
        return result;
    }

}
