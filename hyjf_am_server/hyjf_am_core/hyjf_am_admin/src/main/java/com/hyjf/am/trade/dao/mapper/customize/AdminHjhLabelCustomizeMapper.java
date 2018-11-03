/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.customize.AdminHjhLabelCustomize;
import com.hyjf.am.trade.dao.model.customize.AdminHjhLabelCustomizeExample;
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

	List<AdminHjhLabelCustomize> selectByExample(AdminHjhLabelCustomizeExample example);
}
