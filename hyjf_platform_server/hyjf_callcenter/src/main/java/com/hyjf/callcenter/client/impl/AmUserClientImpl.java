package com.hyjf.callcenter.client.impl;

import com.hyjf.am.response.callcenter.CallCenterAccountHuifuResponse;
import com.hyjf.am.response.callcenter.CallCenterUserBaseResponse;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.client.AmUserClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version AmUserClientImpl, v0.1 2018/7/6 17:15
 */

@Service
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<BankCardVO> getTiedCardOfAccountBank(UserVO user) {
		BankCardResponse bankCardResponse = restTemplate
				.getForEntity("http://AM-USER/am-user/callcenter/getTiedCardForBank/" + user.getUserId(), BankCardResponse.class)
				.getBody();
		if (bankCardResponse != null) {
			return bankCardResponse.getResultList();
		}
		return null;
	}

	@Override
	public List<CallcenterAccountHuifuVO> selectBankCardList(
			CallcenterAccountHuifuRequest callcenterAccountHuifuRequest) {
		CallCenterAccountHuifuResponse callCenterAccountHuifuResponse = restTemplate
				.postForEntity("http://AM-USER//am-user/callcenter/getHuifuTiedcardInfo/",callcenterAccountHuifuRequest, CallCenterAccountHuifuResponse.class)
				.getBody();
		if (callCenterAccountHuifuResponse != null) {
			return callCenterAccountHuifuResponse.getResultList();
		}
		return null;
	}

	@Override
	public UserVO getUser(UserBean bean) {
		String condition = null;
		if(StringUtils.isNotBlank(bean.getUserName())){
			condition = bean.getUserName();
		}
		if(StringUtils.isNotBlank(bean.getMobile())){
			condition = bean.getMobile();
		}
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findByCondition/" + condition, UserResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<CallCenterUserBaseVO> selectNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
				.postForEntity("http://AM-USER//am-user/callcenter/getNoServiceFuTouUsersList/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
				.getBody();
		if (callCenterUserBaseResponse != null) {
			return callCenterUserBaseResponse.getResultList();
		}
		return null;
	}

	@Override
	public List<CallCenterUserBaseVO> selectNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
				.postForEntity("http://AM-USER//am-user/callcenter/getNoServiceLiuShiUsersList/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
				.getBody();
		if (callCenterUserBaseResponse != null) {
			return callCenterUserBaseResponse.getResultList();
		}
		return null;
	}

	@Override
	public List<CallCenterUserBaseVO> selectNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
				.postForEntity("http://AM-USER//am-user/callcenter/getNoServiceUsersList/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
				.getBody();
		if (callCenterUserBaseResponse != null) {
			return callCenterUserBaseResponse.getResultList();
		}
		return null;
	}

	@Override
	public Integer executeRecord(CallCenterServiceUsersRequest callCenterServiceUsersRequest){
		Integer response = restTemplate
				.postForEntity("http://AM-USER//am-user/callcenter/executeRecord/",callCenterServiceUsersRequest, Integer.class)
				.getBody();
		return response;
	}

	@Override
	public List<CallCenterUserBaseVO> selectUserList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
				.postForEntity("http://AM-USER//am-user/callcenter/getBasicUsersList/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
				.getBody();
		if (callCenterUserBaseResponse != null) {
			return callCenterUserBaseResponse.getResultList();
		}
		return null;
	}

	@Override
	public List<CallCenterUserBaseVO> selectUserDetailById(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
				.postForEntity("http://AM-USER//am-user/callcenter/getUserDetailById/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
				.getBody();
		if (callCenterUserBaseResponse != null) {
			return callCenterUserBaseResponse.getResultList();
		}
		return null;
	}

	@Override
	public String getCouponContent(String couponCode){
		return restTemplate
				.getForEntity("http://AM-USER/am-user/callcenter/getCouponContent/"+ couponCode,
						String.class).getBody();
	}
}
