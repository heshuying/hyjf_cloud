package com.hyjf.admin.service.impl;

import com.hyjf.admin.Utils.Page;
import com.hyjf.admin.beans.BorrowRepaymentInfoListBean;
import com.hyjf.admin.client.BorrowRepaymentInfoListClient;
import com.hyjf.admin.client.HjhInstConfigClient;
import com.hyjf.admin.service.BorrowRepaymentInfoListService;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoListServiceImpl, v0.1 2018/7/10 10:23
 */
@Service
public class BorrowRepaymentInfoListServiceImpl implements BorrowRepaymentInfoListService {
    @Autowired
    private HjhInstConfigClient hjhInstConfigClient;

    @Autowired
    private BorrowRepaymentInfoListClient borrowRepaymentInfoListClient;
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        List<HjhInstConfigVO> list = hjhInstConfigClient.selectHjhInstConfigByInstCode(instCode);
        return list;
    }

    @Override
    public BorrowRepaymentInfoListBean selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request) {
        BorrowRepaymentInfoListBean bean=new BorrowRepaymentInfoListBean();
        Integer count = this.borrowRepaymentInfoListClient.countBorrowRepaymentInfoList(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        bean.setTotal(count);

        if (count != null && count > 0) {
            List<BorrowRepaymentInfoListCustomizeVO> recordList = this.borrowRepaymentInfoListClient.selectBorrowRepaymentInfoListList(request);
            bean.setRecordList(recordList);
            BorrowRepaymentInfoListCustomizeVO sumObject = this.borrowRepaymentInfoListClient.sumBorrowRepaymentInfoList(request);
            bean.setSumObject(sumObject);
        }
        return null;
    }

    @Override
    public List<BorrowRepaymentInfoListCustomizeVO> selectExportBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request) {
        List<BorrowRepaymentInfoListCustomizeVO> recordList = this.borrowRepaymentInfoListClient.selectBorrowRepaymentInfoListList(request);
        return recordList;
    }
}
