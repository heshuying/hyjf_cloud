/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.AdminUtmReadPermissionsMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminUtmReadPermissionsCustomMapper;
import com.hyjf.am.config.dao.model.auto.AdminUtmReadPermissions;
import com.hyjf.am.config.dao.model.auto.AdminUtmReadPermissionsExample;
import com.hyjf.am.config.service.AdminUtmReadPermissionsService;
import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.resquest.admin.AppChannelReconciliationRequest;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsServiceImpl, v0.1 2018/7/16 14:34
 */
@Service
public class AdminUtmReadPermissionsServiceImpl implements AdminUtmReadPermissionsService {
	@Autowired
	private AdminUtmReadPermissionsMapper adminUtmReadPermissionsMapper;
	@Autowired
	private AdminUtmReadPermissionsCustomMapper adminUtmReadPermissionsCustomMapper;

	@Override
	public AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		int count = adminUtmReadPermissionsCustomMapper.countAdminUtmReadPermissionsRecord(request);
		response.setCount(count);
		if(count>0){
			Paginator paginator = new Paginator(request.getCurrPage(), count);
			request.setLimitStart(paginator.getOffset());
			request.setLimitEnd(paginator.getLimit());
			List<AdminUtmReadPermissionsVO> list = adminUtmReadPermissionsCustomMapper.selectAdminUtmReadPermissionsRecord(request);
			response.setResultList(list);
		}
		return response;
	}

	@Override
	public void insertAction(AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissions record = new AdminUtmReadPermissions();
		BeanUtils.copyProperties(request, record);
		adminUtmReadPermissionsMapper.insertSelective(record);
	}

	@Override
	public void updateAction(AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissions record = new AdminUtmReadPermissions();
		BeanUtils.copyProperties(request, record);
		adminUtmReadPermissionsMapper.updateByPrimaryKey(record);
	}
	@Override
	public void deleteById(Integer id){
		adminUtmReadPermissionsMapper.deleteByPrimaryKey(id);
	}

    @Override
    public AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId) {
		AdminUtmReadPermissionsExample example = new AdminUtmReadPermissionsExample();
		AdminUtmReadPermissionsExample.Criteria cra = example.createCriteria();
		cra.andAdminUserIdEqualTo(userId);
		List<AdminUtmReadPermissions> list = this.adminUtmReadPermissionsMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return CommonUtils.convertBean(list.get(0),AdminUtmReadPermissionsVO.class);
		}
        return null;
    }

}
