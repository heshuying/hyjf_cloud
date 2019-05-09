package com.hyjf.admin.service;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.vo.config.AdminSystemVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @version ApplyAgreementService, v0.1 2018/8/9 16:51
 * @Author: Zha Daojian
 */
public interface ApplyBorrowAgreementService {

    /**
     * 查询协议申请列表
     * @author Zha Daojian
     * @date 2018/7/11 14:34
     */
    AdminResult getApplyBorrowAgreementList(ApplyBorrowAgreementRequest request);


    /**
     * 协议申请明细页
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    AdminResult getApplyBorrowInfoDetail(ApplyBorrowInfoRequest request);

    /**
     * 申请协议
    * @author Zha Daojian
    * @date 2019/5/8 17:28
    * @param request
    * @return com.hyjf.admin.common.result.AdminResult
    **/
    AdminResult addBorrowAgreement(ApplyBorrowInfoRequest request,AdminSystemVO currUser);


    /**
     * 下载文件签署
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    void downloadAction(DownloadAgreementRequest requestBean, HttpServletResponse response);


}
