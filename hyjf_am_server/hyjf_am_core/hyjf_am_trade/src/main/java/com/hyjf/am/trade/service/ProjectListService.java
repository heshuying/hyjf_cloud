/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.util.List;

import javax.validation.Valid;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;


/**
 * Web端项目列表Service
 * @author liuyang
 * @version ProjectListService, v0.1 2018/6/13 11:37
 */
public interface ProjectListService {


    /**
     * 获取项目列表
     * @param request
     * @return
     */
    List<WebProjectListCustomize> searchProjectList(@Valid ProjectListRequest request);

    /**
     * 获取项目列表件数
     * @param request
     * @return
     */
    int countProjectList(@Valid ProjectListRequest request);

    /**
     * 获取债转列表count
     * @param request
     * @return
     */
    int countCreditList(@Valid CreditListRequest request);

    /**
     * @desc  获取债转列表list
     * @author zhangyk
     * @date 2018/6/19 16:00
     */
    List<TenderCreditDetailCustomizeVO> searchCreditList(@Valid CreditListRequest request);
}
