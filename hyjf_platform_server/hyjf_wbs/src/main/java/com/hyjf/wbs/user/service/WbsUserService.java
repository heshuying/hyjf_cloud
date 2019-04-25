package com.hyjf.wbs.user.service;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.wbs.qvo.*;

/**
 * @author cui
 * @version WbsUserService, v0.1 2019/4/19 10:06
 */
public interface WbsUserService {

    /**
     * 查询客户信息
     * @param assetCustomerId
     * @return
     */
    WbsUserAuthInfo queryUserAuthInfo(String assetCustomerId);

    /**
     * 快速授权
     * @param qo
     */
    void authorize(WbsRegisterMqVO qo);


    /**
     * web端快速绑定
     * @param webUserBindQO
     * @param response
     */
    void webBind(WebUserBindQO webUserBindQO, BaseResult response);

    /**
     * wechat绑定
     * @param wechatUserBindQO
     * @param result
     */
    void wechatBind(WechatUserBindQO wechatUserBindQO, BaseResult result);

    /**
     * 重定向请求
     * @param qo
     * @param ipAddr
     * @param channel
     * @param presetProps
     * @return
     */
    WebUserBindVO redirect(WbsRedirectQO qo, String ipAddr, String channel, String presetProps);
}
