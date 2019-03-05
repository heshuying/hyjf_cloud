package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.BankRepayFreezeOrgCustomize;

import java.util.List;
import java.util.Map;

/**
 * 担保机构代偿冻结
 */
public interface BankRepayFreezeOrgCustomizeMapper {

	List<BankRepayFreezeOrgCustomize> selectList(Map<String, Object> paraMap);

	int selectCount(Map<String, Object> paraMap);

	Integer getFailCredit();
}

	