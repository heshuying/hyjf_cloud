/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.dao.model.customize.AdminPlanAccedeListCustomize;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AutoTenderExceptionService, v0.1 2018/7/12 10:57
 */
public interface AutoTenderExceptionService {

    /**
     * 检索加入明细件数
     *
     * @Title countAccedeRecord
     * @param mapParam
     * @return
     */
    int countAccedeRecord(Map<String,Object> mapParam);

    /**
     * 检索加入明细列表
     *
     * @Title selectAccedeRecordList
     * @param mapParam
     * @return
     */
    List<AdminPlanAccedeListCustomize> selectAccedeRecordList(Map<String,Object> mapParam);


    /**
     * 查询计划加入明细
     * @param mapParam
     * @return
     */
    HjhAccede selectHjhAccede(Map<String,Object> mapParam);

    /**
     * 查询计划加入明细
     * @param mapParam
     * @return
     */
    HjhAccede doSelectHjhAccede(Map<String,Object> mapParam);

    /**
     * 查询计划加入明细临时表
     * @param mapParam
     * @return
     */
    HjhPlanBorrowTmp selectBorrowJoinList(Map<String,Object> mapParam);
    /**
     * 更改状态
     */
    boolean updateTender(Map<String,Object> mapParam);
    /**
     * 银行自动投标成功后，更新出借数据
     *
     * @param borrowNid
     * @param accedeOrderId
     * @param bean
     * @return
     */
    boolean updateBorrowForAutoTender(String borrowNid, String accedeOrderId, BankCallBean bean);
    /**
     * 保存用户的债转承接log数据
     *
     * @param credit
     * @param debtPlanAccede
     * @param creditOrderId
     * @param creditOrderDate
     * @param account
     * @return
     * @throws Exception
     */
    HjhCreditCalcResultVO saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO debtPlanAccede, String creditOrderId, String creditOrderDate, BigDecimal account, Boolean isLast);
    /**
     * 银行自动投标成功后，更新出借数据
     *
     * @param creditNid
     * @param accedeOrderId
     * @param planNid
     * @param bean
     * @param tenderUsrcustid
     * @param sellerUsrcustid
     * @param resultVO
     * @return
     */
    boolean updateCreditForAutoTender(String creditNid, String accedeOrderId, String planNid, BankCallBean bean,
                                             String tenderUsrcustid, String sellerUsrcustid, HjhCreditCalcResultVO resultVO);
    /**
     * 删除 自动出借临时表
     *
     * @param borrowNid
     * @param accedeOrderId
     * @return
     */
    boolean deleteBorrowTmp(String borrowNid, String accedeOrderId);
}
