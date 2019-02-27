package com.hyjf.cs.trade.controller.web.tradedetail;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TradeDetailBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.trade.TradeDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetail, v0.1 2018/6/27 10:10
 */
@Api(tags = "web端-交易明细页面")
@RestController
@RequestMapping("/hyjf-web/tradedetail")
public class TradeDetailController  extends BaseTradeController {
    @Autowired
    private TradeDetailService tradeDetailService;
    /**
     * @Description 资产管理页面初始化
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "初始化交易类型列表", notes = "初始化交易类型列表")
    @PostMapping(value = "/getAccountTradeList")
    public WebResult<Object> getAccountTradeList( HttpServletRequest request) {

        logger.info("web初始化交易类型列表");
        WebResult<Object> result=new WebResult<Object>();
        // 交易类型列表
        List<AccountTradeVO> trades = this.tradeDetailService.selectTradeTypes();
        if(trades == null){
            result.setData(new ArrayList<AccountTradeVO>());
        } else {
            result.setData(trades);
        }
        result.setStatus(BaseResult.SUCCESS);
        result.setStatusDesc(BaseResult.SUCCESS_DESC);
        return result;
    }


    /**
     * @Description 获取用户收支明细列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户收支明细列表分页数据", notes = "获取用户收支明细列表分页数据")
    @PostMapping(value = "/searchUserTradeList", produces = "application/json;charset=utf-8")
    public TradeDetailBean searchUserTradeList(@RequestHeader(value = "userId") int userId,HttpServletRequest request,@RequestBody @Valid TradeDetailBeanRequest form) {
        logger.info("web获取用户收支明细列表分页数据, userId is :{}", JSONObject.toJSONString(userId));
        WebViewUserVO user=tradeDetailService.getUserFromCache(userId);
        TradeDetailBean result = new TradeDetailBean();
        result.setListType("trade");
        if(user != null){
            if(!user.isBankOpenAccount()){
                return result;
            }
            if(user.getRoleId() != null){
            	form.setRoleId(user.getRoleId());
            }
            form.setUserId(user.getUserId().toString());
            result  = tradeDetailService.searchUserTradeList(form);
            result.setListType("trade");
        }
        return result;

    }


    /**
     * @Description 获取用户充值记录列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户充值记录列表分页数据", notes = "获取用户充值记录列表分页数据")
    @PostMapping(value = "/searchUserRechargeList", produces = "application/json;charset=utf-8")
    public TradeDetailBean searchUserRechargeList(@RequestHeader(value = "userId", required = true) int userId,@RequestBody @Valid TradeDetailBeanRequest form) {
        logger.info("web获取用户充值记录列表分页数据, userId is :{}", JSONObject.toJSONString(userId));
        WebViewUserVO user=tradeDetailService.getUserFromCache(userId);
        logger.info("startDate -> " + form.getStartDate() + "endDate -> " + form.getEndDate());
        TradeDetailBean result = new TradeDetailBean();
        result.setListType("recharge");
        if(user != null){
            if(!user.isBankOpenAccount()){
                return result;
            }
            if(user.getRoleId() != null){
            	form.setRoleId(user.getRoleId());
            }
            form.setUserId(user.getUserId().toString());
            result  = tradeDetailService.searchUserRechargeList(form);
            result.setListType("recharge");
        }
        return result;
    }

    /**
     * @Description 获取用户提现记录列表分页数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户提现记录列表分页数据", notes = "获取用户提现记录列表分页数据")
    @PostMapping(value = "/searchUserWithdrawList", produces = "application/json;charset=utf-8")
    public TradeDetailBean searchUserWithdrawList(@RequestHeader(value = "userId", required = true) int userId,@RequestBody @Valid TradeDetailBeanRequest form) {
        logger.info("web获取用户提现记录列表分页数据, userId is :{}", JSONObject.toJSONString(userId));
        WebViewUserVO user=tradeDetailService.getUserFromCache(userId);
        TradeDetailBean result = new TradeDetailBean();
        result.setListType("withdraw");
        if(user != null){
            if(!user.isBankOpenAccount()){
                return result;
            }
            if(user.getRoleId()!= null){
            	form.setRoleId(user.getRoleId());
            }
            form.setUserId(user.getUserId().toString());
            result  = tradeDetailService.searchUserWithdrawList(form);
            result.setListType("withdraw");
        }
        return result;
    }

    private TradeDetailBeanRequest createTradeDetailBeanRequest(HttpServletRequest request) {
        TradeDetailBeanRequest bean=new TradeDetailBeanRequest();
        bean.setTrade(request.getParameter("trade"));
        bean.setStartDate(request.getParameter("startDate"));
        bean.setEndDate(request.getParameter("endDate"));
        return bean;

    }
}
