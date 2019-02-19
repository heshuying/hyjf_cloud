package com.hyjf.cs.trade.service.trade.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.vo.app.AppTradeListCustomizeVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.account.AppAccountTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.TradeDetailBean;
import com.hyjf.cs.trade.bean.app.AppTradeDetailBean;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.trade.TradeDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetailServiceImpl, v0.1 2018/6/27 10:12
 */
@Service
public class TradeDetailServiceImpl extends BaseTradeServiceImpl implements TradeDetailService {
    private static final Logger logger = LoggerFactory.getLogger(TradeDetailServiceImpl.class);
    @Override
    public List<AccountTradeVO> selectTradeTypes() {
        return amTradeClient.selectTradeTypes();
    }

    @Override
    public TradeDetailBean searchUserTradeList(TradeDetailBeanRequest form) {
        TradeDetailBean result=new TradeDetailBean();

        // 统计相应的用户交易明细总数
        int recordTotal = this.amTradeClient.countUserTradeRecordTotal(form);

        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        form.setLimitStart(page.getOffset());
        form.setLimitEnd(page.getLimit());

        if (recordTotal > 0) {
            // 查询相应的用户交易明细列表
            List<WebUserTradeListCustomizeVO> userTrades = amTradeClient.searchUserTradeList(form);
            result.setData(userTrades);
        } else {
            result.setData(new ArrayList<WebUserTradeListCustomizeVO>());
        }
        result.setPage(page);
        return result;
    }

    @Override
    public TradeDetailBean searchUserRechargeList(TradeDetailBeanRequest form) {
        TradeDetailBean result=new TradeDetailBean();

        // 统计相应的用户充值记录总数
        int recordTotal = this.amTradeClient.countUserRechargeRecordTotal(form);

        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        form.setLimitStart(page.getOffset());
        form.setLimitEnd(page.getLimit());

        if (recordTotal > 0) {
            // 查询相应的用户充值记录列表
            List<WebUserRechargeListCustomizeVO> userTrades = amTradeClient.searchUserRechargeList(form);
            result.setData(userTrades);
        } else {
            result.setData(new ArrayList<WebUserRechargeListCustomizeVO>());
        }
        result.setPage(page);
        return result;
    }

    @Override
    public TradeDetailBean searchUserWithdrawList(TradeDetailBeanRequest form) {
        TradeDetailBean result=new TradeDetailBean();

        // 统计相应的用户提现记录总数
        int recordTotal = this.amTradeClient.countUserWithdrawRecordTotal(form);

        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        form.setLimitStart(page.getOffset());
        form.setLimitEnd(page.getLimit());

        if (recordTotal > 0) {
            // 查询相应的用户提现记录列表
            List<WebUserWithdrawListCustomizeVO> userTrades = amTradeClient.searchUserWithdrawList(form);
            result.setData(userTrades);
        } else {
            result.setData(new ArrayList<WebUserWithdrawListCustomizeVO>());
        }
        result.setPage(page);
        return result;
    }

    /**
     * 获取交易类型
     * @return
     */
    @Override
    public List<AppAccountTradeListCustomizeVO> searchAppTradeTypes() {
        return amTradeClient.searchAppTradeTypes();
    }

    @Override
    public AppTradeDetailBean createTradeDetailListPage(AppTradeDetailBeanRequest trade) {
        AppTradeDetailBean appTradeDetailBean=new AppTradeDetailBean();
        // 统计相应的用户出借项目总数
        int recordTotal = this.amTradeClient.countAppTradeDetailListRecordTotal(trade);
        Page page = Page.initPage(trade.getCurrPage(), trade.getPageSize());
        page.setTotal(recordTotal);
        trade.setLimitStart(page.getOffset());
        trade.setLimitEnd(page.getLimit());
        if (recordTotal > 0) {
            // 查询相应的用户出借项目列表
            List<AppTradeListCustomizeVO> userTrades = amTradeClient.searchAppTradeDetailList(trade);
            appTradeDetailBean.setTradeTotal(recordTotal);
            appTradeDetailBean.setUserTrades(userTrades);

        } else {
            logger.info("未查询到交易数据,返回月份标题....");
            appTradeDetailBean.setTradeTotal(0);
            appTradeDetailBean.setUserTrades(buildNoneTradeListReturn(trade.getYear(), trade.getMonth()));
        }
        return appTradeDetailBean;
    }

    /**
     * 没有交易明细的返回json
     * @param tradeYear
     * @param tradeMonth
     * @return
     */
    private List<AppTradeListCustomizeVO> buildNoneTradeListReturn(String tradeYear, String tradeMonth){
        List<AppTradeListCustomizeVO> list =  new ArrayList<AppTradeListCustomizeVO>();
        AppTradeListCustomizeVO customize = new AppTradeListCustomizeVO();
        Calendar cal = Calendar.getInstance();
        int newYear = cal.get(Calendar.YEAR);
        int newMonth = cal.get(Calendar.MONTH) + 1 ;

        // 没有年月的过滤条件，默认当前
        int paramYear = newYear;
        int paramMonth = newMonth;
        if(StringUtils.isNotBlank(tradeYear)){
            paramYear = Integer.parseInt(tradeYear);
        }
        if(StringUtils.isNotBlank(tradeMonth)){
            paramMonth = Integer.parseInt(tradeMonth);
        }

        if(paramYear != newYear){
            customize.setMonth(tradeYear + "年" + tradeMonth + "月");
        } else {
            if(paramMonth != newMonth){
                customize.setMonth(tradeMonth + "月");
            } else {
                customize.setMonth("本月");
            }
        }
        customize.setIsMonth("0");
        list.add(customize);
        return list;
    }
}

