/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.adminuser.impl;

import com.hyjf.am.trade.dao.model.auto.ROaDepartment;
import com.hyjf.am.trade.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.user.dao.customize.CustomizeMapper;
import com.hyjf.am.user.service.admin.adminuser.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DongZeShan
 * @version DepartmentService.java, v0.1 2018年9月13日 下午2:47:02
 */
@Service
public class DepartmentServiceImpl  extends CustomizeMapper implements DepartmentService {
	@Override
	public List<ROaDepartment> getDepartmentList() {
		ROaDepartmentExample example = new ROaDepartmentExample();
		example.createCriteria().andNameNotLike("%居间人%").andNameNotLike("%借款人%").andNameNotLike("%无主单%");
		example.setOrderByClause(" parentid, sort ");
		return rOaDepartmentMapper.selectByExample(example);
	}
}
