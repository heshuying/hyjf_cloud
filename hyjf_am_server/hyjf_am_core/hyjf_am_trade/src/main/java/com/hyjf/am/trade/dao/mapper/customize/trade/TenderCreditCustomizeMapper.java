/**
 * Description:汇转让WEB接口DAO
 * Copyright: Copyright (HYJF Corporation) 2016
 * Company: HYJF Corporation
 * @author: 朱晓东
 * @version: 1.0
 * Created at: 2015年03月24日 下午18:35:00
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.*;

import java.util.List;
import java.util.Map;


public interface TenderCreditCustomizeMapper {

	int countTenderToCredit(Map<String, Object> params);
	
	List<TenderCreditCustomize> selectTenderToCreditList(Map<String, Object> params);
	
	int tenderAbleToCredit(Map<String ,Object> params);
	
	int countCreditByStatus(Map<String, Object> params);
    
    List<TenderCreditDetailCustomize> selectCreditByStatus(Map<String, Object> params);
    
    int countWebCredit(Map<String, Object> params);
    
    List<TenderCreditDetailCustomize> selectWebCreditList(Map<String, Object> params);
    
    List<TenderToCreditDetailCustomize> selectWebCreditTenderDetail(Map<String, Object> params);
    
    String creditTenderCapitalSum(Map<String, Object> params);
    
    int countCreditTenderAssigned(Map<String, Object> params);
    List<TenderCreditAssignedCustomize> selectCreditTenderAssigned(Map<String, Object> params);
    List<TenderCreditRepayPlanCustomize> selectCreditRepayPlan(Map<String, Object> params);

	List<TenderToCreditDetailCustomize> selectPlanWebCreditTenderDetail(
			Map<String, Object> params);
	
	java.math.BigDecimal selectCanCreditMoneyTotal(Map<String, Object> params);
	
	java.math.BigDecimal selectInCreditMoneyTotal(Map<String, Object> params);
	
	java.math.BigDecimal selectCreditSuccessMoneyTotal(Map<String, Object> params);
	
	java.math.BigDecimal selectInCreditNotAssignedMoneyTotal(Map<String, Object> params);
	
	java.math.BigDecimal selectInCreditAssignedMoneyTotal(Map<String, Object> params);
	
	List<TenderCreditDetailCustomize> selectCreditRecordList(Map<String, Object> params);
	
	int countCreditRecordTotal(Map<String, Object> params);
	
	List<TenderCreditAssignedStatisticCustomize> selectCreditTenderAssignedStatistic(Map<String, Object> params);

	List<CreditTenderListCustomize> selectWebCreditTenderList(Map<String, Object> params);

	int countWebCreditTenderList(Map<String, Object> params);
	
	List<TenderToCreditDetailCustomize> selectHJHWebCreditTenderDetail(Map<String, Object> params);
	
    List<TenderToCreditDetailCustomize> selectWebCreditTenderDetailForContract(Map<String, Object> params);
	
}
