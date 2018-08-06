/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.CreditRepay;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.dao.model.customize.trade.TenderCreditCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.TenderToCreditDetailCustomize;
import com.hyjf.am.vo.trade.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 银行债转异常处理
 * @author jun
 * @since 20180620
 */
public interface BankCreditTenderService {

	List<CreditTenderLog> selectCreditTenderLogs();

    List<CreditTender> selectCreditTender(String assignNid);

    int updateCreditTenderLog(CreditTenderLogVO creditTenderLog);

    CreditTenderLog selectCreditTenderLogByOrderId(String logOrderId);

    List<CreditTenderLog> selectByOrderIdAndUserId(String assignOrderId, Integer userId);

    int deleteByOrderIdAndUserId(String assignOrderId, Integer userId);

    CreditTender selectByAssignNidAndUserId(String assignNid, Integer userId);

    boolean updateTenderCreditInfo(CreditTenderRequest request) throws Exception;

	List<BorrowCredit> getBorrowCreditList(BorrowCreditRequest request);

    List<CreditTender> getCreditTenderList(CreditTenderRequest request);

	List<TenderToCreditDetailCustomize> selectWebCreditTenderDetailForContract(Map<String, Object> params);

    List<TenderToCreditDetailCustomize> selectHJHWebCreditTenderDetail(Map<String,Object> params);

    /**
     * 获取我要转让页面的金额
     * @param userId
     * @return
     */
    CreditPageVO selectCreditPageMoneyTotal(Integer userId);

    /**
     * 查询我可转让列表数量
     * @param request
     * @return
     */
    int searchCreditListCount(MyCreditListQueryRequest request);

    /**
     * 查询我可转让列表数量
     * @param request
     * @return
     */
    List<TenderCreditCustomize> searchCreditList(MyCreditListQueryRequest request);

    /**
     * 查询债转详情
     * @param userId
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    TenderCreditCustomize selectTenderToCreditDetail(Integer userId, String borrowNid, String tenderNid);

    /**
     * 债转详细预计服务费计算
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    ExpectCreditFeeVO selectExpectCreditFee(String borrowNid, String tenderNid);

    /**
     * 投资人当天是否可以债转
     * @param userId
     * @return
     */
    Integer tenderAbleToCredit(Integer userId);

    /**
     * 查询债转还款列表
     * @param tenderNid
     * @return
     */
    List<CreditRepay> selectCreditRepayList(String tenderNid);

    List<CreditRepay> selectCreditRepayList(String borrowNid, String tenderOrderId, Integer periodNow, Integer status);

    /**
     * 我要债转
     * @param request
     * @return
     */
    Integer saveCreditTender(BorrowCreditVO request);

    /**
     * 前端Web页面投资可债转输入投资金额后收益提示 用户未登录 (包含查询条件)
     * @param creditNid
     * @param assignCapital
     * @param userId
     * @return
     */
    TenderToCreditAssignCustomizeVO getInterestInfo(String creditNid, String assignCapital, Integer userId);

    /**
     * 根据creditNid查询
     * @param creditNid
     * @return
     */
    BorrowCreditVO getBorrowCreditByCreditNid(String creditNid);

    /**
     * 保存债转异步数据
     * @param request
     */
    void saveCreditBgData(CreditTenderBgVO request);

    /**
     * 修改债转结果
     * @param logOrderId
     * @param logUserId
     * @param retCode
     * @param retMsg
     * @return
     */
    Integer updateCreditTenderResult(String logOrderId, String logUserId, String retCode, String retMsg);

    /**
     * 查询债转失败原因
     * @param logOrderId
     * @param logUserId
     * @return
     */
    String getFailResult(String logOrderId, String logUserId);

    /**
     * 查询债转信息
     * @param logOrderId
     * @param logUserId
     * @return
     */
    CreditTenderVO getCreditTenderByUserIdOrdId(String logOrderId, String logUserId);

    /**
     * 获取utm用户债转投资
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getHzrTenderPrice(List<Integer> list);


    /**
     * 查询承接总记录数
     * @author zhangyk
     * @date 2018/7/25 17:19
     */
    int getCreditTenderCount(Map<String,Object> params);


    /**
     * 查询承接list
     * @author zhangyk
     * @date 2018/7/25 17:19
     */
    List<CreditTenderListCustomizeVO> getCreditTenderList(Map<String,Object> params);
    
	/**
	 * 获取债转承接信息AssignNid
	 * @param nid
	 * @return
	 */
    CreditTenderVO getCreditTenderByAssignNid(String assignNid);
}
