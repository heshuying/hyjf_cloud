/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.impl;

import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.borrow.BankReturnCodeConfigVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.user.beans.AutoPlusRequestBean;
import com.hyjf.cs.user.beans.AutoPlusRetBean;
import com.hyjf.cs.user.beans.BaseResultBean;
import com.hyjf.cs.user.client.AmBankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.ApiUserService;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version ApiUserServiceImpl, v0.1 2018/5/30 17:45
 */
@Service
public class ApiUserServiceImpl implements ApiUserService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBankOpenClient amBankOpenClient;

    @Autowired
    SystemConfig systemConfig;

    @Override
    public BankCallBean apiUserAuth(String type, String smsSeq, AutoPlusRequestBean payRequestBean) {
        BankOpenAccountVO bankOpenAccount = this.amBankOpenClient.selectByAccountId(payRequestBean.getAccountId());
        UserVO user= amUserClient.findUserById(bankOpenAccount.getUserId());
        Integer userId = user.getUserId();
        // 同步调用路径
        String retUrl = systemConfig.getWebHost()
                + "/server/autoPlus/userAuthInvesReturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl =systemConfig.getWebHost()
                + "/server/autoPlus/userAuthInvesBgreturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(payRequestBean.getAccountId(),userId,type,payRequestBean.getChannel(),smsSeq,payRequestBean.getSmsCode());
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 插入日志
        this.insertUserAuthLog(user, bean,1, BankCallConstant.QUERY_TYPE_1);
        return bean;
    }

    @Override
    public AutoPlusRetBean userAuthCreditReturn(BankCallBean bean, String callback, String acqRes, String type) {
        AutoPlusRetBean repwdResult = new AutoPlusRetBean();
        repwdResult.setCallBackAction(callback);
        repwdResult.setAcqRes(acqRes);
        bean.convert();
        //业务变更，银行不直接返回accountId，需要根据用户Id查询账号
        if (StringUtils.isNotBlank(bean.getLogUserId())) {
            BankOpenAccountVO bankOpenAccount =amBankOpenClient.selectById(Integer.parseInt(bean.getLogUserId()));
            repwdResult.set("accountId", bankOpenAccount.getAccount());
        }else{
            repwdResult.set("accountId", bean.getAccountId());
        }
        //投资人签约状态查询
        BankCallBean retBean = getUserAuthQUery(Integer.parseInt(bean.getLogUserId()),type);
        bean = retBean;
        if (bean!=null&& BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) && "1".equals(bean.getState())) {
            try {
                // 成功
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
                repwdResult.set("deadline", bean.getDeadline());

            } catch (Exception e) {
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
            }
        } else {
            // 失败

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }
        return repwdResult;
        }

    /**
     * @Author: zhangqingqing
     * @Desc :授权异步回调
     * @Param: * @param bean
     * @param callback
     * @param acqRes
     * @Date: 11:03 2018/5/31
     * @Return: com.hyjf.pay.lib.bank.bean.BankCallResult
     */
    @Override
    public BankCallResult userAuthInvesBgreturn(BankCallBean bean, String callback, String acqRes) {

        BankCallResult result = new BankCallResult();
        String message = "授权成功";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        // 返回值修改 end
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = amUserClient.findUserById(userId);
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 更新签约状态和日志表
                BankRequest bankRequest = new BankRequest();
                BeanUtils.copyProperties(bean,bankRequest);
                amUserClient.updateUserAuthInves(bankRequest);
                status = ErrorCodeConstant.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                message = "授权失败";
                status = ErrorCodeConstant.STATUS_CE999999;
            }
        }else{
            // 失败
            message = "授权失败,失败原因：" + getBankRetMsg(bean.getRetCode());
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        if (StringUtils.isNotBlank(bean.getLogUserId())) {
            BankOpenAccountVO bankOpenAccount =amBankOpenClient.selectById(Integer.parseInt(bean.getLogUserId()));
            params.put("accountId", bankOpenAccount.getAccount()==null?bean.getAccountId():bankOpenAccount.getAccount());
        }else{
            params.put("accountId", bean.getAccountId());
        }
        params.put("status", status);
        params.put("statusDesc",message);

        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("deadline", bean.getBidDeadline());
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",acqRes);
        CommonSoaUtils.noRetPostThree(callback, params);
        result.setMessage(message);
        result.setStatus(true);
        return result;
    }

    public BankCallBean getUserAuthQUery(Integer userId,String type) {
        // 调用查询投资人签约状态查询
        BankOpenAccountVO bankOpenAccount =amBankOpenClient.selectById(userId);
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);
        selectbean.setTxCode(BankCallConstant.TXCODE_CREDIT_AUTH_QUERY);
        selectbean.setInstCode(systemConfig.getBankInstcode());
        selectbean.setBankCode(systemConfig.getBankCode());
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        selectbean.setType(type);
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        //根据银行查询投资人签约状态
        if(BankCallConstant.QUERY_TYPE_1.equals(type)){
            selectbean.setLogRemark("用户授权自动投资");
        }else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
            selectbean.setLogRemark("用户授权自动债转");
        }
        selectbean.setLogClient(0);
        // 调用接口
        BankCallBean retBean = BankCallUtils.callApiBg(selectbean);
        return retBean;
    }

    /**
     * 组装发往江西银行参数
     * @param accountId
     * @param userid
     * @param type
     * @param channel
     * @param lastSrvAuthCode
     * @param smsCode
     * @return
     */
    private BankCallBean getCommonBankCallBean(String accountId, Integer userid, String type, String channel, String lastSrvAuthCode, String smsCode) {
        String remark = "";
        String txcode = "";
        // 构造函数已经设置
        // 版本号  交易代码  机构代码  银行代码  交易日期  交易时间  交易流水号   交易渠道
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,txcode,userid,channel);
        if(BankCallConstant.QUERY_TYPE_1.equals(type)){
            remark = "投资人自动投标签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
            bean.setDeadline(GetDate.date2Str(GetDate.countDate(1,5),new SimpleDateFormat("yyyyMMdd")));
            bean.setTxAmount("1000000");
            bean.setTotAmount("1000000000");
        } else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
            remark = "投资人自动债权转让签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
        }
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
        bean.setOrderId(bean.getLogOrderId());
        bean.setAccountId(accountId);
        bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
        bean.setLastSrvAuthCode(lastSrvAuthCode);
        bean.setSmsCode(smsCode);
        bean.setLogRemark(remark);

        return bean;
    }

    /**
     *插入日志
     * @param user
     * @param bean
     * @param client
     * @param authType
     */
    public void insertUserAuthLog(UserVO user, BankCallBean bean, Integer client, String authType) {
        Date nowTime=GetDate.getNowTime();
        HjhUserAuthLogVO hjhUserAuthLog=new HjhUserAuthLogVO();
        hjhUserAuthLog.setUserId(user.getUserId());
        hjhUserAuthLog.setUserName(user.getUsername());
        hjhUserAuthLog.setOrderId(bean.getLogOrderId());
        hjhUserAuthLog.setOrderStatus(2);
        if(authType!=null&&authType.equals(BankCallConstant.QUERY_TYPE_2)){
            hjhUserAuthLog.setAuthType(4);
        }else{
            hjhUserAuthLog.setAuthType(Integer.valueOf(authType));
        }
        hjhUserAuthLog.setOperateEsb(client);
        hjhUserAuthLog.setCreateUser(user.getUserId());
        hjhUserAuthLog.setCreateTime(nowTime);
        hjhUserAuthLog.setUpdateTime(nowTime);
        hjhUserAuthLog.setUpdateUser(user.getUserId());
        hjhUserAuthLog.setDelFlg(0);
        amUserClient.insertUserAuthLog(hjhUserAuthLog);
    }

    public String getBankRetMsg(String retCode) {
        //如果错误码不为空
        if (StringUtils.isNotBlank(retCode)) {
            BankReturnCodeConfigVO retCodes = this.amUserClient.getBankReturnCodeConfig(retCode);
            if (retCodes != null) {
                String retMsg = retCodes.getErrorMsg();
                if (StringUtils.isNotBlank(retMsg)) {
                    return retMsg;
                } else {
                    return "请联系客服！";
                }
            } else {
                return "请联系客服！";
            }
        } else {
            return "操作失败！";
        }
    }
}
