/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.myproject.impl;

import com.hyjf.am.resquest.app.AppProjectContractDetailBeanRequest;
import com.hyjf.am.resquest.app.AppRepayPlanListBeanRequest;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.app.*;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.AppAlreadyRepayListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppTenderCreditRecordListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.MD5;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.myproject.MyInvestProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version MyInvestProjectServiceImpl, v0.1 2018/7/28 14:18
 */
@Service
public class MyInvestProjectServiceImpl extends BaseUserServiceImpl implements MyInvestProjectService {


    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public List<AppAlreadyRepayListCustomizeVO> selectAlreadyRepayList(AssetManageBeanRequest request) {
        return amTradeClient.selectAlreadyRepayList(request);
    }

    @Override
    public int countAlreadyRepayListRecordTotal(AssetManageBeanRequest request) {
        return amTradeClient.selectRepaymentListTotal(request);
    }

    @Override
    public List<AppRepayPlanListCustomizeVO> selectRepayPlanList(AppRepayPlanListBeanRequest params) {
        return amTradeClient.selectRepayPlanList(params);
    }

    @Override
    public int countRepayPlanListRecordTotal(AppRepayPlanListBeanRequest params) {
        return amTradeClient.countRepayPlanListRecordTotal(params);
    }

    @Override
    public BorrowAndInfoVO selectBorrowByBorrowNid(String borrowNid) {
        return amTradeClient.selectBorrowByBorrowNid(borrowNid);
    }

    @Override
    public BorrowStyleVO selectBorrowStyleByStyle(String borrowStyle) {
        return amTradeClient.selectBorrowStyleByStyle(borrowStyle);
    }

    @Override
    public int countRepayRecoverListRecordTotal(AppRepayPlanListBeanRequest params) {
        return amTradeClient.countRepayRecoverListRecordTotal(params);
    }

    @Override
    public List<AppRepayPlanListCustomizeVO> selectRepayRecoverList(AppRepayPlanListBeanRequest params) {
        return amTradeClient.selectRepayRecoverList(params);
    }

    @Override
    public List<AppProjectContractRecoverPlanCustomizeVO> selectProjectContractRecoverPlan(AppProjectContractDetailBeanRequest params) {
        return amTradeClient.selectProjectContractRecoverPlan(params);
    }

    @Override
    public AppProjectContractDetailCustomizeVO selectProjectContractDetail(AppProjectContractDetailBeanRequest params) {
        return amTradeClient.selectProjectContractDetail(params);
    }


    @Override
    public List<AppRepayPlanListCustomizeVO> selectCouponRepayRecoverList(AppRepayPlanListBeanRequest params) {
        return amTradeClient.selectCouponRepayRecoverList(params);
    }


    @Override
    public int countCouponRepayRecoverListRecordTotal(AppRepayPlanListBeanRequest params) {
        return amTradeClient.countCouponRepayRecoverListRecordTotal(params);
    }

    @Override
    public String selectReceivedInterest(AppRepayPlanListBeanRequest params) {
        return amTradeClient.selectReceivedInterest(params);
    }

    @Override
    public AppRepayDetailCustomizeVO selectRepayDetail(Map<String, Object> params) {
        return null;
    }

    @Override
    public AppRepayDetailCustomizeVO selectCouponRepayDetail(Map<String, Object> params) {
        return null;
    }

    @Override
    public AppRepayDetailCustomizeVO selectInvestProjectDetail(Map<String, Object> params) {
        return null;
    }

    @Override
    public AppRepayDetailCustomizeVO selectCouponInvestProjectDetail(Map<String, Object> params) {
        return null;
    }

    @Override
    public AppRepayDetailCustomizeVO selectRepayedProjectDetail(Map<String, Object> params) {
        return null;
    }

    @Override
    public AppRepayDetailCustomizeVO selectCouponRepayedProjectDetail(Map<String, Object> params) {
        return null;
    }

