package com.hyjf.callcenter.clientImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.callcenter.beans.customizebean.CallcenterUserBaseCustomize;
import com.hyjf.callcenter.client.AmCallcenterUserInfoClient;
import com.hyjf.ribbon.EurekaInvokeClient;

/**
 * @author libin
 * @version AmCallcenterUserInfoClientImpl, v0.1 2018/6/6 10:03
 */
@Service
public class AmCallcenterUserInfoClientImpl implements AmCallcenterUserInfoClient {
    private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();
    
	@Override
	public List<CallcenterUserBaseCustomize> selectNoServiceFuTouUsersList(Map<String, Object> user) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	
	
	@Override
	public List<CallcenterUserBaseCustomize> selectNoServiceLiuShiUsersList(Map<String, Object> user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallcenterUserBaseCustomize> selectNoServiceUsersList(Map<String, Object> user) {
		// TODO Auto-generated method stub
		return null;
	}

}
