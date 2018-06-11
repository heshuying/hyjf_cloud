package com.hyjf.callcenter.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.beans.customizebean.CallcenterUserBaseCustomize;
import com.hyjf.callcenter.client.AmCallcenterUserInfoClient;
import com.hyjf.callcenter.service.UserInfoService;
import com.hyjf.ribbon.EurekaInvokeClient;

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
	public List<CallcenterUserBaseCustomize> getNoServiceUsersList(UserBean bean) {
		// 封装查询条件
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("limitStart", bean.getLimitStart());
		conditionMap.put("limitEnd", bean.getLimitSize());
		List<CallcenterUserBaseCustomize> users = null;
		
		
		
		
		
		
		/*暂时停工涉及到跨库查询，目前没有定论，暂时先做到这一步  start*/
		if (bean.getFlag().equals("1")) {
			// 复投用户筛选---参考  CallcenterUserInfoCustomizeMapper.xml 的 selectNoServiceFuTouUsersList 查询
			users = this.amCallcenterUserInfoClient.selectNoServiceFuTouUsersList(conditionMap);
		}else if(bean.getFlag().equals("2")){
			// 流失用户筛选
			users = this.amCallcenterUserInfoClient.selectNoServiceLiuShiUsersList(conditionMap);
		} else {
			// 查询用户列表
			users = this.amCallcenterUserInfoClient.selectNoServiceUsersList(conditionMap);
		}
		/*暂时停工涉及到跨库查询，目前没有定论，暂时先做到这一步  end*/
		
		
		
		
		return users;
	}

}
