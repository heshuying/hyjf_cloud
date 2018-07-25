package com.hyjf.cs.trade.controller.web.tradedetail;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.trade.bean.ObligatoryRightAjaxBean;
import com.hyjf.cs.trade.bean.TradeDetailBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.TradeDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version TradeDetail, v0.1 2018/6/27 10:10
 */
@Api(value = "Web交易明细页面")
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
    public Map<String,Object> getAccountTradeList( HttpServletRequest request) {
        logger.info("web初始化交易类型列表");
        Map<String,Object> result = new HashMap<>();
        // 交易类型列表
        List<AccountTradeVO> trades = this.tradeDetailService.selectTradeTypes();
        result.put("trades", trades);
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
    public TradeDetailBean searchUserTradeList(@RequestHeader(value = "token", required = true) String token,
                                               @RequestBody @Valid TradeDetailBeanRequest form,
                                               HttpServletRequest request) {
        logger.info("web获取用户收支明细列表分页数据, token is :{}", JSONObject.toJSONString(token));
        WebViewUserVO user=tradeDetailService.getUsersByToken(token);
        form.setRoleId(user.getRoleId());
        form.setUserId(user.getUserId().toString());
        TradeDetailBean result  = tradeDetailService.searchUserTradeList(form);
        result.setListType("trade");
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
    public TradeDetailBean searchUserRechargeList(@RequestHeader(value = "token", required = true) String token,
                                               @RequestBody @Valid TradeDetailBeanRequest form,
                                               HttpServletRequest request) {
        logger.info("web获取用户充值记录列表分页数据, token is :{}", JSONObject.toJSONString(token));
        WebViewUserVO user=tradeDetailService.getUsersByToken(token);
        form.setRoleId(user.getRoleId());
        form.setUserId(user.getUserId().toString());
        TradeDetailBean result  = tradeDetailService.searchUserRechargeList(form);
        result.setListType("recharge");
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
    public TradeDetailBean searchUserWithdrawList(@RequestHeader(value = "token", required = true) String token,
                                                  @RequestBody @Valid TradeDetailBeanRequest form,
                                                  HttpServletRequest request) {
        logger.info("web获取用户提现记录列表分页数据, token is :{}", JSONObject.toJSONString(token));
        WebViewUserVO user=tradeDetailService.getUsersByToken(token);
        form.setRoleId(user.getRoleId());
        form.setUserId(user.getUserId().toString());
        TradeDetailBean result  = tradeDetailService.searchUserWithdrawList(form);
        result.setListType("withdraw");
        return result;
    }
}
