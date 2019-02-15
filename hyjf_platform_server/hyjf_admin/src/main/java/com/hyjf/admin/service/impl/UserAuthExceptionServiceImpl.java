/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.UserAuthExceptionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: sunpeikai
 * @version: UserAuthExceptionServiceImpl, v0.1 2018/7/2 10:34
 * 自动出借债转授权异常
 */
@Service
public class UserAuthExceptionServiceImpl extends BaseAdminServiceImpl implements UserAuthExceptionService {

    /**
     * 查询自动出借债转授权异常列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public AdminUserAuthListResponse selectUserAuthList(AdminUserAuthListRequest request) {
        AdminUserAuthListResponse response = amUserClient.userAuthList(request);
        return response;
    }
    /**
     * 同步用户授权状态
     * @auth sunpeikai
     * @param userId 用户id
     * @param type 1自动出借授权  2债转授权
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdminUserAuthListResponse synUserAuth(Integer userId,Integer type) {
        AdminUserAuthListResponse response = new AdminUserAuthListResponse();
        JSONObject jsonObject = amUserClient.synUserAuth(userId, type);
        // 返回值jsonObject不为空
        if(null != jsonObject && jsonObject.containsKey("status")){
            String status = jsonObject.get("status").toString();
            String msg = jsonObject.get("msg").toString();
            // 如果status != 0   且返回值中有retCode，说明调用银行接口出现错误
            if(!Response.SUCCESS.equals(status) && jsonObject.containsKey("retCode")){
                String retCode = jsonObject.get("retCode").toString();
                logger.debug("msg:[{}],retCode:[{}]",msg,retCode);
                if(!"".equals(retCode.trim())){
                    msg = amConfigClient.getBankRetMsg(retCode);
                }
            }
            response.setRtn(status);
            response.setMessage(msg);
        }
        logger.info(response.getMessage());
        return response;
    }
}
