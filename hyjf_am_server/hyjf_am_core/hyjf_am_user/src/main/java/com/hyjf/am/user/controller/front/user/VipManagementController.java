/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.response.admin.VipUpdateGradeListResponse;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.resquest.admin.VipUpdateGradeListRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.VipDetailListCustomize;
import com.hyjf.am.user.dao.model.customize.VipManageListCustomize;
import com.hyjf.am.user.dao.model.customize.VipUpdateGradeListCustomize;
import com.hyjf.am.user.service.front.user.VipManagementService;
import com.hyjf.am.vo.admin.VipDetailListVO;
import com.hyjf.am.vo.admin.VipManageVO;
import com.hyjf.am.vo.admin.VipUpdateGradeListVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version VipManagementController, v0.1 2018/7/2 17:33
 * 后台vip中心——vip管理
 */

@RestController
@RequestMapping("/am-user/vipManage")
public class VipManagementController extends BaseController {

    @Autowired
    private VipManagementService vipManagementService;

    /**
     * 根据条件查找（vip管理列表显示）
     *
     * @param request
     * @return
     */
    @PostMapping("/getUserList")
    public VipManageResponse getVipList(@RequestBody @Valid VipManageRequest request) {
        logger.info("---findVipList by param---  " + JSONObject.toJSON(request));
        VipManageResponse response = new VipManageResponse();
        String returnCode = Response.FAIL;
        Map<String, Object> mapParam = paramSet(request);
        int count = vipManagementService.countRecord(mapParam);
        Paginator paginator = new Paginator(request.getPaginatorPage(), count, request.getLimit());
        if (request.getLimit() == 0) {
            paginator = new Paginator(request.getPaginatorPage(), count);
        }
        List<VipManageListCustomize> manageList = vipManagementService.selectUserList(mapParam, paginator.getOffset(), paginator.getLimit());
        if (count > 0) {
            if (!CollectionUtils.isEmpty(manageList)) {
                List<VipManageVO> vipManageVOS = CommonUtils.convertBeanList(manageList, VipManageVO.class);
                response.setResultList(vipManageVOS);
                response.setCount(count);
                returnCode = Response.SUCCESS;
            }
        }
        response.setRtn(returnCode);
        return response;
    }

    /**
     * 根据vip用户id查询用户详情列表
     *
     * @param request
     * @return
     */
    @PostMapping("/vipDetailList")
    public VipDetailListResponse getDetailList(@RequestBody @Valid VipDetailListRequest request) {
        VipDetailListResponse response = new VipDetailListResponse();
        String returnCode = Response.FAIL;
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("userId", request.getUserId());
        int count = vipManagementService.countDetailRecord(mapParam);
        Paginator paginator = new Paginator(request.getPaginatorPage(), count, request.getLimit());
        if (request.getLimit() == 0) {
            paginator = new Paginator(request.getPaginatorPage(), count);
        }
        List<VipDetailListCustomize> detailListCustomizes = vipManagementService.searchDetailList(mapParam, paginator.getOffset(), paginator.getLimit());
        if (count > 0) {
            if (!CollectionUtils.isEmpty(detailListCustomizes)) {
                List<VipDetailListVO> vipDetailListVOS = CommonUtils.convertBeanList(detailListCustomizes, VipDetailListVO.class);
                response.setResultList(vipDetailListVOS);
                response.setCount(count);
                returnCode = Response.SUCCESS;
            }
        }
        response.setRtn(returnCode);
        return response;
    }


    @PostMapping("/vipUpdateGradeList")
    public VipUpdateGradeListResponse getUpdateGradeList(@RequestBody @Valid VipUpdateGradeListRequest request) {
        VipUpdateGradeListResponse response = new VipUpdateGradeListResponse();
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("userId", request.getUserId());
        int count = vipManagementService.countUpdateGradeList(mapParam);
        Paginator paginator = new Paginator(request.getPaginatorPage(), count, request.getLimit());
        if (request.getLimit() == 0) {
            paginator = new Paginator(request.getPaginatorPage(), count);
        }
        List<VipUpdateGradeListCustomize> vipUpdateGradeListCustomizes = vipManagementService.searchUpdaeGradeList(mapParam, paginator.getOffset(), paginator.getLimit());
        if (count > 0) {
            if (!CollectionUtils.isEmpty(vipUpdateGradeListCustomizes)) {
                List<VipUpdateGradeListVO> vipUpdateGradeListVOS = CommonUtils.convertBeanList(vipUpdateGradeListCustomizes, VipUpdateGradeListVO.class);
                response.setResultList(vipUpdateGradeListVOS);
                response.setCount(count);
                response.setMessage(Response.SUCCESS_MSG);
            }
        }
        return response;
    }


    /**
     * 查询条件设置
     *
     * @param request
     * @return
     */
    private Map<String, Object> paramSet(VipManageRequest request) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("regTimeStart", request.getRegTimeStart());
        mapParam.put("regTimeEnd", request.getRegTimeEnd());
        mapParam.put("userName", request.getUserName());
        mapParam.put("realName", request.getRealName());
        mapParam.put("mobile", request.getMobile());
        mapParam.put("recommendName", request.getRecommendName());
        mapParam.put("combotreeListSrch", request.getCombotreeListSrch());
        mapParam.put("userRole", request.getUserRole());
        mapParam.put("userProperty", request.getUserProperty());
        mapParam.put("userStatus", request.getUserStatus());
        mapParam.put("is51", request.getIs51());
        return mapParam;
    }


}
