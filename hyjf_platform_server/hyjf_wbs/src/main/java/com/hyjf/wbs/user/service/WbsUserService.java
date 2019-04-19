package com.hyjf.wbs.user.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.wbs.qvo.WbsUserAuthInfo;
import com.hyjf.wbs.qvo.WebUserBindQO;

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


    void bind(WebUserBindQO webUserBindQO, JSONObject response);
}
