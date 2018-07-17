package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;

import java.math.BigDecimal;
import java.util.List;

public interface BorrowTenderService {

    Integer getCountBorrowTenderService(Integer userId, String borrowNid);

    BorrowTender selectBorrowTender(BorrowTenderRequest request);

    List<FddTemplet> getFddTempletList(Integer protocolType);

    int saveTenderAgreement(TenderAgreement tenderAgreement);

    int updateTenderAgreement(TenderAgreement tenderAgreement);

    List<BorrowTender> getBorrowTenderListByNid(String nid);

    /**
     * 根据投资订单号查询已承接金额
     * @param tenderNid
     * @return
     */
    BigDecimal getAssignCapitalByTenderNid(String tenderNid);

    /**
     * 保存债转信息
     * @param bean
     * @return
     */
    Integer saveCreditTenderAssignLog(CreditTenderLog bean);

    /**
     * 获取utm注册用户投资次数
     * @param list utm注册用户userid集合
     * @return
     */
    Integer getUtmTenderNum(List<Integer> list);

    /**
     * 获取utm注册用户HZT投资额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getHztTenderPrice(List<Integer> list);

    /**
     * 获取utm注册用户HXF投资额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getHxfTenderPrice(List<Integer> list);

    /**
     * 获取utm注册用户RTB投资额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getRtbTenderPrice(List<Integer> list);
}
