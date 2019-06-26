/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.front.borrow.ProjectListService;
import com.hyjf.am.trade.service.front.borrow.WjtProjectListService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.CreditListVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Web端项目列表Service实现类
 *
 * @author liuyang
 * @version ProjectListServiceImpl, v0.1 2018/6/13 11:38
 */
@Service
public class WjtProjectListServiceImpl extends BaseServiceImpl implements WjtProjectListService {


    /**
     * 获取标的列表
     * @param request
     * @return
     */
    @Override
    public List<WebProjectListCustomize> searchWjtWebProjectList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("limitStart", limitStart);
        params.put("limitEnd", limitEnd);
        params.put("publishInstCode",StringUtils.isBlank(request.getPublishInstCode()) ? "" : request.getPublishInstCode());
        return wjtProjectListCustomizeMapper.searchWjtWebProjectList(params);
    }

    /**
     * 获取标的列表件数
     *
     * @param request
     * @return
     */
    @Override
    public int countWjtWebProjectList(@Valid ProjectListRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 项目类型
        String projectType = request.getProjectType();
        // 项目子类型
        String borrowClass = request.getBorrowClass();
        // 分页起始
        Integer limitStart = request.getLimitStart();
        // 分页结束
        Integer limitEnd = request.getLimitEnd();
        params.put("projectType", projectType);
        params.put("borrowClass", borrowClass);
        params.put("limitStart",limitStart);
        params.put("limitEnd", limitEnd);
        params.put("publishInstCode",StringUtils.isBlank(request.getPublishInstCode()) ? "" : request.getPublishInstCode());
        return wjtProjectListCustomizeMapper.countWjtWebProjectList(params);
    }


    /**
     * 查询微信端首页产品列表
     * @author pcc
     * @date 2018/7/24 13:45
     */
    @Override
    public List<WechatHomeProjectListVO> searchWjtWechatProjectList(Map<String, Object> params) {
        return  wjtProjectListCustomizeMapper.searchWjtWechatProjectList(params);
    }

}
