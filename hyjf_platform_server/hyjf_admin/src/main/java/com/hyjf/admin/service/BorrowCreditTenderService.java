package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.beans.request.BorrowCreditTenderInfoRequest;
import com.hyjf.admin.beans.request.BorrowCreditTenderPDFSignReq;
import com.hyjf.admin.beans.request.BorrowCreditTenderRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.AdminCreditTenderResponse;
import com.hyjf.am.vo.config.AdminSystemVO;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface BorrowCreditTenderService {

    /**
     * 查询还款信息列表
     * @author zhangyk
     * @date 2018/7/11 14:34
     */
    AdminResult getBorrowCreditRepayList(BorrowCreditRepayRequest request);

    /**
     * 还款信息明细
     * @author zhangyk
     * @date 2018/7/12 10:52
     */
    AdminResult getBorrowCreditRepayInfoList(BorrowCreditRepayRequest request);


    /**
     * 承接信息列表
     * @author zhangyk
     * @date 2018/7/12 19:04
     */
    AdminResult getCreditTenderList(BorrowCreditTenderRequest request);

    /**
     * 查看债权人债权信息
     * @author zhangyk
     * @date 2018/7/13 15:21
     */
    AdminResult getCreditUserInfo(BorrowCreditTenderInfoRequest request);

    /**
     * pdf签署
     * @author zhangyk
     * @date 2018/8/28 16:59
     */
    AdminResult  pdfSign(BorrowCreditTenderPDFSignReq request, AdminSystemVO adminSystemVO);


    /**
     * pdf预览
     * @author zhangyk
     * @date 2018/11/6 9:47
     */
    AdminResult pdfPreview(BorrowCreditTenderPDFSignReq req);

    /**
     * 查询还款信息条数
     * @param request
     * @return
     */
    int selectBorrowCreditRepayCount(BorrowCreditRepayRequest request);

    /**
     * 查询承接信息条数
     * @param request
     * @return
     */
    int selectBorrowCreditTenderCount(BorrowCreditTenderRequest request);

    /**
     * 获取承接数据
     * @param request
     * @return
     */
    AdminCreditTenderResponse getCreditTenderResponse(BorrowCreditTenderRequest request);

    IntegerResponse doCreditEnd(String orderId);
}
