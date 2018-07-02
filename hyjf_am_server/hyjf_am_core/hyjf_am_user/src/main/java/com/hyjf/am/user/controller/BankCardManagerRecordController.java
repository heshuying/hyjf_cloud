/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankCardManagerResponse;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.user.dao.model.customize.BankcardManagerCustomize;
import com.hyjf.am.user.service.BankCardManagerRecordService;
import com.hyjf.am.vo.user.BankcardManagerVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-銀行卡管理
 */

@RestController
@RequestMapping("/am-user/bankCardManager")
public class BankCardManagerRecordController extends BaseController{
    @Autowired
    private BankCardManagerRecordService bankCardManagerServiceService;

    /**
     * 根据筛选条件查找(汇付银行卡管理列表显示)
     *
     * @param request
     * @return
     */
    @RequestMapping("/bankcardlistHF")
    public BankCardManagerResponse findBankcardlistHF(@RequestBody @Valid BankCardManagerRequest request) {
        logger.info("---findBankcardlistHF by param---  " + JSONObject.toJSON(request));
        BankCardManagerResponse response = new BankCardManagerResponse();
        Map<String,Object> mapParam = paramSet(request);
        int usesrCount = bankCardManagerServiceService.countUserRecord(mapParam);
        Paginator paginator = new Paginator(request.getPaginatorPage(), usesrCount,request.getLimit());
        List<BankcardManagerCustomize> bankcardManagerCustomizeList = bankCardManagerServiceService.selectBankCardList(mapParam,paginator.getOffset(), paginator.getLimit());
        if(usesrCount>0){
            if (!CollectionUtils.isEmpty(bankcardManagerCustomizeList)) {
                List<BankcardManagerVO> bankcardManager = CommonUtils.convertBeanList(bankcardManagerCustomizeList, BankcardManagerVO.class);
                response.setResultList(bankcardManager);
                response.setCount(usesrCount);
                response.setRtn(Response.SUCCESS);//代表成功
            }
        }
        return response;
    }

    /**
     * 获取列表总数
     *
     * @param request
     * @return
     */
    @RequestMapping("/countBankcardlistHF")
    public UserManagerResponse countBankcardlistHF(@RequestBody @Valid BankCardManagerRequest request) {
        logger.info("---countBankcardlistHF by param---  " + JSONObject.toJSON(request));
        UserManagerResponse response = new UserManagerResponse();
        Map<String,Object> mapParam = paramSet(request);
        int usesrCount =  bankCardManagerServiceService.countUserRecord(mapParam);
        response.setCount(usesrCount);
        return response;
    }

    /**
     * 根据筛选条件查找(江西银行卡管理列表显示)
     *
     * @param request
     * @return
     */
    @RequestMapping("/bankcardlistJX")
    public BankCardManagerResponse bankcardlistJX(@RequestBody @Valid BankCardManagerRequest request) {
        logger.info("---bankcardlistJX by param---  " + JSONObject.toJSON(request));
        BankCardManagerResponse response = new BankCardManagerResponse();
        Map<String,Object> mapParam = paramSet(request);
        int usesrCount = bankCardManagerServiceService.countRecordTotalNew(mapParam);
        Paginator paginator = new Paginator(request.getPaginatorPage(), usesrCount,request.getLimit());
        List<BankcardManagerCustomize> bankcardManagerCustomizeList = bankCardManagerServiceService.selectNewBankCardList(mapParam,paginator.getOffset(), paginator.getLimit());
        if(usesrCount>0){
            if (!CollectionUtils.isEmpty(bankcardManagerCustomizeList)) {
                List<BankcardManagerVO> bankcardManager = CommonUtils.convertBeanList(bankcardManagerCustomizeList, BankcardManagerVO.class);
                response.setResultList(bankcardManager);
                response.setCount(usesrCount);
                response.setRtn(Response.SUCCESS);//代表成功
            }
        }
        return response;
    }
    /**
     * 获取列表总数
     *
     * @param request
     * @return
     */
    @RequestMapping("/countBankcardlistJX")
    public UserManagerResponse countBankcardlistJX(@RequestBody @Valid BankCardManagerRequest request) {
        logger.info("---countBankcardlistJX by param---  " + JSONObject.toJSON(request));
        UserManagerResponse response = new UserManagerResponse();
        Map<String,Object> mapParam = paramSet(request);
        int usesrCount =  bankCardManagerServiceService.countRecordTotalNew(mapParam);
        response.setCount(usesrCount);
        return response;
    }

    /**
     * 查询条件设置
     *
     * @param request
     * @return
     */
    private Map<String, Object> paramSet(BankCardManagerRequest request) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("userName", request.getUserName());
        mapParam.put("bank", request.getBank());
        mapParam.put("account", request.getAccount());
        mapParam.put("cardProperty", request.getCardProperty());
        mapParam.put("cardType", request.getCardType());
        mapParam.put("addTimeStart", request.getAddTimeStart());
        mapParam.put("addTimeEnd", request.getAddTimeEnd());
        mapParam.put("mobile",request.getMobile());
        mapParam.put("realName",request.getRealName());
        return mapParam;
    }

}
