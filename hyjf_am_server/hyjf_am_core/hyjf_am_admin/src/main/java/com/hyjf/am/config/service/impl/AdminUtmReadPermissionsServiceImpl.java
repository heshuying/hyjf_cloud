/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.AdminMapper;
import com.hyjf.am.config.dao.mapper.auto.AdminUtmReadPermissionsMapper;
import com.hyjf.am.config.dao.mapper.customize.AdminUtmReadPermissionsCustomMapper;
import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.dao.model.auto.AdminExample;
import com.hyjf.am.config.dao.model.auto.AdminUtmReadPermissions;
import com.hyjf.am.config.dao.model.auto.AdminUtmReadPermissionsExample;
import com.hyjf.am.config.service.AdminUtmReadPermissionsService;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
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
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		int count = adminUtmReadPermissionsCustomMapper.countAdminUtmReadPermissionsRecord(request);
		response.setCount(count);
		if(count>0){
			Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize()==0?10:request.getPageSize());
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
	/**
	 * 用户是否存在
	 * @param userName
	 * @return
	 * @author Michael
	 */

	@Override
	public int isExistsAdminUser(String userName) {
		int usersFlag = 0;
		AdminExample example = new AdminExample();
		AdminExample.Criteria cra = example.createCriteria();
		cra.andUsernameEqualTo(userName);
		List<Admin> userList = this.adminMapper.selectByExample(example);
		// 用户名不存在
		if (userList == null || userList.size() == 0) {
			usersFlag = 1;
			return usersFlag;
		}
		Admin users = userList.get(0);
		//用户已经被禁用
		if(users.getState().equals("1")){
			usersFlag = 2;
			return usersFlag;
		}
		AdminUtmReadPermissionsExample utmexample = new AdminUtmReadPermissionsExample();
		AdminUtmReadPermissionsExample.Criteria utmcra = utmexample.createCriteria();
		utmcra.andAdminUserNameEqualTo(userName);
		List<AdminUtmReadPermissions> utmuserList = this.adminUtmReadPermissionsMapper.selectByExample(utmexample);
		//该记录已存在
		if(utmuserList != null && utmuserList.size() > 0){
			usersFlag = 3;
		}
		return usersFlag;
	}
	@Override
	public void updateAction(AdminUtmReadPermissionsRequest request) {
		AdminUtmReadPermissions record = new AdminUtmReadPermissions();
		BeanUtils.copyProperties(request, record);
		adminUtmReadPermissionsMapper.updateByPrimaryKey(record);
	}
	@Override
	public AdminUtmReadPermissionsResponse getAdminUtmReadPermissions(AdminUtmReadPermissionsRequest request){
		AdminUtmReadPermissionsResponse response = new AdminUtmReadPermissionsResponse();
		if(StringUtils.isNotBlank(request.getId())){
			AdminUtmReadPermissionsVO vo = new AdminUtmReadPermissionsVO();
			AdminUtmReadPermissions adminUtmReadPermissions = adminUtmReadPermissionsMapper.selectByPrimaryKey(Integer.valueOf(request.getId()));
			BeanUtils.copyProperties(adminUtmReadPermissions, vo);
			response.setResult(vo);
		}
		return response;
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
