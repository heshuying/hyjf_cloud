package com.hyjf.callcenter.serviceImpl;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.callcenter.client.AmCallcenterBaseClient;
import com.hyjf.callcenter.service.UserService;
import com.hyjf.ribbon.EurekaInvokeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AmCallcenterBaseClient amCallcenterBaseClient;

	@Override
	public User getUser(UserBean bean) {
        UserVO userVO = amCallcenterBaseClient.getUser(bean);
        if(userVO != null){
			User user = new User();
			BeanUtils.copyProperties(userVO, user);
			return user;
        }
		return null;
	}
}
