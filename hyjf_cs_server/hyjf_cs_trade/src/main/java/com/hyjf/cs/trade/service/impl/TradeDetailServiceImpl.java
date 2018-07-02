package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.TradeDetailBean;
import com.hyjf.cs.trade.client.AssetManageClient;
import com.hyjf.cs.trade.client.TradeDetailClient;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.TradeDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetailServiceImpl, v0.1 2018/6/27 10:12
 */
@Service
public class TradeDetailServiceImpl extends BaseTradeServiceImpl implements TradeDetailService {
    private static final Logger logger = LoggerFactory.getLogger(AssetManageServiceImpl.class);
    @Autowired
    TradeDetailClient tradeDetailClient;
    @Override
    public List<AccountTradeVO> selectTradeTypes() {
        return tradeDetailClient.selectTradeTypes();
    }

    @Override
    public TradeDetailBean searchUserTradeList(TradeDetailBeanRequest form) {
        TradeDetailBean result=new TradeDetailBean();

        // 统计相应的用户交易明细总数
        int recordTotal = this.tradeDetailClient.countUserTradeRecordTotal(form);

        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        form.setLimitStart(page.getOffset());
        form.setLimitEnd(page.getLimit());

        if (recordTotal > 0) {
            // 查询相应的用户交易明细列表
            List<WebUserTradeListCustomizeVO> userTrades = tradeDetailClient.searchUserTradeList(form);
            result.setTradeList(userTrades);
        } else {
            result.setTradeList(new ArrayList<WebUserTradeListCustomizeVO>());
        }
        result.setPage(page);
        return result;
    }

    @Override
    public TradeDetailBean searchUserRechargeList(TradeDetailBeanRequest form) {
        TradeDetailBean result=new TradeDetailBean();

        // 统计相应的用户充值记录总数
        int recordTotal = this.tradeDetailClient.countUserRechargeRecordTotal(form);

        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        form.setLimitStart(page.getOffset());
        form.setLimitEnd(page.getLimit());

        if (recordTotal > 0) {
            // 查询相应的用户充值记录列表
            List<WebUserRechargeListCustomizeVO> userTrades = tradeDetailClient.searchUserRechargeList(form);
            result.setRechargeList(userTrades);
        } else {
            result.setRechargeList(new ArrayList<WebUserRechargeListCustomizeVO>());
        }
        result.setPage(page);
        return result;
    }

    @Override
    public TradeDetailBean searchUserWithdrawList(TradeDetailBeanRequest form) {
        TradeDetailBean result=new TradeDetailBean();

        // 统计相应的用户提现记录总数
        int recordTotal = this.tradeDetailClient.countUserWithdrawRecordTotal(form);

        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(recordTotal);
        form.setLimitStart(page.getOffset());
        form.setLimitEnd(page.getLimit());

        if (recordTotal > 0) {
            // 查询相应的用户提现记录列表
            List<WebUserWithdrawListCustomizeVO> userTrades = tradeDetailClient.searchUserWithdrawList(form);
            result.setWithdrawList(userTrades);
        } else {
            result.setWithdrawList(new ArrayList<WebUserWithdrawListCustomizeVO>());
        }
        result.setPage(page);
        return result;
    }
}

