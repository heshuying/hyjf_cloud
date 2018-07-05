package com.hyjf.admin.service.impl;

import com.hyjf.admin.Utils.Page;
import com.hyjf.admin.beans.BorrowRecoverBean;
import com.hyjf.admin.beans.BorrowRepaymentBean;
import com.hyjf.admin.client.BorrowRecoverClient;
import com.hyjf.admin.client.BorrowRepaymentClient;
import com.hyjf.admin.client.HjhInstConfigClient;
import com.hyjf.admin.service.BorrowRepaymentService;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentServiceImpl, v0.1 2018/7/4 11:37
 */
@Service
public class BorrowRepaymentServiceImpl implements BorrowRepaymentService {

    @Autowired
    private HjhInstConfigClient hjhInstConfigClient;

    @Autowired
    private BorrowRepaymentClient borrowRepaymentClient;
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        List<HjhInstConfigVO> list = hjhInstConfigClient.selectHjhInstConfigByInstCode(instCode);
        return list;
    }

    @Override
    public BorrowRepaymentBean searchBorrowRepayment(BorrowRepaymentRequest request) {

        BorrowRepaymentBean bean=new BorrowRepaymentBean();
        Integer count = this.borrowRepaymentClient.countBorrowRepayment(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count != null && count > 0) {
            List<BorrowRepaymentCustomizeVO> recordList = this.borrowRepaymentClient.selectBorrowRepaymentList(request);
            bean.setRecordList(recordList);
            BorrowRepaymentCustomizeVO sumObject = this.borrowRepaymentClient.sumBorrowRepaymentInfo(request);
            bean.setSumObject(sumObject);
        }else{
            bean.setSumObject(new BorrowRepaymentCustomizeVO());
            bean.setRecordList(new ArrayList<BorrowRepaymentCustomizeVO>());
        }
        return bean;
    }

    @Override
    public List<BorrowRepaymentPlanCustomizeVO> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request) {
        return  this.borrowRepaymentClient.exportRepayClkActBorrowRepaymentInfoList(request);
    }

    @Override
    public List<BorrowRepaymentCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentRequest request) {
        return this.borrowRepaymentClient.selectBorrowRepaymentList(request);
    }
}
