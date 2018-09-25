package com.hyjf.admin.service;

import com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean;
import com.hyjf.admin.beans.request.OpenAccountEnquiryDefineRequestBean;
import com.hyjf.am.vo.config.AdminSystemVO;

/**
 * @version OpenAccountEnquiryService, v0.1 2018/8/20 16:25
 * @Author: Zha Daojian
 */
public interface OpenAccountEnquiryService {

    /**
     * 用户按照手机号和身份证号查询开户掉单
    * @author Zha Daojian
    * @date 2018/8/20 16:26
    * @param requestBean
    * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
    **/
    OpenAccountEnquiryDefineResultBean openAccountEnquiry(AdminSystemVO currUser,OpenAccountEnquiryDefineRequestBean requestBean);

    /**
     * 用户按照手机号和身份证号查询开户掉单后同步系统掉单信息，更改用户状态
    * @author Zha Daojian
    * @date 2018/8/20 16:35
    * @param requestBean
    * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
    **/
    OpenAccountEnquiryDefineResultBean openAccountEnquiryUpdate(OpenAccountEnquiryDefineRequestBean requestBean);
}
