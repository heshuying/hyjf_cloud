package com.hyjf.admin.service.impl;

import com.hyjf.admin.Utils.Page;
import com.hyjf.admin.beans.BorrowLogBean;
import com.hyjf.admin.client.BorrowLogClient;
import com.hyjf.admin.client.BorrowRecoverClient;
import com.hyjf.admin.service.BorrowLogService;
import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowLogServiceImpl, v0.1 2018/7/11 10:20
 */
@Service
public class BorrowLogServiceImpl implements BorrowLogService {

    @Autowired
    private BorrowLogClient borrowLogClient;
    @Override
    public BorrowLogBean selectBorrowLogList(BorrowLogRequset request) {
        BorrowLogBean bean=new BorrowLogBean();
        Integer count = this.borrowLogClient.countBorrowLog(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        bean.setTotal(count);
        if (count != null && count > 0) {
            List<BorrowLogCustomizeVO> recordList = this.borrowLogClient.selectBorrowLogList(request);
            bean.setRecordList(recordList);
        }
        return bean;
    }

    @Override
    public List<BorrowLogCustomizeVO> exportBorrowLogList(BorrowLogRequset request) {
        List<BorrowLogCustomizeVO> recordList = this.borrowLogClient.exportBorrowLogList(request);
        return recordList;
    }
}
