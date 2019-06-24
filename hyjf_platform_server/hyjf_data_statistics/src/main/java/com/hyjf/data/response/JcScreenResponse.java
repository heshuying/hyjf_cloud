/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.response;

import com.hyjf.am.vo.admin.JcCustomerServiceVO;
import com.hyjf.data.bean.jinchuang.JcRegisterTrade;
import com.hyjf.data.bean.jinchuang.JcTradeAmount;
import com.hyjf.data.vo.jinchuang.JcDataStatisticsVO;
import com.hyjf.data.vo.jinchuang.JcUserAnalysisVO;
import com.hyjf.data.vo.jinchuang.JcUserConversionVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoyong
 * @version JcScreenResponse, v0.1 2019/6/19 10:53
 */
public class JcScreenResponse implements Serializable {

    private JcUserConversionVO userConversion;

    private String jsonObject;

    private List<JcDataStatisticsVO> dataStatisticsList;

    private List<Interest> interests;

    private JcUserAnalysisVO analysis;

    private JcTradeAmount tradeAmount;

    private List<JcRegisterTrade> registerTrades;

    JcCustomerServiceVO customerService;

    public JcUserConversionVO getUserConversion() {
        return userConversion;
    }

    public void setUserConversion(JcUserConversionVO userConversion) {
        this.userConversion = userConversion;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public List<JcDataStatisticsVO> getDataStatisticsList() {
        return dataStatisticsList;
    }

    public void setDataStatisticsList(List<JcDataStatisticsVO> dataStatisticsList) {
        this.dataStatisticsList = dataStatisticsList;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public JcUserAnalysisVO getAnalysis() {
        return analysis;
    }

    public void setAnalysis(JcUserAnalysisVO analysis) {
        this.analysis = analysis;
    }

    public JcTradeAmount getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(JcTradeAmount tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public List<JcRegisterTrade> getRegisterTrades() {
        return registerTrades;
    }

    public void setRegisterTrades(List<JcRegisterTrade> registerTrades) {
        this.registerTrades = registerTrades;
    }

    public JcCustomerServiceVO getCustomerService() {
        return customerService;
    }

    public void setCustomerService(JcCustomerServiceVO customerService) {
        this.customerService = customerService;
    }
}
