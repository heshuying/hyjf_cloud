/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.user.beans.BaseMapBean;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.AppUserService;
import com.hyjf.cs.user.util.ClientConstant;
import com.hyjf.cs.user.util.ResultEnum;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AppUserServiceImpl, v0.1 2018/5/30 17:39
 */
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private AmUserClient amUserClient;
    /**
     * app wechat授权自动债转、投资同步回调
     * @param token
     * @param bean
     * @param userAutoType 1债转 0投资
     * @param request
     * @return
     */
    @Override
    public String userAuthCreditReturn(String token, BankCallBean bean, String userAutoType, String sign, String isSuccess) {
        Map<String,BaseMapBean> result = new HashMap<>();
        bean.convert();
        // 用户ID
        Integer userId = Integer.parseInt(bean.getLogUserId());
        HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(userId);
        if (isSuccess == null || !"1".equals(isSuccess)|| hjhUserAuth == null||hjhUserAuth.getAutoCreditStatus()!=1) {
            if (ClientConstant.INVES_AUTO_TYPE.equals(userAutoType)){
                result = getErrorMap(ResultEnum.USER_ERROR_204,sign,userAutoType, hjhUserAuth);
            }else {
                result = getErrorMap(ResultEnum.USER_ERROR_205,sign,userAutoType, hjhUserAuth);
            }
        }else {
            result = getSuccessMap(sign, userAutoType, hjhUserAuth);
        }
        return JSONObject.toJSONString(result, true);
    }

    /**
     * 组装跳转错误页面MV
     * @param param
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private Map<String,BaseMapBean> getErrorMap(ResultEnum param,String sign, String type, HjhUserAuthVO hjhUserAuth) {
        Map<String,BaseMapBean> result = new HashMap<>();
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.setCallBackAction(systemConfig.getWebHost()+"/user/setting/authorization/result/failed");
        result.put("callBackForm", baseMapBean);
        return result;
    }

    /**
     * 组装跳转成功页面MV
     * @param sign
     * @param type
     * @param autoInvesStatus
     * @param autoCreditStatus
     * @return
     */
    private Map<String,BaseMapBean> getSuccessMap(String sign, String type, HjhUserAuthVO hjhUserAuth) {
        Map<String,BaseMapBean> result = new HashMap<>();
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS.getStatusDesc());
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
        baseMapBean.setCallBackAction(systemConfig.getWebHost()+"/user/setting/authorization/result/success");
        result.put("callBackForm", baseMapBean);
        return result;
    }

}
