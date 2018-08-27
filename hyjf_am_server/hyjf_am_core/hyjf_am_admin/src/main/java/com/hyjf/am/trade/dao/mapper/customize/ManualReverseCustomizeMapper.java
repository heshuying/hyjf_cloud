/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
	
package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.customize.ManualReverseCustomize;

/**
 * @author wangjun
 * @version ManualReverseCustomizeMapper, v0.1 2018/6/26 15:51
 */

public interface ManualReverseCustomizeMapper {
	/**
	 * 检索手动冲正数量
	 * 
	 * @param param
	 * @return
	 */
	Integer countManualReverse(Map<String, Object> param);
	/**
	 * 检索手动冲正数据
	 * 
	 * @param param
	 * @return
	 */
	List<ManualReverseCustomize> selectManualReverseList(Map<String, Object> param);
	/**
	 * 添加手动冲正数据
	 * 
	 * @param manualReverseCustomize
	 * @return
	 */
	void insertManualReverse(ManualReverseCustomize manualReverseCustomize);
	/**
	 * 通过用户账户信息查询用户信息
	 * 
	 * @param param
	 * @return
	 */
	List<String> selectUserIdsByAccount(Map<String, Object> param);
	/**
	 * 通过用户账户信息查询用户信息
	 * 
	 * @param param
	 * @return
	 */
	String selectUserNamebyAccountId(Map<String, Object> param);
	/**
	 * 查询原体现交易数据
	 * 
	 * @param param
	 * @turn
	 */
	Integer countAccountList(Map<String, Object> param);
}

	