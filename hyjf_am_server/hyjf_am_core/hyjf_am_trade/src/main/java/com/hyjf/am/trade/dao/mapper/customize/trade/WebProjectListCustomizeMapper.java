/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;

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
}
