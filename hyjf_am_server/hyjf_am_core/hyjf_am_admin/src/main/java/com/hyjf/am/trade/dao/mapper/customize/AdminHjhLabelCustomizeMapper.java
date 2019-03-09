/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.AdminHjhLabelCustomize;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version AdminHjhLabelCustomizeMapper.java, v0.1 2018年7月2日 上午11:36:40
 */
public interface AdminHjhLabelCustomizeMapper {
	/**
	 * @param paraMap
	 * @return
	 */
	List<HjhLabelCustomizeVO> selectHjhLabelListById(Map<String, Object> paraMap);

	/**
	 * 条件查询列表
	 * @param paraMap
	 * @return
	 */
	List<AdminHjhLabelCustomize> selectHjhLabelList(Map<String, Object> paraMap);

	/**
	 * 获取count
	 * @param paraMap
	 * @return
	 */
	int getAdminHjhLabelCount(Map<String,Object> paraMap);
}