    @Override
    public String getInvestingData(String userId, String host, String page, String pageSize) {
        String SOA_INTERFACE_KEY = systemConfig.getAopAccesskey();
        String GET_INVESTINGDATA = "user/invest/getInvestList.json";

        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + page + pageSize + timestamp
                + SOA_INTERFACE_KEY));

        Map<String, String> params = new HashMap<String, String>();
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // userid
        params.put("userId", userId);
        params.put("host", host);
        params.put("page", page);
        params.put("pageSize", pageSize);

        // 请求路径
        String requestUrl = systemConfig.getApiWebUrl() + GET_INVESTINGDATA;
        // 0:成功，1：失败
        return HttpClientUtils.post(requestUrl, params);
    }

    @Override
    public String getRepayedData(String userId, String host, String page, String pageSize) {
        String SOA_INTERFACE_KEY = systemConfig.getAopAccesskey();
        String GET_REPAYEDDATA = "user/invest/getAlreadyRepayList.json";

        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + page + pageSize + timestamp
                + SOA_INTERFACE_KEY));

        Map<String, String> params = new HashMap<String, String>();
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // userid
        params.put("userId", userId);
        params.put("host", host);
        params.put("page", page);
        params.put("pageSize", pageSize);

        // 请求路径
        String requestUrl = systemConfig.getApiWebUrl() + GET_REPAYEDDATA;

        // 0:成功，1：失败
        return HttpClientUtils.post(requestUrl, params);
    }

    @Override
    public String getRepayData(String userId, String host, String hostContact, String page, String pageSize) {
        String SOA_INTERFACE_KEY = systemConfig.getAopAccesskey();
        String GET_REPAYDATA = "user/invest/getRepayList.json";

        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + page + pageSize + timestamp
                + SOA_INTERFACE_KEY));

        Map<String, String> params = new HashMap<String, String>();
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // userid
        params.put("userId", userId);
        params.put("host", host);
        params.put("hostContact", hostContact);
        params.put("page", page);
        params.put("pageSize", pageSize);

        // 请求路径
        String requestUrl = systemConfig.getApiWebUrl() + GET_REPAYDATA;

        // 0:成功，1：失败
        return HttpClientUtils.post(requestUrl, params);
    }

    @Override
    public CouponConfigVO getCouponConfigByOrderId(String nid) {
        return amTradeClient.getCouponConfig(nid);
    }

    @Override
    public List<WebUserInvestListCustomizeVO> selectUserInvestList(String borrowNid, Integer userId, String nid, int limitStart, int limitEnd) {
        return null;
    }

    @Override
    public boolean isAllowChannelAttorn(Integer userId) {
        return false;
    }

    @Override
    public AccountVO getAccount(Integer userId) {
        return amTradeClient.getAccount(userId);
    }

    @Override
    public int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request) {
        return amTradeClient.selectCurrentHoldObligatoryRightListTotal(request);
    }

    @Override
    public List<CurrentHoldObligatoryRightListCustomizeVO> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest params) {
        return amTradeClient.selectCurrentHoldObligatoryRightList(params);
    }

    @Override
    public int countCreditRecord(AssetManageBeanRequest params) {
        return amTradeClient.countCreditRecordTotal(params);
    }

    @Override
    public List<AppTenderCreditRecordListCustomizeVO> searchCreditRecordList(AssetManageBeanRequest params) {
        return amTradeClient.searchCreditRecordList(params);
    }

    @Override
    public AppProjectDetailCustomizeVO selectProjectDetail(String borrowNid) {
        return amTradeClient.selectProjectDetail(borrowNid);
    }

    @Override
    public UserInfoVO getUserInfoByUserId(Integer userId) {
        return amUserClient.findUserInfoById(userId);
    }

    @Override
    public BorrowCreditVO selectCreditTenderByCreditNid(String creditNid) {
        return amTradeClient.selectCreditTenderByCreditNid(creditNid);
    }

    @Override
    public List<AppTenderCreditRepayPlanListCustomizeVO> selectTenderCreditRepayPlanList(AppRepayPlanListBeanRequest params) {
        return amTradeClient.selectTenderCreditRepayPlanList(params);
    }

    @Override
    public List<AppTenderCreditRepayPlanListCustomizeVO> selectTenderCreditRepayRecoverPlanList(AppRepayPlanListBeanRequest params) {
        return amTradeClient.selectTenderCreditRepayRecoverPlanList(params);
    }

    @Override
    public List<AppTenderToCreditListCustomizeVO> selectTenderToCreditList(Map<String, Object> params) {
        return amTradeClient.selectTenderToCreditList(params);
    }
}
