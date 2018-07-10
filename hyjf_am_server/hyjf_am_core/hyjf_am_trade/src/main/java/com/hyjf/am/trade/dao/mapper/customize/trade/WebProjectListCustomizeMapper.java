/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.AppProjectListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * Web端项目列表相关Mapper
 *
 * @author liuyang
 * @version WebProjectListCustomizeMapper, v0.1 2018/6/13 13:42
 */
public interface WebProjectListCustomizeMapper {

    /**
     * 获取可投资项目列表
     * @param params
     * @return
     */
    List<WebProjectListCustomize> searchProjectList(Map<String, Object> params);

    /**
     * 获取可投资项目列表count
     * @param params
     * @return
     */
    int countProjectList(Map<String, Object> params);


    ProjectCustomeDetailVO getProjectDetail(Map<String, Object> params);
    /**
     * @desc 获取债转列表count
     * @author zhangyk
     * @date 2018/6/19 15:55
     */
    int countCreditList(Map<String,Object> params);

    /**
     * @desc  获取债转列表list
     * @author zhangyk
     * @date 2018/6/19 16:01
     */
    List<TenderCreditDetailCustomizeVO> searchCreditList(Map<String,Object> params);


    /**
     * 获取计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:54
     */
    Map<String,Object> searchPlanData();

    /**
     * Web端获取计划专区计划列表count
     * @author zhangyk
     * @date 2018/6/21 15:54
     */
    int countWebPlanList(Map<String,Object> params);

    /**
     * web端获取计划专区计划列表list
     * @author zhangyk
     * @date 2018/6/21 15:55
     */
    List<WebProjectListCustomizeVO> searchWebPlanList(Map<String, Object> params);


    // ----------------------------------web end -----------------------------------------------
    // ---------------------------------- app start --------------------------------------------

    /**
     * 获取可投资项目列表
     * @param params
     * @return
     */
    List<AppProjectListCustomize> searchAppProjectList(Map<String, Object> params);

    /**
     * 获取可投资项目列表count
     * @param params
     * @return
     */
    int countAppProject(Map<String, Object> params);


    /**
     * 获取债转项目列表
     * @param params
     * @return
     */
    List<WebProjectListCustomize> searchAppCreditList(Map<String, Object> params);

    /**
     * 获取债转列表count
     * @param params
     * @return
     */
    int countAppCredit(Map<String, Object> params);

    /**
     * app获取计划列表count
     * @author zhangyk
     * @date 2018/6/22 11:53
     */
    int countAppPlanList(Map<String, Object> params);

    /**
     * app 获取计划列表list
     * @param params
     * @return
     */
    List<WebProjectListCustomize> searchAppPlanList(Map<String, Object> params);
    // -----------------------------------app end ----------------------------------------------
}
