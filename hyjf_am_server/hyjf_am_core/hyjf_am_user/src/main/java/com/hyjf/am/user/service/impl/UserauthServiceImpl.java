package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.customize.AdminUserAuthCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthLogListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserRePayAuthCustomize;
import com.hyjf.am.user.service.UserauthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserauthServiceImpl  implements UserauthService {
	@Autowired
	private AdminUserAuthCustomizeMapper adminUserAuthCustomizeMapper;
	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	public List<AdminUserAuthListCustomize> getRecordList(Map<String, Object> authUser, int limitStart, int limitEnd) {

		if (limitStart == 0 || limitStart > 0) {
			authUser.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			authUser.put("limitEnd", limitEnd);
		}
		// 查询用户列表
		List<AdminUserAuthListCustomize> users = adminUserAuthCustomizeMapper.selectUserAuthList(authUser);
		return users;
	}

	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * 
	 * @param authUser
	 * @return
	 * @author Administrator
	 */

	@Override
	public int countRecordTotal(Map<String, Object> authUser) {
		// 查询用户列表
		int countTotal = adminUserAuthCustomizeMapper.countRecordTotal(authUser);
		return countTotal;

	}


    @Override
    public int countRecordTotalLog(Map<String, Object> authUser) {
        // 查询用户列表
        int countTotal = adminUserAuthCustomizeMapper.countRecordTotalLog(authUser);
        return countTotal;
    }

    @Override
    public List<AdminUserAuthLogListCustomize> getRecordListLog(Map<String, Object> authUser, int limitStart, int limitEnd) {
        if (limitStart == 0 || limitStart > 0) {
			authUser.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
			authUser.put("limitEnd", limitEnd);
        }
        // 查询用户列表
        List<AdminUserAuthLogListCustomize> users = adminUserAuthCustomizeMapper.selectUserAuthLogList(authUser);
        return users;
    }

	@Override
	public int countRecordTotalPay(Map<String, Object> authUser) {
		
		int countTotal = adminUserAuthCustomizeMapper.countRecordTotalPay(authUser);
		return countTotal;
	}

	@Override
	public List<AdminUserPayAuthCustomize> getRecordListPay(Map<String, Object> authUser, int limitStart,
			int limitEnd) {
		if (limitStart == 0 || limitStart > 0) {
			authUser.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			authUser.put("limitEnd", limitEnd);
		}
		// 查询用户列表
		List<AdminUserPayAuthCustomize> users = adminUserAuthCustomizeMapper.selectUserPayAuthList(authUser);
		return users;
	}

	@Override
	public void updatePayAuthRecord(int id, String signEndDate, int authtype) {
		adminUserAuthCustomizeMapper.updatePayAuthRecord(id,signEndDate,authtype);
	}
	

	@Override
	public int countRecordTotalRePay(Map<String, Object> authUser) {
		int countTotal = adminUserAuthCustomizeMapper.countRecordTotalRePay(authUser);
		return countTotal;
	}

	@Override
	public List<AdminUserRePayAuthCustomize> getRecordListRePay(Map<String, Object> authUser, int limitStart,
			int limitEnd) {
		if (limitStart == 0 || limitStart > 0) {
			authUser.put("limitStart", limitStart);
		}
		if (limitEnd > 0) {
			authUser.put("limitEnd", limitEnd);
		}
		// 查询用户列表
		List<AdminUserRePayAuthCustomize> users = adminUserAuthCustomizeMapper.selectUserRePayAuthList(authUser);
		return users;
	}

	@Override
	public void updateRePayAuthRecord(int id, String signEndDate, int authtype) {
		adminUserAuthCustomizeMapper.updateRePayAuthRecord(id,signEndDate,authtype);
	}

	@Override
	public int isDismissPay(int userid) {
//		return adminUserAuthCustomizeMapper.selectCanDismissPay(userid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userid);
        params.put("roleId", 2);
        params.put("status", 0);
	//	return webUserRepayListCustomizeMapper.countUserPayProjectRecordTotal(params);
        return 0;
	}

	@Override
	public int isDismissRePay(int userid) {
		return adminUserAuthCustomizeMapper.selectCanDismissRePay(userid);
	}
	
	

}
