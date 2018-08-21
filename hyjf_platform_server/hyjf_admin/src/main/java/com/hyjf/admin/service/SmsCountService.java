/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.response.admin.SmsCountCustomizeResponse;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;

/**
 * @author fq
 * @version SmsCountService, v0.1 2018/8/17 14:01
 */
public interface SmsCountService {
    /**
     * 查询短信统计，通过分公司显示
     * @param request
     * @return
     */
    SmsCountCustomizeResponse querySmsCountList(SmsCountCustomizeVO request);

    /**
     * 查询短信总条数+总费用
     * @param smsCountCustomize
     * @return
     */
    Integer querySmsCountNumberTotal(SmsCountCustomizeVO request);

    /**
     * 获取部门列表
     * @param list
     * @return
     */
    JSONArray getCrmDepartmentList(String[] list);
}
