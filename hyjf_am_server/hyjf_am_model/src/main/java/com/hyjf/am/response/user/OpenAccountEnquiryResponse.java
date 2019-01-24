package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.OpenAccountEnquiryCustomizeVO;
import com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO;

/**
 * @version OpenAccountEnquiryResponse, v0.1 2018/8/21 15:36
 * @Author: Zha Daojian
 */
public class OpenAccountEnquiryResponse extends Response<OpenAccountEnquiryCustomizeVO> {

   private OpenAccountEnquiryDefineResultBeanVO OpenAccountEnquiryDefineResultBeanVO;

    public com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO getOpenAccountEnquiryDefineResultBeanVO() {
        return OpenAccountEnquiryDefineResultBeanVO;
    }

    public void setOpenAccountEnquiryDefineResultBeanVO(com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO openAccountEnquiryDefineResultBeanVO) {
        OpenAccountEnquiryDefineResultBeanVO = openAccountEnquiryDefineResultBeanVO;
    }
}
