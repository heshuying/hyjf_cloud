package com.hyjf.am.trade.dao.mapper.customize.batch;

import com.hyjf.am.trade.dao.model.auto.Account;

/**
 * 会员用户开户记录初始化列表查询
 * 
 * @ClassName BatchAccountCustomizeMapper
 * @author dxj
 * @Date 
 */
public interface BatchAccountCustomizeMapper {

	/**
	 * 加息还款后,更新投资人账户信息
	 * 
	 * @Title updateAccountAfterRepay
	 * @param account
	 * @return
	 */
	public int updateAccountAfterRepay(Account account);

}
