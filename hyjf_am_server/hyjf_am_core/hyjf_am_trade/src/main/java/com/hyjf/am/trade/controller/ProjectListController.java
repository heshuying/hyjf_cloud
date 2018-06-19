/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;
import com.hyjf.am.trade.service.ProjectListService;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVo;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 项目列表
 * @author liuyang
 * @version ProjectListController, v0.1 2018/6/13 11:15
 */
@RestController
@RequestMapping("/web/projectlist")
public class ProjectListController {

    @Autowired
   private ProjectListService projectListService;

    /**
     * 网站首页获取散标推荐
     * @param request
     * @return
     */
    @RequestMapping("/searchProjectList")
    public ProjectListResponse searchProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = projectListService.searchProjectList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectListCustomizeVo> webProjectListCustomizeVo = CommonUtils.convertBeanList(list,WebProjectListCustomizeVo.class);
            projectListResponse.setResultList(webProjectListCustomizeVo);
        }
        return projectListResponse;
    }

    /**
     * 网站首页获取散标推荐数目
     * @param request
     * @return
     */
    @RequestMapping("/countProjectList")
    public ProjectListResponse countProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countProjectList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * @desc  查询web端债转列表count
     * @author zhangyk
     * @date 2018/6/19 15:12
     */
    @RequestMapping("/searchWebCreditListCount")
    public CreditListResponse searchCreditListCount(@RequestBody @Valid CreditListRequest request){
        CreditListResponse CreditListResponse = new CreditListResponse();
        int count = projectListService.countCreditList(request);
        CreditListResponse.setCount(count);
        return CreditListResponse;
    }


    /**
     * @desc 查询web端债转列表数据
     * @author zhangyk
     * @date 2018/6/19 15:09
     */
    @RequestMapping("/searchWebCreditList")
    public CreditListResponse searchCreditList(@RequestBody @Valid CreditListRequest request){
        CreditListResponse res = new CreditListResponse();
        List<TenderCreditDetailCustomizeVO> list = projectListService.searchCreditList(request);
        res.setResultList(list);
        return res;
    }




}
