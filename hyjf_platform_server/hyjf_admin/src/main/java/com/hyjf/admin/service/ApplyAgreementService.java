package com.hyjf.admin.service;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.resquest.admin.DownloadAgreementRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @version ApplyAgreementService, v0.1 2018/8/9 16:51
 * @Author: Zha Daojian
 */
public interface ApplyAgreementService {

    /**
     * 查询垫付协议申请列表
     * @author Zha Daojian
     * @date 2018/7/11 14:34
     */
    AdminResult getApplyAgreementList(ApplyAgreementRequest request);

    /**
     * 垫付协议申请明细列表页
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    AdminResult getAddApplyAgreementListDetail(BorrowRepayAgreementRequest request);

    /**
     * 批量生成垫付债转协议
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    AdminResult generateContract(BorrowRepayAgreementRequest request);

    /**
     * 下载文件签署
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    void downloadAction(DownloadAgreementRequest request,HttpServletResponse response);


}
