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
     * Web端获取项目列表
     * @param request
     * @return
     */
    List<WebProjectListCustomize> searchProjectList(@Valid ProjectListRequest request);

    /**
     * Web端获取项目列表件数
     * @param request
     * @return
     */
    int countProjectList(@Valid ProjectListRequest request);

    /**
     * Web端获取债转列表count
     * @param request
     * @return
     */
    int countCreditList(@Valid CreditListRequest request);

    /**
     * Web端获取债转列表list
     * @author zhangyk
     * @date 2018/6/19 16:00
     */
    List<TenderCreditDetailCustomizeVO> searchCreditList(@Valid CreditListRequest request);

    // --------------------------web end --------------------------------------------------
    //---------------------------app start ------------------------------------------------
    /**
     * app端获取散标投资count
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
     int  countAppProjectList(@Valid ProjectListRequest request);

    /**
     * app端获取散标投资数据list
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
    List<WebProjectListCustomize> searchAppProjectList(@Valid ProjectListRequest request);

    /**
     * app端获取散标投资count
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
    int  countAppCreditList(@Valid ProjectListRequest request);

    /**
     * app端获取散标投资数据list
     * @author zhangyk
     * @date 2018/6/20 16:11
     */
    List<WebProjectListCustomize> searchAppCreditList(@Valid ProjectListRequest request);
    // --------------------------app end --------------------------------------------------
}
