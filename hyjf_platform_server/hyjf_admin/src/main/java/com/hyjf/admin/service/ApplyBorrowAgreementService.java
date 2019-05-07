package com.hyjf.admin.service;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.ApplyBorrowAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.resquest.admin.DownloadAgreementRequest;

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
    AdminResult getApplyBorrowInfoDetail(ApplyBorrowAgreementRequest request);


    /**
     * 下载文件签署
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    void downloadAction(DownloadAgreementRequest requestBean, HttpServletResponse response);


}
