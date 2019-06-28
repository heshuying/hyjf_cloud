package com.hyjf.data.market.service.impl;

import com.hyjf.am.vo.admin.JcCustomerServiceVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.data.bean.jinchuang.*;
import com.hyjf.data.client.CsMessageClient;
import com.hyjf.data.mongo.jinchuang.*;
import com.hyjf.data.response.Interest;
import com.hyjf.data.market.service.JinChuangDataService;
import com.hyjf.data.vo.jinchuang.JcDataStatisticsVO;
import com.hyjf.data.vo.jinchuang.JcUserAnalysisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author:yangchangwei
 * @Date:2019/6/18
 * @Description:
 */
@Service
public class JinChuangDataServiceImpl implements JinChuangDataService {

    @Autowired
    private JcUserConversionDao userConversionDao;
    @Autowired
    private JcUserPointDao userPointDao;
    @Autowired
    private JcDataStatisticsDao statisticsDao;
    @Autowired
    private JcUserAnalysisDao analysisDao;
    @Autowired
    private JcTradeAmountDao tradeAmountDao;
    @Autowired
    private JcRegisterTradeDao registerTradeDao;
    @Autowired
    private CsMessageClient csMessageClient;
    @Autowired
    private JcUserInterestDao userInterestDao;
    @Autowired
    private TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;

    @Override
    public JcUserConversion getUserConversion() {
        JcUserConversion userConversion = userConversionDao.getUserConversion();
        if (userConversion != null) {
            return userConversion;
        }
        return new JcUserConversion();
    }

    @Override
    public String getUserPoint() {
        JcUserPoint userPoint = userPointDao.getUserPoint();
        if (userPoint != null) {
            return userPoint.getJo();
        }
        return null;
    }

    @Override
    public JcUserAnalysis getUserAnalysis() {
        JcUserAnalysis analysis = analysisDao.getUserAnalysis();
        if (analysis != null) {
            return analysis;
        }
        return new JcUserAnalysis();
    }

    @Override
    public JcTradeAmount getTradeAmount() {
        JcTradeAmount tradeAmount = tradeAmountDao.getTradeAmount();
        if (tradeAmount != null) {
            return tradeAmount;
        }
        return new JcTradeAmount();
    }

    @Override
    public List<JcRegisterTrade> getRegisterTrade() {
        List<JcRegisterTrade> registerTrades = registerTradeDao.getRegisterTrade();
        if (!CollectionUtils.isEmpty(registerTrades)) {
            return registerTrades;
        }
        return new ArrayList<>();
    }

    @Override
    public JcCustomerServiceVO getCustomerService() {
        JcCustomerServiceVO customerServiceVO = csMessageClient.getCustomerService();
        if (customerServiceVO != null) {
            return customerServiceVO;
        }
        return new JcCustomerServiceVO();
    }

    @Override
    public List<JcUserInterest> getUserInterest() {
        List<JcUserInterest> interests = userInterestDao.getUserInterest();
        if (!CollectionUtils.isEmpty(interests)) {
            return interests;
        }
        return new ArrayList<>();
    }

    @Override
    public List<JcDataStatisticsVO> getDataStatistics() {
        List<JcDataStatistics> dataStatisticsList = statisticsDao.getDataStatistics();
        List<JcDataStatisticsVO> statisticsVOS;
        if (!CollectionUtils.isEmpty(dataStatisticsList)) {
            statisticsVOS = CommonUtils.convertBeanList(dataStatisticsList, JcDataStatisticsVO.class);
            return statisticsVOS;
        }
        return new ArrayList<>();
    }

    /**
     * 获取实时收益规模 add by yangchangwei
     * @return
     */
    private String getTotalInvestAmount() {

        DecimalFormat df = new DecimalFormat("#,##0");
        TotalInvestAndInterestEntity entity = totalInvestAndInterestMongoDao.findOne(new Query());
        if (entity != null) {
            df.format(entity.getTotalInvestAmount().setScale(0, BigDecimal.ROUND_DOWN));
        }
        return null;
    }
}
