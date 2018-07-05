/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import java.util.List;
import java.util.Map;

import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;

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
}
