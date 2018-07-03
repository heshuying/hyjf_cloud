/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.VipManageClient;
import com.hyjf.admin.service.VipManageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.vo.admin.VipDetailListVO;
import com.hyjf.am.vo.admin.VipManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public JSONObject searchList(VipManageRequest vipManageRequest) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> mapParam = new HashMap<>();
        //0->成功,99->失败
        String status = Response.SUCCESS;
        //初始化分页参数，并组合到请求参数
        List<VipManageVO> vipManageVOS = vipManageClient.searchList(vipManageRequest);
        if (CollectionUtils.isEmpty(vipManageVOS)) {
            //暂无数据
            status = Response.FAIL;
        }
        jsonObject.put("record", vipManageVOS);
        jsonObject.put("status", status);
        return jsonObject;
    }

    /**
     * 查询vip详情信息
     * @param detailListRequest
     * @return
     */
    @Override
    public List<VipDetailListVO> searchDetailList(VipDetailListRequest detailListRequest) {
        Map<String, Object> mapParam = new HashMap<>();
        List<VipDetailListVO> vipDetailListVOS = vipManageClient.searchDetailList(detailListRequest);
        return vipDetailListVOS;
    }
}
