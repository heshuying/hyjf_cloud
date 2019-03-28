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
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
    public String updateAccountCheck(Integer userId, String startTime, String endTime, String ip) {
        String payment = "";
        String cardId = "";
        // 获取开户银行卡信息
        BankCardVO bankCardVO = this.amUserClient.getBankCardByUserId(userId);
        if(null != bankCardVO) {
            payment = bankCardVO.getBank() == null ? "":bankCardVO.getBank();
            cardId = bankCardVO.getCardNo();
        }
        return this.amTradeClient.updateAccountCheck(userId, startTime, endTime, ip, payment, cardId);
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
     * 部门查询条件处理
     *
     * @param combotreeListSrch
     * @return
     */
    @Override
    public String[] getDeptId(String[] combotreeListSrch) {
        String[] strIds = null;
        List<String> stringList = new ArrayList<String>();
        if (Validator.isNotNull(combotreeListSrch)) {
            //判断部门选择里是否有父级部门
            for (int i = 0; i < combotreeListSrch.length; i++) {
                String st = combotreeListSrch[i];
                if (st.contains("P")) {
                    //选择的是父级部门的情况下
                    String strDe = st.split("P")[1];
                    if (StringUtils.isNotBlank(strDe)) {
                        stringList = getDeptInfoByDeptId(Integer.parseInt(strDe), stringList);
                    }
                } else {
                    //既选择了父级部门又选择子级菜单的情况下
                    stringList.add(st);
                }
                //其他
                if (("-10086").equals(st)) {
                    stringList.add("0");
                }
            }
        }
        if (null != stringList && stringList.size() > 0) {
            strIds = stringList.toArray(new String[stringList.size()]);
        }
        return strIds;
    }

    /**
     * 根据部门查找是否有子级部门并循环将部门id设置的list中
     */
    private List<String> getDeptInfoByDeptId(int deptId, List<String> keysList) {
        List<OADepartmentCustomizeVO> list = amUserClient.getDeptInfoByDeptId(deptId);
        if (null != list && list.size() > 0) {
            for (OADepartmentCustomizeVO oaDepartmentCustomizeVO : list) {
                keysList.add(oaDepartmentCustomizeVO.getId().toString());
                getDeptInfoByDeptId(oaDepartmentCustomizeVO.getId(), keysList);
            }
        }
        return keysList;
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
        JSONObject joAttr = new JSONObject();
        if (departmentTreeDBList != null && departmentTreeDBList.size() > 0) {
            JSONObject jo = null;
            for (OADepartmentCustomizeVO departmentTreeRecord : departmentTreeDBList) {
                jo = new JSONObject();
                jo.put("title", departmentTreeRecord.getName());
                jo.put("key", UUID.randomUUID());
                jo.put("value", departmentTreeRecord.getId().toString());
                String departmentCd = String.valueOf(departmentTreeRecord.getId());
                String departmentName = String.valueOf(departmentTreeRecord.getName());
                String parentDepartmentCd = String.valueOf(departmentTreeRecord.getParentid());
                if (topParentDepartmentCd.equals(parentDepartmentCd)) {
                    JSONArray array = treeDepartmentList(departmentTreeDBList, map, selectedNode, departmentCd, departmentName);
                    if(null!=array&&array.size()>0){
                        jo.put("value", "P"+departmentTreeRecord.getId().toString());
                    }
                    jo.put("children", array);
                    ja.add(jo);
                }
            }
        }
        return ja;
    }
}
