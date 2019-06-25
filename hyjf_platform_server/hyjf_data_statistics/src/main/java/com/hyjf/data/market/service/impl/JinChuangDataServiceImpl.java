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
import com.hyjf.data.vo.jinchuang.JcUserConversionVO;
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
    public JcUserConversionVO getUserConversion() {
        JcUserConversion userConversion = userConversionDao.getUserConversion();
        JcUserConversionVO conversionVO = new JcUserConversionVO();
        if (userConversion != null) {
//            conversionVO.setRegisterNum("100%");
//            conversionVO.setOpenAccountNum(userConversion.getOpenAccountNum() / userConversion.getRegisterNum() + "%");
//            conversionVO.setRechargeNum(userConversion.getRechargeNum() / userConversion.getRegisterNum() + "%");
//            conversionVO.setInvestNum(userConversion.getInvestNum() / userConversion.getRegisterNum() + "%");
//            conversionVO.setReInvestNum(userConversion.getReInvestNum() / userConversion.getRegisterNum() + "%");
            return conversionVO;
        }
        return new JcUserConversionVO();
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
        Integer primaryInvest = 0;
        Integer middleInvest = 0;
        Integer seniorInvest = 0;
        Integer highestInvest = 0;
        Integer oneInvest = 0;
        Integer twoInvest = 0;
        Integer threeInvest = 0;
        Integer fourInvest = 0;
        Integer fiveInvest = 0;
        Integer maleCount = 0;
        Integer femaleCount = 0;
        if (!CollectionUtils.isEmpty(analysisList)) {
            for (JcUserAnalysis analysis : analysisList) {
                primaryInvest += analysis.getPrimaryInvest();
                middleInvest += analysis.getMiddleInvest();
                seniorInvest += analysis.getSeniorInvest();
                highestInvest += analysis.getHighestInvest();
                oneInvest += analysis.getOneInvest();
                twoInvest += analysis.getTwoInvest();
                threeInvest += analysis.getThreeInvest();
                fourInvest += analysis.getFourInvest();
                fiveInvest += analysis.getFiveInvest();
                maleCount += analysis.getMaleCount();
                femaleCount += analysis.getFemaleCount();
            }
            Integer investCount = primaryInvest + middleInvest + seniorInvest + highestInvest;
            Integer sumInvest = oneInvest + twoInvest + threeInvest + fourInvest + fiveInvest;
            Integer person = maleCount + femaleCount;
            analysisVO.setPrimaryInvest(primaryInvest / investCount + "%");
            analysisVO.setMiddleInvest(middleInvest / investCount + "%");
            analysisVO.setSeniorInvest(seniorInvest / investCount + "%");
            analysisVO.setHighestInvest(highestInvest / investCount + "%");
            analysisVO.setOneInvest(oneInvest / sumInvest + "%");
            analysisVO.setTwoInvest(twoInvest / sumInvest + "%");
            analysisVO.setThreeInvest(threeInvest / sumInvest + "%");
            analysisVO.setFourInvest(fourInvest / sumInvest + "%");
            analysisVO.setFiveInvest(fiveInvest / sumInvest + "%");
            analysisVO.setMaleCount(maleCount / person + "%");
            analysisVO.setFemaleCount(femaleCount / person + "%");
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
