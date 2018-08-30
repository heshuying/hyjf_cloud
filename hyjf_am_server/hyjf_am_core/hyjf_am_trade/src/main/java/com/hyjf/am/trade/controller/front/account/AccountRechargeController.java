/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.account;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.account.AccountRechargeCustomizeResponse;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AccountRechargeCustomize;
import com.hyjf.am.trade.service.front.account.AccountRecharge;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountRechargeCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version AccountRechargeController, v0.1 2018/7/17 11:00
 */
@RestController
@RequestMapping("/am-trade/accountrecharge")
public class AccountRechargeController extends BaseController {
    @Autowired
    private AccountRecharge accountRecharge;


    /**
     * 获取充值金额
     * @param list 用户id集合
     * @return
     */
    @RequestMapping("/getrechargeprice")
    public AccountRechargeResponse getRechargePrice(@RequestBody List<Integer> list) {
        AccountRechargeResponse response = new AccountRechargeResponse();
        BigDecimal rechargePrice = accountRecharge.getRechargePrice(list);
        if (rechargePrice != null) {
            response.setRechargePrice(rechargePrice);
        }
        return response;
    }

    /**
     * 根据用户id获取用户充值
     * @param userId
     * @return
     */
    @GetMapping("/getAccountRechargeByUserId/{userId}")
    public com.hyjf.am.response.admin.AccountRechargeResponse getAccountRechargeByUserId(@PathVariable Integer userId) {
        com.hyjf.am.response.admin.AccountRechargeResponse response = new com.hyjf.am.response.admin.AccountRechargeResponse();
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> list = accountRecharge.getAccountRechargeByUserId(userId);
        if (CollectionUtils.isNotEmpty(list)) {
            List<AccountRechargeVO> voList = CommonUtils.convertBeanList(list,AccountRechargeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 资金中心 - 充值管理
     * @param request
     * @return
     * @Author : huanghui
     */
    @RequestMapping(value = "/getAccountRechargeList", method = RequestMethod.POST)
    public AccountRechargeCustomizeResponse getAccountRechargeList(@RequestBody AccountRechargeRequest request){

        AccountRechargeCustomizeResponse rechargeResponse =  new AccountRechargeCustomizeResponse();


        Integer count = this.accountRecharge.getAccountRechargeListCount(request);

        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            if (request.getPageSize() > 0){
                request.setLimitEnd(request.getPageSize());
            }else {
                request.setLimitEnd(paginator.getLimit());
            }
        }

        List<AccountRechargeCustomize> responseList = this.accountRecharge.getAccountRechargeList(request);

        if (!CollectionUtils.isEmpty(responseList)){

            String rechargeStatusKey = RedisConstants.CACHE_PARAM_NAME + CustomConstants.RECHARGE_STATUS;
            String bankTypeKey = RedisConstants.CACHE_PARAM_NAME + CustomConstants.BANK_TYPE;
            String userPropertyKey = RedisConstants.CACHE_PARAM_NAME + CustomConstants.USER_PROPERTY;
            Map<String, String> rechargeStatusMap = RedisUtils.hgetall(rechargeStatusKey);
            Map<String, String> bankTypeMap = RedisUtils.hgetall(bankTypeKey);
            Map<String, String> userPropertyMap = RedisUtils.hgetall(userPropertyKey);

            // 遍历列表从, 从Redis中读取配置信息
            for (AccountRechargeCustomize ac : responseList) {
                ac.setStatus(rechargeStatusMap.get(ac.getStatus()));
                ac.setIsBank(bankTypeMap.get(ac.getIsBank()));
                ac.setUserProperty(userPropertyMap.get(ac.getUserProperty()));
            }

            List<AccountRechargeCustomizeVO> rechargeCustomizeVOList = CommonUtils.convertBeanList(responseList,AccountRechargeCustomizeVO.class);
            rechargeResponse.setResultList(rechargeCustomizeVOList);
            rechargeResponse.setCount(count);
            rechargeResponse.setRtn(Response.SUCCESS);
        }
        return  rechargeResponse;
    }
    
    /**
     * 更新用户充值订单状态
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    @RequestMapping(value = "/modifyRechargeStatus/{userId}/{nid}", method = RequestMethod.GET)
    public boolean modifyRechargeStatus(@PathVariable Integer userId, @PathVariable String nid){
        return this.accountRecharge.updateRechargeStatus(userId, nid);
    }

    /**
     * 确认充值(FIX) 操作
     * @param request
     * @return
     * @Author : huanghui
     */
    @RequestMapping(value = "/updateAccountAfterRecharge", method = RequestMethod.POST)
    public boolean updateAccountAfterRecharge(@RequestBody AccountRechargeRequest request){

        String status = request.getStatus();
        Integer userId = request.getUserId();
        String nid = request.getNid();

        // 确认充值 ; 0表示充值失败
        boolean isAccountUpdate = false;
        if ("1".equals(status)){
            isAccountUpdate = this.accountRecharge.updateAccountAfterRecharge(userId, nid);
        }else {
            isAccountUpdate = this.accountRecharge.updateAccountAfterRechargeFail(userId, nid);
        }

        return isAccountUpdate;
    }
}
