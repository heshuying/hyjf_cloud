/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.response;

import com.hyjf.am.vo.admin.JcCustomerServiceVO;
import com.hyjf.data.bean.jinchuang.*;
import com.hyjf.data.vo.jinchuang.JcDataStatisticsVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoyong
 * @version JcScreenResponse, v0.1 2019/6/19 10:53
 */
public class JcScreenResponse implements Serializable {

    private JcUserConversion userConversion;

    private String jsonObject;

    private List<JcDataStatisticsVO> dataStatisticsList;

    private JcUserAnalysis analysis;

    private String tradeAmount;

    private List<JcRegisterTrade> registerTrades;

    private JcCustomerServiceVO customerService;

    private List<JcUserInterest> interests;

    public JcUserConversion getUserConversion() {
        return userConversion;
    }

    public void setUserConversion(JcUserConversion userConversion) {
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

    public JcUserAnalysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(JcUserAnalysis analysis) {
        this.analysis = analysis;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
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

    public List<JcUserInterest> getInterests() {
        return interests;
    }

    public void setInterests(List<JcUserInterest> interests) {
        this.interests = interests;
    }
}
