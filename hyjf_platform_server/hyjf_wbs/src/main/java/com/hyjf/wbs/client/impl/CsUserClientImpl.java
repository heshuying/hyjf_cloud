/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.exception.CheckException;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.wbs.client.CsUserClient;
import com.hyjf.wbs.qvo.WechatUserBindVO;
import com.hyjf.wbs.qvo.csuser.LoginRequestVO;
import com.hyjf.wbs.qvo.csuser.LoginResultBean;
import com.hyjf.wbs.qvo.csuser.ResultEnum;
import com.hyjf.wbs.qvo.csuser.WebUserLoginResponse;

/**
 * @author cui
 * @version CsUserClientImpl, v0.1 2019/4/18 18:12
 */
@Service
public class CsUserClientImpl implements CsUserClient {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${cs.user.service.name}")
	private String userService;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public WebViewUserVO login(LoginRequestVO user) {
		String url = userService+"/hyjf-web/user/login";

		WebUserLoginResponse response = restTemplate.postForEntity(url,user, WebUserLoginResponse.class).getBody();

		logger.info("调用cs-user登录返回内容【{}】",JSON.toJSONString(response));

		if (response != null) {
			if (WebResult.SUCCESS.equals(response.getStatus())) {
				try {
					return response.getData();
				} catch (Exception e) {
					logger.error("绑定登录返回数据转换失败！",e);
				}
			}else{
				throw new CheckException(response.getStatus(),response.getStatusDesc());
			}
		}


		throw new CheckException("用户登录失败！");
	}

	@Override
	public WechatUserBindVO wechatLogin(String userName, String password) {
		String url = userService+"/hyjf-wechat/wx/login/doLogin.do?userName="+userName+"&password="+password+"&env=1";
		LoginResultBean loginResultBean=restTemplate.postForEntity(url,null,LoginResultBean.class).getBody();
		if(loginResultBean!=null){
			if(ResultEnum.SUCCESS.getStatus().equals(loginResultBean.getStatus())){
				WechatUserBindVO wechatUserBindVO=new WechatUserBindVO();
				BeanUtils.copyProperties(loginResultBean,wechatUserBindVO);
				return wechatUserBindVO;
			}else{
				throw new CheckException(loginResultBean.getStatus(),loginResultBean.getStatusDesc());
			}
		}else{
			throw new CheckException("微信用户登录失败！");
		}

	}
}
