package com.hyjf.callcenter.service.impl;

import java.util.List;

import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import com.hyjf.am.vo.callcenter.CallCenterServiceUsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.client.AmCallcenterUserInfoClient;
import com.hyjf.callcenter.service.UserInfoService;

/**
 * @author libin
 * @version UserServiceImpl, v0.1 2018/6/5
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	/*private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();*/
	@Autowired
	private AmCallcenterUserInfoClient amCallcenterUserInfoClient;

	@Override
	public List<CallCenterUserBaseVO> getNoServiceUsersList(UserBean bean) {
		// 封装查询条件
		CallCenterUserInfoRequest callCenterUserInfoRequest = new CallCenterUserInfoRequest();
		callCenterUserInfoRequest.setLimitStart(bean.getLimitStart());
		callCenterUserInfoRequest.setLimitSize(bean.getLimitSize());
/*		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("limitStart", bean.getLimitStart());
		conditionMap.put("limitEnd", bean.getLimitSize());*/
		List<CallCenterUserBaseVO> users = null;
		/*此接口情况特殊，直接是跨库查询  start*/
		if ("1".equals(bean.getFlag())) {
			// 复投用户筛选---参考  CallcenterUserInfoCustomizeMapper.xml 的 selectNoServiceFuTouUsersList 查询
			users = this.amCallcenterUserInfoClient.selectNoServiceFuTouUsersList(callCenterUserInfoRequest);
		}else if("2".equals(bean.getFlag())){
			// 流失用户筛选
			users = this.amCallcenterUserInfoClient.selectNoServiceLiuShiUsersList(callCenterUserInfoRequest);
		} else {
			// 查询用户列表
			users = this.amCallcenterUserInfoClient.selectNoServiceUsersList(callCenterUserInfoRequest);
		}
		/*此接口情况特殊，直接是跨库查询  start*/
		return users;
	}

	@Override
	public Integer executeRecord(List<CallCenterServiceUsersVO> userList){
		CallCenterServiceUsersRequest callCenterServiceUsersRequest = new CallCenterServiceUsersRequest();
		callCenterServiceUsersRequest.setCallCenterServiceUsersVOList(userList);
		return this.amCallcenterUserInfoClient.executeRecord(callCenterServiceUsersRequest);
	}

	@Override
	public List<CallCenterUserBaseVO> getUserBaseList(UserVO user) {
		List<CallCenterUserBaseVO> users = null;
		// 封装查询条件
		CallCenterUserInfoRequest callCenterUserInfoRequest = new CallCenterUserInfoRequest();
		if(user.getUserId() != null){
			callCenterUserInfoRequest.setUserId(user.getUserId());
			users = this.amCallcenterUserInfoClient.selectUserList(callCenterUserInfoRequest);
		}
		return users;
	}

	@Override
	public List<CallCenterUserBaseVO> getUserDetailList(UserVO user) {
		List<CallCenterUserBaseVO> users = null;
		// 封装查询条件
		CallCenterUserInfoRequest callCenterUserInfoRequest = new CallCenterUserInfoRequest();
		if(user.getUserId() != null){
			callCenterUserInfoRequest.setUserId(user.getUserId());
			users = this.amCallcenterUserInfoClient.selectUserDetailById(callCenterUserInfoRequest);
		}
		return users;
	}

}