/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.regist;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.RegisterRequest;

/**
 * @author zhangqingqing
 * @version RegistService, v0.1 2018/6/11 15:09
 */
public interface RegistService extends BaseUserService {



    void registerCheckParam(int client, RegisterRequest registerRequest);

    boolean existUser(String mobile);

    /**
     * 注册
     * @param registerRequest
     * @param ip
     * @return
     * @throws ReturnMessageException
     */
    WebViewUserVO register(RegisterRequest registerRequest, String ip)
            throws ReturnMessageException;

    /**
     * api端注册
     * @param registerRequest
     * @param ipAddr
     * @return
     */
    UserVO apiRegister(RegisterRequest registerRequest, String ipAddr);

    /**
     * 检查活动是否有效
     * @param activityId
     * @return
     */
    boolean checkActivityIfAvailable(Integer activityId);

    int updateCheckMobileCode(String mobile, String code, String validCodeType, String clientPc, Integer ckcodeYiyan, Integer ckcodeYiyan1);

    int countUserByRecommendName(String recommend);
}
