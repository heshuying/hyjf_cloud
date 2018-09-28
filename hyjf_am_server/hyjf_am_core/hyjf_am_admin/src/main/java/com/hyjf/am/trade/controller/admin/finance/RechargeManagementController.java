package com.hyjf.am.trade.controller.admin.finance;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.account.AccountRechargeCustomizeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.RechargeManagementCustomize;
import com.hyjf.am.trade.service.admin.finance.RechargeManagementService;
import com.hyjf.am.vo.trade.account.AccountRechargeCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.collections.CollectionUtils;
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
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            if (request.getPageSize() > 0){
                request.setLimitEnd(request.getPageSize());
            }else {
                request.setLimitEnd(paginator.getLimit());
            }
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
            Map<String, String> bankTypeMap = RedisUtils.hgetall(bankTypeKey);
            Map<String, String> userPropertyMap = RedisUtils.hgetall(userPropertyKey);

            // 遍历列表从, 从Redis中读取配置信息
            for (RechargeManagementCustomize ac : responseList) {
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
        return this.rechargeManagementService.updateRechargeStatus(userId, nid);
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
        String nid = request.getNidSearch();

        // 确认充值 ; 0表示充值失败
        boolean isAccountUpdate = false;
        // 代码规约
        String errorStatus = "1";
        if (errorStatus.equals(status)){
            isAccountUpdate = this.rechargeManagementService.updateAccountAfterRecharge(userId, nid);
        }else {
            isAccountUpdate = this.rechargeManagementService.updateAccountAfterRechargeFail(userId, nid);
        }

        return isAccountUpdate;
    }
}
