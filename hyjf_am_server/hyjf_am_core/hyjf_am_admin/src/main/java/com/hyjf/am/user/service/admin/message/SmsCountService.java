/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.message;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.user.dao.model.customize.OADepartmentCustomize;
import com.hyjf.am.user.dao.model.customize.SmsCountCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version SmsCountService, v0.1 2018/8/20 16:27
 */
public interface SmsCountService {
    /**
     * 查询短信统计，通过分公司显示
     * @param request
     * @return
     */
    List<SmsCountCustomize> querySmsCountLlist(SmsCountRequest request);

    /**
     * 查询短信总条数+总费用
     * @param request
     * @return
     */
    List<SmsCountCustomize> querySmsCountNumberTotal(SmsCountRequest request);

    /**
     * 查询部门信息
     * @return
     */
    List<OADepartmentCustomize> queryDepartmentInfo();

    /**
     * 查询总条数
     * @return
     */
    int selectCount(SmsCountRequest request);

    /**
     * 查询符合条件的用户的号码集合
     * @param request
     * @return
     */
    List<String> queryUser(SmsCodeUserRequest request);

    Map<String, SmsCountCustomize> getSmsCount(String mobile, String messageStr);

    void addSmsCount(Map<String, SmsCountCustomize> paramMap);
}
