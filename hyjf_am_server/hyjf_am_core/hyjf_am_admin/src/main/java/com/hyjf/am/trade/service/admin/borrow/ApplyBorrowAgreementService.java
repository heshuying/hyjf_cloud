package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.borrow.ApplyBorrowAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyBorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;

import java.util.List;

/**
 * @version ApplyAgreementService, v0.1 2018/10/22 14:30
 * @Author: Zha Daojian
 */
public interface ApplyBorrowAgreementService extends BaseService{

    /**
     *  协议下载申请数目
     *
     * @return
     */
    Integer countApplyBorrowAgreement(ApplyBorrowAgreementRequest request);

    /**
     * 协议下载申请列表
     *
     * @return
     */
    List<ApplyBorrowAgreementVO> selectApplyBorrowAgreement(ApplyBorrowAgreementRequest request);



   /**
     * 协议申请标的信息明细
     *
     * @param borrowNid
     * @return
     */
   ApplyBorrowInfoVO getApplyBorrowInfoDetail(String borrowNid);

}
