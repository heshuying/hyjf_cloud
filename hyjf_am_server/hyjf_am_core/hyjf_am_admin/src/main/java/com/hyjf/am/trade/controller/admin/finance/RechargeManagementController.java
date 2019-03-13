package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.account.AccountRechargeCustomizeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.customize.RechargeManagementCustomize;
import com.hyjf.am.trade.service.admin.finance.RechargeManagementService;
import com.hyjf.am.vo.trade.account.AccountRechargeCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 充值管理控制器
 * @Author : huanghui
 */
@RestController
@RequestMapping("/am-trade/rechargemanagement")
public class RechargeManagementController extends BaseController {

    Logger logger = LoggerFactory.getLogger(RechargeManagementController.class);

    @Autowired
    private RechargeManagementService rechargeManagementService;
    /**
     * 资金中心 - 充值管理
     * @param request
     * @return
     * @Author : huanghui
     */
    @RequestMapping(value = "/getAccountRechargeList", method = RequestMethod.POST)
    public AccountRechargeCustomizeResponse getAccountRechargeList(@RequestBody AccountRechargeRequest request){

        AccountRechargeCustomizeResponse rechargeResponse =  new AccountRechargeCustomizeResponse();


        Integer count = this.rechargeManagementService.getAccountRechargeListCount(request);

        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        List<RechargeManagementCustomize> responseList = this.rechargeManagementService.getAccountRechargeList(request);

        if (!CollectionUtils.isEmpty(responseList)){

            // 充值状态 Redis Key
            String rechargeStatusKey = RedisConstants.CACHE_PARAM_NAME + CustomConstants.RECHARGE_STATUS;
            // 托管平台Key
            String bankTypeKey = RedisConstants.CACHE_PARAM_NAME + CustomConstants.BANK_TYPE;
            // 用户类型 Key
            String userPropertyKey = RedisConstants.CACHE_PARAM_NAME + CustomConstants.USER_PROPERTY;

            Map<String, String> rechargeStatusMap = RedisUtils.hgetall(rechargeStatusKey);
            logger.info("Redis中充值状态:" + CustomConstants.RECHARGE_STATUS + "的值为:" + rechargeStatusMap);
            Map<String, String> bankTypeMap = RedisUtils.hgetall(bankTypeKey);
            logger.info("Redis中托管平台:" + CustomConstants.BANK_TYPE + "中的值为:" + bankTypeMap);
            Map<String, String> userPropertyMap = RedisUtils.hgetall(userPropertyKey);
            logger.info("Redis中用户类型:" + CustomConstants.USER_PROPERTY + "中的值为:" + userPropertyMap);

            // 遍历列表从, 从Redis中读取配置信息
            for (RechargeManagementCustomize ac : responseList) {
                ac.setStatusName(rechargeStatusMap.get(ac.getStatus()));
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
        return this.rechargeManagementService.updateRechargeStatus(userId, nid);
    }

    /**
     * 确认充值(FIX) 操作
     * @param request
     * @return
     * @Author : huanghui
     */
    @RequestMapping(value = "/updateAccountAfterRecharge", method = RequestMethod.POST)
    public AccountRechargeCustomizeResponse updateAccountAfterRecharge(@RequestBody AccountRechargeRequest request){

        AccountRechargeCustomizeResponse rechargeResponse =  new AccountRechargeCustomizeResponse();

        String status = request.getStatusSearch();
        Integer userId = request.getUserId();
        String nid = request.getNidSearch();

        if (Validator.isNull(userId) || StringUtils.isBlank(nid) || StringUtils.isBlank(status)) {
            rechargeResponse.setRtn(Response.FAIL);
            rechargeResponse.setMessage("确认发生错误,请重新操作!参数不正确[userId=" + userId + "]");
            logger.info("确认发生错误,请重新操作!参数不正确[userId=" + userId + "]");
            return rechargeResponse;
        }

        // 根据用户ID查询用户账户信息
        Account account = rechargeManagementService.getAccountByUserId(userId);
        if (Validator.isNull(account)){
            rechargeResponse.setRtn(Response.FAIL);
            rechargeResponse.setMessage("确认发生错误,请重新操作!参数不正确[userId=" + userId + "]账户异常！");
            logger.info("确认发生错误,请重新操作!参数不正确[userId=" + userId + "]账户异常！");
            return rechargeResponse;
        }

        // 获取充值信息
        AccountRecharge accountRecharge = rechargeManagementService.getAccountRechargeByNid(nid);
        if (Validator.isNull(accountRecharge)){
            rechargeResponse.setRtn(Response.FAIL);
            rechargeResponse.setMessage("确认发生错误,请重新操作!参数不正确[nid=" + nid + "]账户异常！");
            logger.info("确认发生错误,请重新操作!参数不正确[nid=" + nid + "]账户异常！");
            return rechargeResponse;
        }

        // 确认充值 ; 0表示充值失败
        boolean isAccountUpdate = false;
        // 代码规约
        String errorStatus = "1";
        if (errorStatus.equals(status)){
            try {
                isAccountUpdate = this.rechargeManagementService.updateAccountAfterRecharge(userId, nid);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }else {
            // 充值失败,更新充值订单
            try {
                isAccountUpdate = this.rechargeManagementService.updateAccountAfterRechargeFail(userId, nid);
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        logger.info("Fix更新充值状态为:");
        // 充值数据状态更新
        if (isAccountUpdate){
            rechargeResponse.setRtn(Response.SUCCESS);
            rechargeResponse.setMessage(Response.SUCCESS_MSG);
        }else {
            rechargeResponse.setRtn(Response.FAIL);
            rechargeResponse.setMessage(Response.FAIL_MSG);
        }
        return rechargeResponse;
    }
}
