/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.trade.HjhAccedeCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
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

//	/**
//	 * 自动投资
//	 *
//	 * @param hjhPlanAsset
//	 * @return
//	 */
//	boolean autoAssetBorrow(HjhAccede hjhAccede);

    /**
     * 银行自动投资成功后，更新投资数据
     * @param borrow
     * @param hjhAccede
     * @param bean
     * @return
     */
    boolean updateBorrowForAutoTender(Borrow borrow, HjhAccede hjhAccede, BankCallBean bean);

    /**
     * 获取相应的标的详情
     */
    Borrow selectBorrowByNid(String borrowNid);

    /**
     * 获取相应的债转详情
     */
    HjhDebtCredit selectCreditByNid(String creditNid);

    /**
     * 投资完成更新计划明细
     */
    int updateHjhAccede(HjhAccede hjhAccede, int orderStaus);

    /**
     * 删除 自动投资临时表
     * @param borrowNid
     * @param hjhAccede
     * @return
     */
    boolean deleteBorrowTmp(String borrowNid, HjhAccede hjhAccede) ;


    /**
     *
     * @param credit
     * @param debtPlanAccede
     * @param creditOrderId
     * @param creditOrderDate
     * @param account
     * @param isLast
     * @return
     * @throws Exception
     */
    Map<String, Object> saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO debtPlanAccede, String creditOrderId, String creditOrderDate, BigDecimal account, Boolean isLast);



    /**
     *
     * @param credit
     * @param debtPlanAccede
     * @param creditOrderId
     * @param creditOrderDate
     * @param account
     * @return
     * @throws Exception
     */
    Map<String, Object> saveCreditTenderLog(HjhDebtCreditVO credit, HjhAccedeVO debtPlanAccede, String creditOrderId, String creditOrderDate, BigDecimal account, Boolean isLast);

    /**
     * 银行自动债转成功后，更新债转数据
     * @param credit
     * @param hjhAccede
     * @param hjhPlan
     * @param bean
     * @param tenderUsrcustid
     * @param sellerUsrcustid
     * @param resultMap
     * @return
     * @throws Exception
     */
    boolean updateCreditForAutoTender(HjhDebtCredit credit, HjhAccede hjhAccede, HjhPlan hjhPlan, BankCallBean bean,
                         String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap);

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
    BigDecimal getPreCreditCapital(HjhDebtCredit credit);
}
