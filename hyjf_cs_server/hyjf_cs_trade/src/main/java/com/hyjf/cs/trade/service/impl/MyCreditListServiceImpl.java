/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.MyCreditListQueryResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyCreditListRequest;
import com.hyjf.am.vo.statistics.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.CreditPageVO;
import com.hyjf.am.vo.trade.ExpectCreditFeeVO;
import com.hyjf.am.vo.trade.TenderCreditCustomizeVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeCsVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.CreditDetailsRequestBean;
import com.hyjf.cs.trade.bean.CreditResultBean;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.CouponService;
import com.hyjf.cs.trade.service.MyCreditListService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 资产管理  我要债转相关
 * @Author sunss
 * @Date 2018/6/30 10:18
 */
@Service
public class MyCreditListServiceImpl extends BaseTradeServiceImpl implements MyCreditListService {

    @Autowired
    private CreditClient creditClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmMongoClient amMongoClient;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private BorrowClient borrowClient;

    @Autowired
    AmBorrowRepayPlanClient amBorrowRepayPlanClient;


    /**
     * 我要债转列表页 获取参数
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult getCreditListData(MyCreditListRequest request, Integer userId) {
        // 判断用户所处的渠道如果不允许债转，可债转金额为0  start
        if (!creditClient.isAllowChannelAttorn(userId)) {
            logger.info("判断用户所处渠道不允许债转,可债转金额0....userId is:{}", userId);
            throw new CheckException(MsgEnum.ERR_ALLOW_CHANNEL_ATTORN);
        }
        AppChannelStatisticsDetailVO appChannelStatisticsDetails = amMongoClient.getAppChannelStatisticsDetailByUserId(userId);
        if (appChannelStatisticsDetails != null) {
            UtmPlatVO utmPlat = amUserClient.selectUtmPlatByUtmId(userId);
            if (utmPlat != null && utmPlat.getAttornFlag() == 0) {
                logger.info("判断用户所处渠道不允许债转,可债转金额0....userId is:{}", userId);
                throw new CheckException(MsgEnum.ERR_ALLOW_CHANNEL_ATTORN);
            }
        }
        // 获取可债转金额   转让中本金   累计已转让本金
        CreditPageVO moneys = creditClient.selectCreditPageMoneyTotal(userId);
        AccountVO account = accountClient.getAccountByUserId(userId);
        moneys.setHoldMoneyTotal(account.getBankAwaitCapital());
        WebResult result =  new WebResult();
        result.setData(moneys);
        return result;
    }

    /**
     * 我要债转列表页 获取列表
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult getCreditList(MyCreditListQueryRequest request, Integer userId) {
        if (!creditClient.isAllowChannelAttorn(userId)) {
            logger.info("判断用户所处渠道不允许债转,可债转金额0....userId is:{}", userId);
            throw new CheckException(MsgEnum.ERR_ALLOW_CHANNEL_ATTORN);
        }
        // 初始化分页参数，并组合到请求参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        MyCreditListQueryResponse res = creditClient.countMyCreditList(request);
        WebResult webResult = new WebResult();
        if (!Response.isSuccess(res)) {
            logger.error("查询count异常");
            throw new RuntimeException("查询count异常");
        }
        int count = res.getCount();
        page.setTotal(count);
        webResult.setData(new ArrayList<>());
        if (count > 0) {
            List<TenderCreditCustomizeVO> result = new ArrayList<>();
            MyCreditListQueryResponse dataResponse = creditClient.searchMyCreditList(request);
            if (!Response.isSuccess(dataResponse)) {
                logger.error("查询list数据异常");
                throw new RuntimeException("查询list数据异常");
            }
            result = CommonUtils.convertBeanList(dataResponse.getResultList(), TenderCreditCustomizeVO.class);
            webResult.setData(result);
        }
        webResult.setPage(page);
        return webResult;
    }

    /**
     * 用户中心查询投资可债转详情
     *
     * @param request
     * @param userId
     * @return
     */
    @Override
    public WebResult tenderToCreditDetail(CreditDetailsRequestBean request, Integer userId) {
        CreditResultBean creditResultBean = new CreditResultBean();
        WebResult webResult = new WebResult();
        UserVO user = amUserClient.findUserById(userId);
        creditResultBean.setMobile(user.getMobile());
        Integer nowTime = GetDate.getNowTime10();
        if (StringUtils.isEmpty(request.getBorrowNid()) || StringUtils.isEmpty(request.getTenderNid())) {
            // 转让失败,无法获取借款和投资编号
            throw  new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        TenderCreditCustomizeVO tenderToCreditDetail = creditClient.selectTenderToCreditDetail(userId, request.getBorrowNid(),
                request.getTenderNid());
        if(tenderToCreditDetail==null){
            // 获取债转数据为空
            throw  new CheckException(MsgEnum.ERROR_CREDIT_PARAM);
        }
        int lastdays = 0;
        String borrowNid = tenderToCreditDetail.getBorrowNid();
        // 根据borrowNid判断是否分期
        BorrowVO borrow = borrowClient.selectBorrowByNid(borrowNid);
        String borrowStyle = borrow.getBorrowStyle();
        if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
            try {
                lastdays = GetDate.daysBetween(GetDate.getDateTimeMyTimeInMillis(nowTime),
                        GetDate.getDateTimeMyTimeInMillis(Integer.parseInt(tenderToCreditDetail.getRecoverTime())));
            } catch (Exception e) {
                // 债转数据错误
                throw  new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
            }
        }

        // 等额本息和等额本金和先息后本
        if (borrowStyle.equals(CalculatesUtil.STYLE_MONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
            List<BorrowRepayPlanVO> list = this.amBorrowRepayPlanClient.selectBorrowRepayPlan(borrowNid, borrow.getBorrowPeriod());
            if (list != null && list.size() > 0) {
                try {
                    lastdays = GetDate.daysBetween(GetDate.getDateTimeMyTimeInMillis(nowTime), GetDate.getDateTimeMyTimeInMillis(list.get(0).getRepayTime()));
                } catch (Exception e) {
                    // 债转数据错误
                    throw  new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
                }
            }
        }

        // 债转详细预计服务费计算
        ExpectCreditFeeVO resultMap = creditClient.selectExpectCreditFee(request.getBorrowNid(), request.getTenderNid());
        tenderToCreditDetail.setLastDays(lastdays + "");
        creditResultBean.setData(tenderToCreditDetail);
        creditResultBean.setCalData(resultMap);

        webResult.setData(creditResultBean);
        return webResult;
    }
}
