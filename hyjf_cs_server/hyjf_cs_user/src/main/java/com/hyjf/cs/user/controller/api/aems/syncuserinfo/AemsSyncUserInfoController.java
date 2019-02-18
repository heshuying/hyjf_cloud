package com.hyjf.cs.user.controller.api.aems.syncuserinfo;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.AemsSyncUserInfoRequest;
import com.hyjf.cs.user.bean.AemsSyncUserInfoResult;
import com.hyjf.cs.user.service.aems.syncuserinfo.AemsSyncUserService;
import com.hyjf.cs.user.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *用户信息
 *
 * @author Zha Daojian
 * @date 2018/12/7 16:38
 * @param 
 * @return 
 **/
@Api(value = "api端-AEMS用户信息",tags = "api端-AEMS用户信息查询")
@RestController
@RequestMapping("/hyjf-api/aems")
public class AemsSyncUserInfoController extends BaseController {

    private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");
    @Autowired
    private AemsSyncUserService syncUserService;

    @ApiOperation(value = "api端-AEMS同步用户信息", notes = "api端-AEMS同步用户信息")
    @PostMapping(value = "/syncUserInfo", produces = "application/json; charset=utf-8")
    public AemsSyncUserInfoResult syncUserInfo(@RequestBody AemsSyncUserInfoRequest syncUserInfoRequest, HttpServletRequest request) {
        logger.info("synUserInfoRequest is {}", JSONObject.toJSONString(syncUserInfoRequest));
        AemsSyncUserInfoResult result = new AemsSyncUserInfoResult();
        //机构编号
        String instCode = syncUserInfoRequest.getInstCode();
        //用户银行电子账号
        String accountIds = syncUserInfoRequest.getAccountIds();
        List<String> accountid = new ArrayList<>();
        String uId[] = accountIds.split(",");

        for (int i = 0; i < uId.length; i++) {
            if(!uId[i].isEmpty()){
                accountid.add(uId[i]);
            }
        }
        try {
            //验证请求参数
            if (Validator.isNull(instCode) || Validator.isNull(accountIds) ){
                result.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000001);
                result.setStatusDesc("请求参数非法");
                return result;
            }
            //验签
            if (!SignUtil.AEMSVerifyRequestSign(syncUserInfoRequest, "/aems/syncUserInfo")) {
                logger.info("----验签失败----");
                result.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000002);
                result.setStatusDesc("验签失败！");
                return result;
            }
            List<AemsSyncUserInfoResult.AccountBean> list = new ArrayList<>();
            AemsSyncUserInfoResult.AccountBean accountBean = null;
            //根据电子账户ID获取用户ID
            for (String accountId:accountid) {
                BankOpenAccountVO bankOpenAccount = this.syncUserService.getUserByAccountId(accountId);
                if (bankOpenAccount == null) {
                    result.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000004);
                    return result;
                }
                Integer userId = bankOpenAccount.getUserId();
                //根据用户ID获取用户账户信息
                AccountVO account = syncUserService.getAccount(userId);
                if (account == null) {
                    result.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000010);
                    return result;
                }
                accountBean = new AemsSyncUserInfoResult.AccountBean();
                this.copyProperties2Result(account, accountBean);

                accountBean.setAccountId(accountId);
                list.add(accountBean);
                result.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
            }
            result.setData(list);
            return result;
        }catch (Exception e){
            logger.error("获取用户信息失败",e);
            result.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE999999);
            return result;
        }
    }
    private void copyProperties2Result(AccountVO account, AemsSyncUserInfoResult.AccountBean accountBean) {
        accountBean.setPlanAwaitAmount(DF_FOR_VIEW.format(account.getPlanAccountWait()));
        accountBean.setPlanAwaitCapital(DF_FOR_VIEW.format(account.getPlanCapitalWait()));
        accountBean.setPlanAwaitInterest(DF_FOR_VIEW.format(account.getPlanInterestWait()));
        accountBean.setBorrowAwaitAmount(DF_FOR_VIEW.format(account.getBankAwait()));
        accountBean.setBorrowAwaitCapital(DF_FOR_VIEW.format(account.getBankAwaitCapital()));
        accountBean.setBorrowAwaitInterest(DF_FOR_VIEW.format(account.getBankAwaitInterest()));
        accountBean.setBalanceAmount(DF_FOR_VIEW.format(account.getBankBalance()));
        accountBean.setFrozenAmount(DF_FOR_VIEW.format(account.getBankFrost()));
        accountBean.setTotalAmount(DF_FOR_VIEW.format(account.getBankTotal()));
        accountBean.setInvestSum(DF_FOR_VIEW.format(account.getBankInvestSum()));
        accountBean.setInterestSum(DF_FOR_VIEW.format(account.getBankInterestSum()));
    }
}
