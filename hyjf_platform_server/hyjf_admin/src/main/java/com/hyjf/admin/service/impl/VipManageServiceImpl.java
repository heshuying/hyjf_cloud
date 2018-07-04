/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.VipManageClient;
import com.hyjf.admin.service.VipManageService;
import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.response.admin.VipUpdateGradeListResponse;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.resquest.admin.VipUpdateGradeListRequest;
import com.hyjf.am.vo.admin.VipDetailListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version VipManageServiceImpl, v0.1 2018/7/2 15:00
 */
@Service
public class VipManageServiceImpl implements VipManageService {

    @Autowired
    private VipManageClient vipManageClient;

    @Override
    public JSONObject initVipManage() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    /**
     * 查询VIP用户信息
     *
     * @param vipManageRequest
     * @return
     */
    @Override
    public VipManageResponse searchList(VipManageRequest vipManageRequest) {
        return vipManageClient.searchList(vipManageRequest);
    }

    /**
     * 查询vip详情信息
     * @param detailListRequest
     * @return
     */
    @Override
    public VipDetailListResponse searchDetailList(VipDetailListRequest detailListRequest) {
        return vipManageClient.searchDetailList(detailListRequest);
    }

    @Override
    public VipUpdateGradeListResponse searchUpdateGradeList(VipUpdateGradeListRequest vgl) {
        return vipManageClient.searchUpdateGradeList(vgl);
    }
}
