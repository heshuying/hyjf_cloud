/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.mapper.customize.trade.WebProjectListCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;
import com.hyjf.am.trade.service.ProjectListService;

/**
 * Web端项目列表Service实现类
 *
 * @author liuyang
 * @version ProjectListServiceImpl, v0.1 2018/6/13 11:38
 */
@Service
public class ProjectListServiceImpl implements ProjectListService {
    @Autowired
    private WebProjectListCustomizeMapper webProjectListCustomizeMapper;

    /**
     * 获取标的列表
     * @param request
     * @return
     */
    @Override
    public List<WebProjectListCustomize> searchProjectList(@Valid ProjectListRequest request) {
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

        return webProjectListCustomizeMapper.searchProjectList(params);
    }

    /**
     * 获取标的列表件数
     *
     * @param request
     * @return
     */
    @Override
    public int countProjectList(@Valid ProjectListRequest request) {
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
        return webProjectListCustomizeMapper.countProjectList(params);
    }
}
