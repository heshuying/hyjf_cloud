package com.hyjf.cs.message.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.hyjf.am.vo.user.UserAliasVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
@Repository
public class AmUserClient {

	@Autowired
	private GenericRest rest;

	@Value("${am.user.service.name}")
	private String amUserServiceName;

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	public UserVO findUserByMobile(final String mobile) {
		RestResponse<UserVO> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amUserServiceName, "/am-user/user/findByMobile/" + mobile);
			ResponseEntity<RestResponse<UserVO>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<UserVO>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	/**
	 * 根据userId查询用户
	 * 
	 * @param userId
	 * @return
	 */
	public UserVO findUserById(final int userId) {
		RestResponse<UserVO> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amUserServiceName, "/am-user/user/findById/" + userId);
			ResponseEntity<RestResponse<UserVO>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<UserVO>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	/**
	 * 根据手机号查询推送别名
	 * 
	 * @param mobile
	 * @return
	 */
	public UserAliasVO findAliasByMobile(final String mobile) {
		RestResponse<UserAliasVO> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amUserServiceName, "/am-user/user/findAliasByMobile/" + mobile);
			ResponseEntity<RestResponse<UserAliasVO>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<UserAliasVO>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	/**
	 * 根据手机号查询推送别名 - 批量
	 * 
	 * @param mobiles
	 * @return
	 */
	public List<UserAliasVO> findAliasesByMobiles(final List<String> mobiles) {
		RestResponse<List<UserAliasVO>> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amUserServiceName, "/am-user/user/findAliasesByMobiles/");
			ResponseEntity<RestResponse<List<UserAliasVO>>> responseEntity = rest.post(url, mobiles,
					new ParameterizedTypeReference<RestResponse<List<UserAliasVO>>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}

	/**
	 * 根据设备类型统计用户人数
	 * 
	 * @param clientAndroid
	 * @return
	 */
	public int countAliasByClient(String clientAndroid) {
		RestResponse<Integer> resp = Rests.exc(() -> {
			String url = Rests.toUrl(amUserServiceName, "/am-user/user/findSmsTemplateByCode/" + clientAndroid);
			ResponseEntity<RestResponse<Integer>> responseEntity = rest.get(url,
					new ParameterizedTypeReference<RestResponse<Integer>>() {
					});
			return responseEntity.getBody();
		});
		return resp.getResult();
	}
}
