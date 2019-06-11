/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AppProjectListCustomize;
import com.hyjf.am.trade.dao.model.customize.HjhPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.PlanDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.WebProjectListCustomize;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.trade.CreditListVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import java.util.List;
import java.util.Map;

/**
 * Web端项目列表相关Mapper
 *
 * @author pcc
 * @version WebProjectListCustomizeMapper, v0.1 2018/6/13 13:42
 */
public interface WjtProjectListCustomizeMapper {

    /**
     * 获取可出借项目列表
     * @param params
     * @return
     */
    List<WebProjectListCustomize> searchWjtWebProjectList(Map<String, Object> params);

    /**
     * 获取可出借项目列表count
     * @param params
     * @return
     */
    int countWjtWebProjectList(Map<String, Object> params);

    List<WechatHomeProjectListVO> searchWjtWechatProjectList(Map<String,Object> params);
}
