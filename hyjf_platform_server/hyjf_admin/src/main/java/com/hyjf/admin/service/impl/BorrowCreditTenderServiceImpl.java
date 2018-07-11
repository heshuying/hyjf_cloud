package com.hyjf.admin.service.impl;

import com.hyjf.admin.Utils.Page;
import com.hyjf.admin.beans.BorrowCreditTenderResultBean;
import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.BorrowCreditTenderService;
import com.hyjf.am.response.trade.BorrowCreditTenderResponse;
import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.service.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.ListUI;
import java.util.List;

@Service
public class BorrowCreditTenderServiceImpl implements BorrowCreditTenderService {

    @Autowired
    private BaseClient baseClient;

    public static final String LIST_URL = "http://AM-TRADE/am-trade/creditTender/getList";

    public static final String COUNT_URL = "http://AM-TRADE/am-trade/creditTender/getCount";

    public static final String SUM_URL = "http://AM-TRADE/am-trade/creditTender/getSum";

    /**
     * 查询还款信息列表
     * @author zhangyk
     * @date 2018/7/11 14:34
     */
    @Override
    public AdminResult getBorrowCreditRepayList(BorrowCreditRepayRequest request) {
        AdminResult result = new AdminResult();
        BorrowCreditTenderResultBean bean = new BorrowCreditTenderResultBean();
        Page page = Page.initPage(request.getCurrPage(),request.getPageSize());
        BorrowCreditRepayAmRequest req = CommonUtils.convertBean(request,BorrowCreditRepayAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        BorrowCreditTenderResponse response = baseClient.postExe(COUNT_URL,req,BorrowCreditTenderResponse.class);
        Integer count = response.getCount();
        if (count > 0){
            response = baseClient.postExe(LIST_URL,req,BorrowCreditTenderResponse.class);
            List<BorrowCreditRepayVO> list = response.getResultList();
            bean.setRecordList(list);
            response = baseClient.postExe(SUM_URL,req,BorrowCreditTenderResponse.class);
            bean.setSumData(response.getSumData());
        }
        bean.setTotal(count);
        result.setData(bean);
        return result;
    }
}
