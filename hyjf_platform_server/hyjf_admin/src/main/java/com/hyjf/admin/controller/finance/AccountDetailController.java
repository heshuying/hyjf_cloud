/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AccountdetailController, v0.1 2018/6/29 13:38
 * 后台管理系统，资金中心->资金明细
 */
@Api(value = "资金明细")
@RestController
@RequestMapping("/hyjf-admin/accountDetail")
public class AccountDetailController extends BaseController{

    @Autowired
    AccountDetailService accountDetailService;
    @Autowired
    UserCenterService userCenterService;
    @ApiOperation(value = "资金明细", notes = "资金明细页面初始化")
    @PostMapping(value = "/accountDetailInit")
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject =null;
       // List<AccountDetailVO> listAccountDetail =  accountDetailService.findAccountDetailList();

        return jsonObject;
    }
    @ApiOperation(value = "资金明细", notes = "资金明细页面初始化")
    @PostMapping(value = "/queryAccountDetail")
    @ResponseBody
    public JSONObject queryAccountDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject jsonObject =null;
        AccountDetailRequest requestAccountDetail = setParam(map);
        AccountDetailResponse accountDetailResponse =  accountDetailService.findAccountDetailList(requestAccountDetail);
        List<AccountDetailVO> listAccountDtaileShow = new ArrayList<AccountDetailVO>();
        if(null!=accountDetailResponse){
            List<AccountDetailVO> listAccountDetail = accountDetailResponse.getResultList();
            String  recordCount = accountDetailResponse.getRecordTotal();
            if(null!=listAccountDetail&&listAccountDetail.size()>0){
                for(AccountDetailVO accountDetailVO :listAccountDetail){
                    //根据用户id获取用户信息
                    UserVO userVO =userCenterService.selectUserByUserId(accountDetailVO.getUserId().toString());
                    //根据用户id获取推荐人信息
                    SpreadsUserVO spreadsUserVO = userCenterService.selectSpreadsUsersByUserId(String.valueOf(accountDetailVO.getUserId()));
                    if(null!=userVO){
                        accountDetailVO.setUsername(userVO.getUsername());
                    }
                    if(null!=spreadsUserVO){
                        //设置推荐人id
                        accountDetailVO.setReferrerId(spreadsUserVO.getSpreadsUserId().toString());
                        //根据推荐人id查找姓名
                        UserVO userSpreads =userCenterService.selectUserByUserId(spreadsUserVO.getSpreadsUserId().toString());
                        accountDetailVO.setReferrerName(userSpreads.getUsername());
                        accountDetailVO.setReferrerId(userSpreads.getUserId().toString());
                    }
                    //根据推荐人姓名筛选
                    if(StringUtils.isNotBlank(requestAccountDetail.getReferrerName().toString())){
                        String referrerNameSearch = requestAccountDetail.getReferrerName().toString();
                        UserVO userReferrerSearch =userCenterService.selectUserByRecommendName(referrerNameSearch);
                        String userRefer = userReferrerSearch.getUserId().toString();
                        if(userRefer.equals(accountDetailVO.getReferrerId())){
                            listAccountDtaileShow.add(accountDetailVO);
                        }
                    }else{
                        listAccountDtaileShow.add(accountDetailVO);
                    }
                }
            }
            if(null!=listAccountDtaileShow){
                jsonObject = this.success();
                jsonObject.put("recordTotle",recordCount);
                jsonObject.put("record",listAccountDtaileShow);

            }else {
                jsonObject= this.fail("暂无符合条件数据");
            }
        }
        return jsonObject;
    }


    @ApiOperation(value = "资金明细", notes = "交易明细修复")
    @PostMapping(value = "/accountdetailDataRepair")
    @ResponseBody
    public JSONObject accountdetailDataRepair(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map){

        return null;
    }
    /**
     * 设置参数
     * @param mapParam
     * @return
     */
    private AccountDetailRequest setParam(Map<String, Object> mapParam){
        AccountDetailRequest request = new AccountDetailRequest();
        if (null != mapParam && mapParam.size() > 0) {
            if (mapParam.containsKey("userName")) {
                request.setUsername(mapParam.get("userName").toString());
            }
            if (mapParam.containsKey("referrerName")) {
                request.setReferrerName(mapParam.get("referrerName").toString());
            }
            if (mapParam.containsKey("nid")) {
                request.setNid(mapParam.get("nid").toString());
            }
            if (mapParam.containsKey("accountId")) {
                request.setAccountId(mapParam.get("accountId").toString());
            }
            if (mapParam.containsKey("seqNo")) {
                request.setSeqNo(mapParam.get("seqNo").toString());
            }
            if (mapParam.containsKey("isBank")) {
                request.setIsBank(mapParam.get("isBank").toString());
            }
            if (mapParam.containsKey("checkStatus")) {
                request.setCheckStatus(mapParam.get("checkStatus").toString());
            }
            if (mapParam.containsKey("tradeStatus")) {
                request.setTradeStatus(mapParam.get("tradeStatus").toString());
            }
            if (mapParam.containsKey("typeSearch")) {
                request.setTypeSearch(mapParam.get("typeSearch").toString());
            }
            if (mapParam.containsKey("tradeTypeSearch")) {
                request.setTradeTypeSearch(mapParam.get("tradeTypeSearch").toString());
            }
            if (mapParam.containsKey("startDate")) {
                request.setStartDate(mapParam.get("startDate").toString());
            }
            if (mapParam.containsKey("endDate")) {
                request.setEndDate(mapParam.get("endDate").toString());
            }
            if (mapParam.containsKey("remarkSrch")) {
                request.setRemarkSrch(mapParam.get("remarkSrch").toString());
            }
            if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                request.setPageSize(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return request;
    }
}
