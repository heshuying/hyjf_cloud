/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminAccountDetailCustomize;
import com.hyjf.am.trade.service.UserService;
import com.hyjf.am.trade.service.admin.AccountDetailService;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AdminAccountDetailController, v0.1 2018/6/29 13:53
 */
@RestController
@RequestMapping("/am-trade/adminAccountDetail")
public class AdminAccountDetailController {

    @Autowired
    AccountDetailService accountDetailService;
    @Autowired
    UserService userService;
    private static Logger logger = LoggerFactory.getLogger(AdminAccountDetailController.class);

    @RequestMapping(value = "/accountDetailList", method = RequestMethod.POST)
    public AccountDetailResponse accountDetailList(@RequestBody @Valid AccountDetailRequest request){
        logger.info("---accountDetailList by param---  " + JSONObject.toJSON(request));
        AccountDetailResponse response = new AccountDetailResponse();
        String returnCode = Response.FAIL;
        Map<String,Object> mapParam = paramSet(request);
        int intCountAccountDetail = accountDetailService.countAccountDetail(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), intCountAccountDetail,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), intCountAccountDetail);
        }
        mapParam.put("limitStart",paginator.getOffset());
        mapParam.put("limitEnd",paginator.getLimit());
        List<AdminAccountDetailCustomize> userManagerCustomizeList = accountDetailService.queryAccountDetails(mapParam);
        if(intCountAccountDetail>0){
            if (!CollectionUtils.isEmpty(userManagerCustomizeList)) {
                List<AccountDetailVO> userVoList = CommonUtils.convertBeanList(userManagerCustomizeList, AccountDetailVO.class);
                response.setResultList(userVoList);
                response.setRecordTotal(String.valueOf(intCountAccountDetail));
                returnCode = Response.SUCCESS;
            }
        }
        response.setRtn(returnCode);//代表成功
        return response;
    }

    /**
     * 查询条件设置
     *
     * @param userRequest
     * @return
     */
    private Map<String, Object> paramSet(AccountDetailRequest userRequest) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        //
        mapParam.put("userId",userRequest.getUserId());
        //
        mapParam.put("userName", userRequest.getUsername());
        mapParam.put("referrerName", userRequest.getReferrerName());
        mapParam.put("nid", userRequest.getNid());
        mapParam.put("accountId", userRequest.getAccountId());
        mapParam.put("seqNo", userRequest.getSeqNo());
        mapParam.put("isBank", userRequest.getIsBank());
        mapParam.put("checkStatus", userRequest.getCheckStatus());
        mapParam.put("tradeStatus", userRequest.getTradeStatus());
        mapParam.put("typeSearch", userRequest.getTypeSearch());
        mapParam.put("tradeTypeSearch", userRequest.getTradeTypeSearch());
        mapParam.put("startDate", userRequest.getStartDate());
        mapParam.put("endDate", userRequest.getEndDate());
        mapParam.put("remarkSrch", userRequest.getRemark());
        mapParam.put("limit",userRequest.getPageSize());
        return mapParam;
    }

    //查询出20170120还款后,交易明细有问题的用户ID
/*    @RequestMapping(value = "/accountDetailList", method = RequestMethod.POST)
    public AdminAccountDetailDataRepairResponse accountdetailDataRepair(){
        AdminAccountDetailDataRepairResponse repairResponse = new AdminAccountDetailDataRepairResponse();

        return repairResponse;
    }*/
}
