/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.UserauthClient;
import com.hyjf.admin.service.UserAuthExceptionService;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sunpeikai
 * @version: UserAuthExceptionServiceImpl, v0.1 2018/7/2 10:34
 * 自动投资债转授权异常
 */
@Service
public class UserAuthExceptionServiceImpl implements UserAuthExceptionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserauthClient userauthClient;
    /**
     * 查询自动投资债转授权异常列表
     * @auth 孙沛凯
     * @param request 筛选条件
     * @return
     */
    @Override
    public AdminUserAuthListResponse selectUserAuthList(AdminUserAuthListRequest request) {
        AdminUserAuthListResponse response = userauthClient.userauthlist(request);
        return response;
    }
    /**
     * 同步用户授权状态
     * @auth 孙沛凯
     * @param userId 用户id
     * @param type 1自动投资授权  2债转授权
     * @return
     */
    @Override
    public AdminUserAuthListResponse synUserAuth(Integer userId,Integer type) {
        AdminUserAuthListResponse response = new AdminUserAuthListResponse();
        JSONObject jsonObject = userauthClient.synUserAuth(userId, type);
        // 返回值jsonObject不为空
        if(null != jsonObject && jsonObject.containsKey("status")){
            String status = jsonObject.get("status").toString();
            String msg = jsonObject.get("msg").toString();
            // 如果status != 00   且返回值中有retCode，说明调用银行接口出现错误
            if(!"00".equals(status) && jsonObject.containsKey("retCode")){
                String retCode = jsonObject.get("retCode").toString();
                msg = userauthClient.getBankRetMsg(retCode);
            }
            response.setRtn(status);
            response.setMessage(msg);
        }
        logger.info(response.getMessage());
        return response;
    }
}
