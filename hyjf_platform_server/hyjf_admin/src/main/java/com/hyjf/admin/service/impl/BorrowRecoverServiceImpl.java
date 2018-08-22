package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.BorrowRecoverBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.HjhInstConfigClient;
import com.hyjf.admin.service.BorrowRecoverService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRecoverServiceImpl, v0.1 2018/7/2 10:17
 */
@Service
public class BorrowRecoverServiceImpl implements BorrowRecoverService {
    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 获取资金来源列表
     */
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        List<HjhInstConfigVO> list = amTradeClient.selectHjhInstConfigByInstCode(instCode);
        return list;
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 获取放款明细列表信息
     */
    @Override
    public BorrowRecoverBean searchBorrowRecover(BorrowRecoverRequest request) {
        BorrowRecoverBean bean=new BorrowRecoverBean();
        Integer count = this.amTradeClient.countBorrowRecover(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count != null && count > 0) {
            List<BorrowRecoverCustomizeVO> recordList = this.amTradeClient.selectBorrowRecoverList(request);
            BorrowRecoverCustomizeVO sumAccount = this.amTradeClient.sumBorrowRecoverList(request);
            bean.setSumAccount(sumAccount);
            bean.setRecordList(recordList);
        }else{
            bean.setSumAccount(new BorrowRecoverCustomizeVO());
            bean.setRecordList(new ArrayList<BorrowRecoverCustomizeVO>());
        }
        bean.setTotal(count);
        return bean;
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 放款明细导出列表
     */
    @Override
    public List<BorrowRecoverCustomizeVO> exportBorrowRecoverList(BorrowRecoverRequest request) {
        List<BorrowRecoverCustomizeVO> recordList = this.amTradeClient.selectBorrowRecoverList(request);
        return recordList;
    }

}
