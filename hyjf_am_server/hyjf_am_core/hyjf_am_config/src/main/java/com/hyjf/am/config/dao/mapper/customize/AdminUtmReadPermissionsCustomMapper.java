package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;

import java.util.List;


public interface AdminUtmReadPermissionsCustomMapper {
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	List<AdminUtmReadPermissionsVO> selectAdminUtmReadPermissionsRecord(AdminUtmReadPermissionsRequest request);

	/**
	 * 获得列表数
	 *
	 * @param request
	 * @return
	 */
	Integer countAdminUtmReadPermissionsRecord(AdminUtmReadPermissionsRequest request);
}