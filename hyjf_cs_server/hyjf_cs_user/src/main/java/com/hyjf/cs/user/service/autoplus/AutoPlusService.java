/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.autoplus;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.bean.ApiAutoPlusResultBean;
import com.hyjf.cs.user.bean.AutoPlusRequestBean;
import com.hyjf.cs.user.bean.AutoPlusRetBean;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusService, v0.1 2018/6/11 15:38
 */
public interface AutoPlusService extends BaseUserService {

    /**
     * 授权自动债转、出借
     * @param
     * @param client  0web 1wechat 2app
     * @param type 1表示出借 2表示债转
     * @param channel
     * @param lastSrvAuthCode
     * @param smsCode
     * @return
     */
    Map<String,Object> userCreditAuthInves(UserVO user, Integer client, String type, String channel, String lastSrvAuthCode, String smsCode);

    /**
     * app、wechat授权自动债转、出借同步回调
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

    /**
     * api检查参数
     * @param payRequestBean
     * @param modelAndView
     * @param queryType
     * @return
     */
    Map<String,Object>  checkParam(AutoPlusRequestBean payRequestBean, ModelAndView modelAndView, String queryType);

    /**
     * api授权
     * @param type
     * @param smsSeq
     * @param user
     * @param payRequestBean
     * @return
     */
    BankCallBean apiUserAuth(String type, String smsSeq, UserVO user, AutoPlusRequestBean payRequestBean);

    /**
     * api自动出借授权同步回调
     * @param bean
     * @param callback
     * @param acqRes
     * @param type
     * @return
     */
    AutoPlusRetBean userAuthCreditReturn(BankCallBean bean, String callback, String acqRes, String type);

    /**
     * api异步回调
     * @param bean
     * @param callback
     * @param acqRes
     * @return
     */
    BankCallResult userAuthInvesBgreturn(BankCallBean bean, String callback, String acqRes);

    /**
     * 银行短信
     * @param userId
     * @param type
     * @return
     */
    BankCallBean getUserAuthQUery(Integer userId, String type);

    /**
     *
     * @param userId
     * @param channel
     * @return
     */
    BankCallBean getTermsAuthQuery(int userId, String channel);

    /**
     * 验证用户信息
     * @param users
     * @param lastSrvAuthCode
     * @param smsCode
     */
    void checkUserMessage(UserVO users, String lastSrvAuthCode, String smsCode);

    /**
     * 授权状态接口
     * @param userId
     * @return
     */
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

    /**
     * 前导发送短信验证码
     *
     * @param autoPlusRequestBean
     * @return
     */
    ApiAutoPlusResultBean sendCode(AutoPlusRequestBean autoPlusRequestBean);

    /**
     * app组装发往江西银行参数
     * @param users
     * @param i
     * @param srvAuthCode
     * @param code
     * @param sign
     * @param token
     * @return
     */
    BankCallBean appGetCommonBankCallBean(UserVO users, int i, String srvAuthCode, String code, String sign, String token);

    /**
     * app用户授权自动债转
     * @param srvAuthCode
     * @param code
     * @param checkResult
     * @param userId
     * @param queryType
     * @return
     */
    void appAuthInvesCheck(String srvAuthCode, String code, JSONObject checkResult, Integer userId, String queryType);

    /**
     * 插入用户签约授权log
     * @param user
     * @param bean
     * @param client
     * @param authType
     */
    void insertUserAuthLog(UserVO user, BankCallBean bean, Integer client, String authType);


    /**
     * 微信 组装发往江西银行参数
     * @param users
     * @param i
     * @param srvAuthCode
     * @param code
     * @param sign
     * @return
     */
    BankCallBean weChatGetCommonBankCallBean(UserVO users, int i, String srvAuthCode, String code, String sign,String wjtClient);
}
