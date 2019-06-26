package com.hyjf.data.market.service.impl;

import com.hyjf.am.vo.admin.JcCustomerServiceVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.data.bean.jinchuang.JcDataStatistics;
import com.hyjf.data.bean.jinchuang.JcUserConversion;
import com.hyjf.data.bean.jinchuang.JcUserPoint;
import com.hyjf.data.bean.jinchuang.JcRegisterTrade;
import com.hyjf.data.bean.jinchuang.JcTradeAmount;
import com.hyjf.data.bean.jinchuang.JcUserAnalysis;
import com.hyjf.data.client.CsMessageClient;
import com.hyjf.data.mongo.jinchuang.*;
import com.hyjf.data.response.Interest;
import com.hyjf.data.market.service.JinChuangDataService;
import com.hyjf.data.vo.jinchuang.JcDataStatisticsVO;
import com.hyjf.data.vo.jinchuang.JcUserAnalysisVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
            return userPoint.getJo().toString();
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserAnalysis() {
        JcUserAnalysisVO analysisVO = new JcUserAnalysisVO();
        List<Interest> interests;
        Map<String, Object> maps = new HashMap<>();
        List<JcUserAnalysis> analysisList = analysisDao.getUserAnalysis();
        if (!CollectionUtils.isEmpty(analysisList)) {
            interests = CommonUtils.convertBeanList(analysisList, Interest.class);
            maps.put("jcUserAnalysis", analysisVO);
            maps.put("interests", interests);
            return maps;
        }
        return null;
    }

    @Override
    public JcTradeAmount getTradeAmount() {
        return tradeAmountDao.getTradeAmount();
    }

    @Override
    public List<JcRegisterTrade> getRegisterTrade() {
        return registerTradeDao.getRegisterTrade();
    }

    @Override
    public JcCustomerServiceVO getCustomerService() {
        return csMessageClient.getCustomerService();
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
}
