/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.adminuser;

import java.util.List;

import com.hyjf.am.trade.dao.model.auto.ROaDepartment;

/**
 * @author DongZeShan
 * @version DepartmentService.java, v0.1 2018年9月13日 下午2:47:02
 */
public interface DepartmentService {
	List<ROaDepartment> getDepartmentList();
}
