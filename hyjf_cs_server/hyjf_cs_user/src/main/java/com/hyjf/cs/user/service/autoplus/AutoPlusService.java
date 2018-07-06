/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.autoplus;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.bean.AutoPlusRequestBean;
import com.hyjf.cs.user.bean.AutoPlusRetBean;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusService, v0.1 2018/6/11 15:38
 */
public interface AutoPlusService extends BaseUserService {

    /**
     * 授权自动债转、投资
     * @param
     * @param client  0web 1wechat 2app
     * @param type 1表示投资 2表示债转
     * @param channel
     * @param lastSrvAuthCode
     * @param smsCode
     * @return
     */
    Map<String,Object> userCreditAuthInves(UserVO user, Integer client, String type, String channel, String lastSrvAuthCode, String smsCode);

    /**
     * app、wechat授权自动债转、投资同步回调
     * @param token
     * @param bean
     * @param userAutoType
     * @param sign
     * @param isSuccess
     * @return
     */
    Map<String,BaseMapBean> userAuthCreditReturn(String token, BankCallBean bean, String userAutoType, String sign, String isSuccess);

    /**
     * 异步回调接口
     * @param bean
     * @return
     */
    String userBgreturn(BankCallBean bean,String type);

    Map<String,String> checkParam(AutoPlusRequestBean payRequestBean);

    Map<String,String> getErrorMV(AutoPlusRequestBean payRequestBean, String status);

    Map<String,Object> apiUserAuth(String type, String smsSeq, AutoPlusRequestBean payRequestBean);

    AutoPlusRetBean userAuthCreditReturn(BankCallBean bean, String callback, String acqRes, String type);

    BankCallResult userAuthInvesBgreturn(BankCallBean bean, String callback, String acqRes);

    BankCallBean getUserAuthQUery(Integer userId, String type);

    BankCallBean getTermsAuthQuery(int userId, String channel);

    void checkUserMessage(UserVO users, String lastSrvAuthCode, String smsCode);

    Map<String,Integer> userAutoStatus(Integer userId);

    /**
     * 查询授权状态
     */
    Map<String,String> getStatus(Integer userId);

    /**
     * 校验发送验证码接口参数
     * @param user
     * @param
     * @return
     */
    String checkSmsParam(UserVO user, String userAutoType);

    String checkApiSmsParam(AutoPlusRequestBean autoPlusRequestBean);

    BankCallBean appGetCommonBankCallBean(UserVO users, int i, String srvAuthCode, String code, String sign, String token);

    BaseMapBean appAuthInvesCheck(String srvAuthCode, String code, JSONObject checkResult, Integer userId);

    /**
     * 插入用户签约授权log
     * @param user
     * @param bean
     * @param client
     * @param authType
     */
    void insertUserAuthLog(UserVO user, BankCallBean bean, Integer client, String authType);

    String getBankRetMsg(String retCode);
}
