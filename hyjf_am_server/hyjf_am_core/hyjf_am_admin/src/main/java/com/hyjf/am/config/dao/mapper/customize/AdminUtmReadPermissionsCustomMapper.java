package com.hyjf.am.config.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;

/**
 * @author by dongzeshan on 2018/7/20.
 */
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