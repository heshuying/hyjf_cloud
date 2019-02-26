/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.HjhAccedeCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 自动出借
 * @author liubin
 * @version AutoTenderService, v0.1 2018/6/28 21:15
 */
public interface AutoTenderService extends BaseService {

    /**
     * 查询计划加入明细
     * @return
     */
    List<HjhAccedeCustomize> selectPlanJoinList();

    /**
     * 查询计划by Plannid
     * @param planNid
     * @return
     */
    HjhPlan selectHjhPlanByPlanNid(String planNid);

    /**
     * 查询计划订单by 加入计划订单号
     *
     * @param accedeOrderId
     * @return
     */
    HjhAccede selectHjhAccedeByAccedeOrderId(String accedeOrderId);

    /**
     * 银行自动投标成功后，更新出借数据
     * @param borrowNid
     * @param accedeOrderId
     * @param bean
     * @return
     */
    boolean updateBorrowForAutoTender(String borrowNid, String accedeOrderId, BankCallBean bean);

    /**
     * 获取相应的标的详情
     * @param borrowNid
     * @return
     */
    Borrow selectBorrowByNid(String borrowNid);

    /**
     * 获取相应的债转详情
     * @param creditNid
     * @return
     */
    HjhDebtCredit selectCreditByNid(String creditNid);

    /**
     * 出借完成更新计划明细
     * @param hjhAccede
     * @param orderStaus
     * @return
     */
    int updateHjhAccede(HjhAccede hjhAccede, int orderStaus);

    /**
     *
     * @param credit
     * @param debtPlanAccede
     * @param creditOrderId
     * @param creditOrderDate
     * @param account
     * @param isLast
     * @return
     */
    HjhCreditCalcResultVO saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO debtPlanAccede, String creditOrderId, String creditOrderDate, BigDecimal account, Boolean isLast);



    /**
     * 计算计划债转实际金额 保存creditTenderLog表
     * @param credit
     * @param debtPlanAccede
     * @param creditOrderId
     * @param creditOrderDate
     * @param account
     * @return
     */
    HjhCreditCalcResultVO saveCreditTenderLog(HjhDebtCreditVO credit, HjhAccedeVO debtPlanAccede, String creditOrderId, String creditOrderDate, BigDecimal account, Boolean isLast);

    /**
     * 银行自动债转成功后，更新债转数据
     * @param creditNid
     * @param accedeOrderId
     * @param planNid
     * @param bean
     * @param tenderUsrcustid
     * @param sellerUsrcustid
     * @param resultMap
     * @return
     */
    boolean updateCreditForAutoTender(String creditNid, String accedeOrderId, String planNid, BankCallBean bean,
                         String tenderUsrcustid, String sellerUsrcustid, HjhCreditCalcResultVO resultVO);

    /**
     * 根据是否原始债权获出让人投标成功的授权号
     * @param tenderOrderId
     * @param SourceType
     * @return
     */
    String getSellerAuthCode(String tenderOrderId, Integer SourceType);

    /**
     * 通过nid查找borrowTender
     * @param tenderNid
     * @return
     */
    BorrowTender selectBorrowTenderByNid(String tenderNid);

    /**
     * 通过AssignOrderId查找HjhDebtCreditTender
     * @param assignOrderId
     * @return
     */
    HjhDebtCreditTender selectHjhDebtCreditTenderByAssignOrderId(String assignOrderId);

    /**
     * 取得当前债权在清算前已经发生债转的本金
     * @param credit
     * @return
     */
    BigDecimal doGetPreCreditCapital(HjhDebtCredit credit);
}
