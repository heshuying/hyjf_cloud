/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.controller.LoginController;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.am.response.admin.OADepartmentResponse;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.http.HtmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageServiceImpl, v0.1 2018/6/29 11:54
 */
@Service
public class BankAccountManageServiceImpl extends BaseServiceImpl implements BankAccountManageService {

    @Value("${hyjf.bank.instcode}")
    private String bankInstCode;

    @Value("${hyjf.bank.bankcode}")
    private String bankBankCode;

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 账户管理页面查询列表
     *
     * @param bankAccountManageRequest
     * @return
     */
    @Override
    public List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        return this.amTradeClient.queryAccountInfos(bankAccountManageRequest);
    }

    /**
     * 资金明细（列表）
     *
     * @param bankAccountManageRequest
     * @return
     */
    @Override
    public List<BankAccountManageCustomizeVO> queryAccountDetails(BankAccountManageRequest bankAccountManageRequest) {
        return this.amTradeClient.queryAccountDetails(bankAccountManageRequest);
    }

    /**
     * 查询用户是否开户
     *
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        return this.amUserClient.getBankOpenAccount(userId);
    }

    /**
     * 更新用户账户信息
     *
     * @param userId
     * @param balance
     * @param frost
     * @return
     */
    @Override
    public Integer updateAccount(Integer userId, BigDecimal balance, BigDecimal frost) {
        AccountVO accountVO = new AccountVO();
        accountVO.setUserId(userId);
        accountVO.setBalance(balance);
        accountVO.setFrost(frost);
        return this.amTradeClient.updateAccountManage(accountVO);
    }

    /**
     * 线下充值对账
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public String updateAccountCheck(Integer userId, String startTime, String endTime) {
        return this.amTradeClient.updateAccountCheck(userId, startTime, endTime);
    }

    /**
     * 银行账户管理页面数据条数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectAccountInfoCount(BankAccountManageRequest request) {
        return this.amTradeClient.queryAccountCount(request);
    }

    /**
     * 获取部门列表
     *
     * @return
     */
    @Override
    public List<OADepartmentCustomizeVO> queryDepartmentInfo() {
        // 实际未传任何参数
        HjhCommissionRequest form = new HjhCommissionRequest();
        OADepartmentResponse response = amTradeClient.getCrmDepartmentList(form);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取部门列表
     *
     * @param list
     * @return
     */
    @Override
    public JSONArray getCrmDepartmentList(String[] list) {
        List<OADepartmentCustomizeVO> departmentList = amUserClient.queryDepartmentInfo(null);

        Map<String, String> map = new HashMap<String, String>();
        if (departmentList != null && departmentList.size() > 0) {
            for (OADepartmentCustomizeVO oaDepartment : departmentList) {
                map.put(String.valueOf(oaDepartment.getId()), HtmlUtil.unescape(oaDepartment.getName()));
            }
        }
        return treeDepartmentList(departmentList, map, list, "0", "");
    }

    /**
     * 根据用户id获取用户账户信息
     *
     * @param userId
     * @return
     */
    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        return this.amTradeClient.getAccountByUserId(userId);
    }

    /**
     * 部门树形结构
     *
     * @param departmentTreeDBList
     * @param map
     * @param selectedNode
     * @param topParentDepartmentCd
     * @param topParentDepartmentName
     * @return
     */
    private JSONArray treeDepartmentList(List<OADepartmentCustomizeVO> departmentTreeDBList, Map<String, String> map, String[] selectedNode, String topParentDepartmentCd,
                                         String topParentDepartmentName) {
        JSONArray ja = new JSONArray();
        if (departmentTreeDBList != null && departmentTreeDBList.size() > 0) {
            JSONObject jo = null;
            for (OADepartmentCustomizeVO departmentTreeRecord : departmentTreeDBList) {
                jo = new JSONObject();

                jo.put("value", departmentTreeRecord.getId().toString());
                jo.put("title", departmentTreeRecord.getName());
                jo.put("key", UUID.randomUUID());

                String departmentCd = String.valueOf(departmentTreeRecord.getId());
                String departmentName = String.valueOf(departmentTreeRecord.getName());
                String parentDepartmentCd = String.valueOf(departmentTreeRecord.getParentid());
                if (topParentDepartmentCd.equals(parentDepartmentCd)) {
                    JSONArray array = treeDepartmentList(departmentTreeDBList, map, selectedNode, departmentCd, departmentName);
                    jo.put("children", array);
                    ja.add(jo);
                }
            }
        }
        return ja;
    }
}
