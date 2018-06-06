package com.hyjf.callcenter.serviceImpl;

import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.beans.Users;
import com.hyjf.callcenter.service.UserService;
import com.hyjf.ribbon.EurekaInvokeClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author libin
 * @version UserServiceImpl, v0.1 2018/6/5
 */
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();

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
}
