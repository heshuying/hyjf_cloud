/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.safe;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.UserNoticeSetRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.result.ContractSetResultBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.BindEmailVO;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version SafeService, v0.1 2018/6/11 15:55
 */
public interface SafeService  extends BaseUserService {

    /**
     * 修登录密码
     * @param userId
     * @param oldPW
     * @param newPW
     * @return
     */
    JSONObject updatePassWd(Integer userId, String oldPW, String newPW);

    /**
     * 获取用戶通知配置信息
     * @param userId
     * @return
     */
    UserVO queryUserByUserId(Integer userId);

    Map<String,Object> safeInit(WebViewUser webViewUser);

    boolean sendEmailActive(Integer userId, String email) throws MQException;

    void checkForEmailSend(String email, Integer userId);

    void checkForEmailBind(BindEmailVO bindEmailVO, WebViewUser user);

    boolean updateEmail(Integer userId, String email) throws MQException;

    void checkForContractSave(String relationId, String rlName, String rlPhone, WebViewUser user);

    boolean saveContract(String relationId, String rlName, String rlPhone, WebViewUser user) throws MQException;

    int updateUserNoticeSet(UserNoticeSetRequest requestBean);

	ContractSetResultBean queryContractInfo(Integer userId);
}
