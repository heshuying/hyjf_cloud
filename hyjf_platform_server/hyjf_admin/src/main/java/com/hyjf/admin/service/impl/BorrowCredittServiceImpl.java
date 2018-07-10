package com.hyjf.admin.service.impl;

import com.hyjf.admin.Utils.Page;
import com.hyjf.admin.beans.BorrowCreditListResultBean;
import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.client.AmBorrowCreditClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.BorrowCreditService;
import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowCredittServiceImpl implements BorrowCreditService {


    @Autowired
    private AmBorrowCreditClient amBorrowCreditClient;

    /**
     * 查询汇转让数据列表
     * @author zhangyk
     * @date 2018/7/9 15:12
     */
    @Override
    public AdminResult getBorrowCreditList(BorrowCreditRequest request) {
        AdminResult result = new AdminResult();
        BorrowCreditListResultBean bean = new BorrowCreditListResultBean();
        Page page = Page.initPage(request.getCurrPage(),request.getPageSize());
        BorrowCreditAmRequest req = CommonUtils.convertBean(request,BorrowCreditAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        Integer count = amBorrowCreditClient.getBorrowCreditCount(req);
        if (count != null && count > 0){
            List<BorrowCreditVO> list = amBorrowCreditClient.getBorrowCreditList(req);
            BorrowCreditSumVO sumVO = amBorrowCreditClient.getBorrwoCreditTotalSum(req);
            bean.setRecordList(list);
            bean.setSumCredit(sumVO);
            bean.setTotal(count);
        }
        result.setData(bean);
        return result;
    }
}
