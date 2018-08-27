package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.customize.CallCenterHtjRepaymentDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.CallCenterHztRepaymentDetailCustomize;

public interface CallCenterRepaymentDetailCustomizeMapper {
    /**
     * 
     * 按照用户名/手机号查询还款明细（直投产品，含承接的债权）
     * @author pcc
     * @param paraMap
     * @return
     */
    List<CallCenterHztRepaymentDetailCustomize> getHztRepaymentDetailList(Map<String, Object> paraMap);
    /**
     * 
     * 按照用户名/手机号查询还款明细（汇添金）
     * @author pcc
     * @param paraMap
     * @return
     */
    List<CallCenterHtjRepaymentDetailCustomize> getHtjRepaymentDetailList(Map<String, Object> paraMap);

}