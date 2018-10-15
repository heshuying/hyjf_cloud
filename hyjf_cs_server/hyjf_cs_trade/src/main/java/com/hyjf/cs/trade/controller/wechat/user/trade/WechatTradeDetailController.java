/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.user.trade;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.vo.app.AppTradeListCustomizeVO;
import com.hyjf.am.vo.trade.account.AppAccountTradeListCustomizeVO;
import com.hyjf.cs.trade.bean.app.AppTradeDetailBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.trade.TradeDetailService;
import com.hyjf.cs.trade.vo.BaseResultBean;
import com.hyjf.cs.trade.vo.SimpleResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Description weChat端-交易明细
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Api(value = "weChat端-交易明细",tags = "weChat端-交易明细")
@RestController
@RequestMapping("/hyjf-wechat/wx/mytrade")
public class WechatTradeDetailController extends BaseTradeController {

    @Autowired
    private TradeDetailService tradeDetailService;


    @ApiOperation(value = "获取交易类型", notes = "获取交易类型")
    @PostMapping("/getTradeTypes.do")
    public BaseResultBean searchTradeTypes(HttpServletRequest request, HttpServletResponse response) {
        SimpleResultBean<List<AppAccountTradeListCustomizeVO>> baseResultBean = new SimpleResultBean<>();
        baseResultBean.setObject(tradeDetailService.searchAppTradeTypes());
        return baseResultBean;

    }
    /**
     * 用户收支明细
     *
     * @param trade
     * @return
     */
    @ApiOperation(value = "用户收支明细", notes = "用户收支明细")
    @ResponseBody
    @PostMapping(value = "/queryTradeList.do",  produces = "application/json; charset=utf-8")
    public Map<String, Object> searchTradeDetailList(@RequestHeader(value = "userId" , required = false )Integer userId, @RequestBody @Valid AppTradeDetailBeanRequest trade) {

        SimpleResultBean<Map<String, Object>> resultBean = new SimpleResultBean<>();

        Map<String, Object> mapVo = Maps.newHashMap();
        resultBean.setObject(mapVo);

        Preconditions.checkArgument(userId!=null);
        trade.setUserId(userId);
        AppTradeDetailBean appTradeDetailBean=tradeDetailService.createTradeDetailListPage(trade);
        int totalCount = appTradeDetailBean.getTradeTotal();

        List<AppTradeListCustomizeVO> lstTrade = appTradeDetailBean.getUserTrades();
        List<AppTradeListCustomizeVO> lstTradeCopy = Lists.newArrayList();
        lstTradeCopy.addAll(lstTrade);
        int page = trade.getCurrPage();
        int pageSize = trade.getPageSize();
        mapVo.put("lstTrade", lstTradeCopy);
        mapVo.put("currentPage", page);
        mapVo.put("pageSize", pageSize);
        mapVo.put("status", "000");
        mapVo.put("statusDesc", "成功");
        mapVo.put("endPage", page * pageSize >= totalCount ? "1" : "0");
        return mapVo;
    }



}
