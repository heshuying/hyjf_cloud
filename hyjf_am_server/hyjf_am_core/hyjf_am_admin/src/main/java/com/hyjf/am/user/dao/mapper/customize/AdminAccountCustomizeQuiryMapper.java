package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize;

/**
 * @version AdminAccountCustomizeQuiryMapper, v0.1 2018/8/21 15:15
 * @Author: Zha Daojian
 */
public interface AdminAccountCustomizeQuiryMapper {

    /**
     * 通过手机号和身份证查询用户信息
     * @author Zha Daojian
     * @date 2018/8/21 18:53
     * @param request
     * @return com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize
     **/
    OpenAccountEnquiryCustomize selectAccountEnquiry(BankOpenAccountLogRequest request);
}
