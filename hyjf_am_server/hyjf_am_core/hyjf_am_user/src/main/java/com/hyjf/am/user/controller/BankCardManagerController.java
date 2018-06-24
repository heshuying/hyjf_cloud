/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.*;
import com.hyjf.am.user.service.BankCardManagerService;
import com.hyjf.am.user.service.UserManagerService;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-銀行卡管理
 */

@RestController
@RequestMapping("/am-user/bankCardManager")
public class BankCardManagerController {
    @Autowired
    private BankCardManagerService bankCardManagerServiceService;
    private static Logger logger = LoggerFactory.getLogger(BankCardManagerController.class);

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
        List<BankcardManagerCustomize> bankcardManagerCustomizeList = bankCardManagerServiceService.selectBankCardList(request);
        int usesrCount = bankCardManagerServiceService.countUserRecord(request);
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
        int usesrCount =  bankCardManagerServiceService.countUserRecord(request);
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
        List<BankcardManagerCustomize> bankcardManagerCustomizeList = bankCardManagerServiceService.selectNewBankCardList(request);
        int usesrCount = bankCardManagerServiceService.countRecordTotalNew(request);
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
        int usesrCount =  bankCardManagerServiceService.countRecordTotalNew(request);
        response.setCount(usesrCount);
        return response;
    }
}
