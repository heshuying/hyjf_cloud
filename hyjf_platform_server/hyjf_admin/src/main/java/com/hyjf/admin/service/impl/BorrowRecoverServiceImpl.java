package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.Utils.Page;
import com.hyjf.admin.beans.BorrowRecoverBean;
import com.hyjf.admin.client.BorrowRecoverClient;
import com.hyjf.admin.client.HjhInstConfigClient;
import com.hyjf.admin.service.BorrowRecoverService;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRecoverServiceImpl, v0.1 2018/7/2 10:17
 */
@Service
public class BorrowRecoverServiceImpl implements BorrowRecoverService {
    @Autowired
    private BorrowRecoverClient borrowRecoverClient;
    @Autowired
    private HjhInstConfigClient hjhInstConfigClient;
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        List<HjhInstConfigVO> list = hjhInstConfigClient.selectHjhInstConfigByInstCode(instCode);
        return list;
    }

    @Override
    public void searchAction(JSONObject jsonObject, BorrowRecoverBean form) {

        BorrowRecoverRequest request =createAdminBorrowRecoverBean(form);


        Integer count = this.borrowRecoverClient.countBorrowRecover(request);

        Page page = Page.initPage(form.getCurrPage(), form.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count != null && count > 0) {
            List<BorrowRecoverCustomizeVO> recordList = this.borrowRecoverClient.selectBorrowRecoverList(request);
            BorrowRecoverCustomizeVO sumAccount = this.borrowRecoverClient.sumBorrowRecoverList(request);
            jsonObject.put("sumAccount", sumAccount);
            jsonObject.put("recordList", recordList);
        }else
        jsonObject.put("page",page);
    }

    private BorrowRecoverRequest createAdminBorrowRecoverBean(BorrowRecoverBean form) {

        BorrowRecoverRequest request=new BorrowRecoverRequest();

        // 借款编号 检索条件
        request.setBorrowNidSrch(form.getBorrowNidSrch());
        // 计划编号 检索条件
        request.setPlanNidSrch(form.getPlanNidSrch());
        // 投资人 检索条件
        request.setUsernameSrch(form.getUsernameSrch());
        // 投资订单号 检索条件
        request.setOrderNumSrch(form.getOrderNumSrch());
        // 合作机构编号
        request.setInstCodeOrgSrch(form.getInstCodeOrgSrch());
        // 放款状态 检索条件
        request.setIsRecoverSrch(form.getIsRecoverSrch());
        // 投资时间 检索条件
        request.setTimeRecoverStartSrch(form.getTimeRecoverStartSrch());
        // 投资时间 检索条件
        request.setTimeRecoverEndSrch(form.getTimeRecoverEndSrch());
        // 投资时间 检索条件
        request.setTimeStartSrch(form.getTimeStartSrch());
        // 投资时间 检索条件
        request.setTimeEndSrch(form.getTimeEndSrch());
        // 放款订单号
        request.setLoanOrdid(form.getLoanOrdid());
        // 放款批次号
        request.setLoanBatchNo(form.getLoanBatchNo());
        // 资金来源 检索条件
        request.setInstCodeSrch(form.getInstCodeSrch());
        return  request;
    }
}
