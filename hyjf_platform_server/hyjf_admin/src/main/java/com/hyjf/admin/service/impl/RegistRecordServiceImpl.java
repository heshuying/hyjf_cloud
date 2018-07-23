/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.UserManagerInitResponseBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class RegistRecordServiceImpl implements RegistRecordService {
    @Autowired
    private AmUserClient registRecordClient;
    /**
     * 查找注册记录列表
     *
     * @param request
     * @return
     */
    @Override
    public RegistRecordResponse findRegistRecordList(RegistRcordRequest request){
        RegistRecordResponse listRgistRecord = registRecordClient.findRegistRecordList(request);
        return listRgistRecord;
    }

    /**
     * 获取下拉列表的值
     * @return
     */
    @Override
    public UserManagerInitResponseBean initRegist(){
        UserManagerInitResponseBean userManagerInitResponseBean = new UserManagerInitResponseBean();
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        userManagerInitResponseBean.setRegistPlat(registPlat);
        return userManagerInitResponseBean;
    }

}
