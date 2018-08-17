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

package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.TenderCreditAssignedCustomize;
import com.hyjf.am.trade.dao.model.customize.TenderCreditAssignedStatisticCustomize;
import com.hyjf.am.trade.dao.model.customize.TenderCreditCustomize;
import com.hyjf.am.trade.dao.model.customize.TenderToCreditDetailCustomize;

import java.util.List;
import java.util.Map;


public interface TenderCreditCustomizeMapper {

	int countTenderToCredit(Map<String, Object> params);
	
	List<TenderCreditCustomize> selectTenderToCreditList(Map<String, Object> params);
	
	int tenderAbleToCredit(Map<String ,Object> params);
	
	java.math.BigDecimal selectCanCreditMoneyTotal(Map<String, Object> params);
	
	java.math.BigDecimal selectInCreditMoneyTotal(Map<String, Object> params);
	
	java.math.BigDecimal selectCreditSuccessMoneyTotal(Map<String, Object> params);

	List<TenderToCreditDetailCustomize> selectHJHWebCreditTenderDetail(Map<String, Object> params);
	
    List<TenderToCreditDetailCustomize> selectWebCreditTenderDetailForContract(Map<String, Object> params);

	int countCreditTenderAssigned(Map<String, Object> params);

	List<TenderCreditAssignedCustomize> selectCreditTenderAssigned(Map<String, Object> params);

	List<TenderCreditAssignedStatisticCustomize> selectCreditTenderAssignedStatistic(Map<String, Object> params);
}
