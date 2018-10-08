package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.BorrowRepaymentInfoListBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BorrowRepaymentInfoListService;
import com.hyjf.admin.utils.Page;
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
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 获取资产来源配置列表
     */
    @Autowired
    private AmTradeClient amTradeClient;
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        List<HjhInstConfigVO> hjhInstConfigVOList = amTradeClient.selectCommonHjhInstConfigList();
        return hjhInstConfigVOList;
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询还款明细列表
     */
    @Override
    public BorrowRepaymentInfoListBean selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request) {
        BorrowRepaymentInfoListBean bean=new BorrowRepaymentInfoListBean();
        Integer count = this.amTradeClient.countBorrowRepaymentInfoList(request);
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        bean.setTotal(count);

        if (count != null && count > 0) {
            List<BorrowRepaymentInfoListCustomizeVO> recordList = this.amTradeClient.selectBorrowRepaymentInfoListList(request);
            bean.setRecordList(recordList);
            BorrowRepaymentInfoListCustomizeVO sumObject = this.amTradeClient.sumBorrowRepaymentInfoList(request);
            bean.setSumObject(sumObject);
        }
        return bean;
    }
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询还款详情导出数据
     */
    @Override
    public List<BorrowRepaymentInfoListCustomizeVO> selectExportBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request) {
        List<BorrowRepaymentInfoListCustomizeVO> recordList = this.amTradeClient.selectBorrowRepaymentInfoListList(request);
        return recordList;
    }
}
