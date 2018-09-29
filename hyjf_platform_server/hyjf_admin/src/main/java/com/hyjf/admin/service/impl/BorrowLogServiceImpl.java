package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.BorrowLogBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BorrowLogService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowLogServiceImpl, v0.1 2018/7/11 10:20
 */
@Service
public class BorrowLogServiceImpl implements BorrowLogService {

    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询借款列表操作日志
     */
    @Override
    public BorrowLogBean selectBorrowLogList(BorrowLogRequset request) {
        BorrowLogBean bean=new BorrowLogBean();
        Integer count = this.amTradeClient.countBorrowLog(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        bean.setTotal(count);
        if (count != null && count > 0) {
            List<BorrowLogCustomizeVO> recordList = this.amTradeClient.selectBorrowLogList(request);
            bean.setRecordList(recordList);
        }else{
            bean.setRecordList(new ArrayList<BorrowLogCustomizeVO>());
        }
        return bean;
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询借款操作日志列表导出
     */
    @Override
    public List<BorrowLogCustomizeVO> exportBorrowLogList(BorrowLogRequset request) {
        List<BorrowLogCustomizeVO> recordList = this.amTradeClient.exportBorrowLogList(request);
        return recordList;
    }
}
