package com.hyjf.cs.message.client.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.response.trade.OperationReportJobResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.resquest.message.FindAliasesForMsgPushRequest;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.message.client.AmUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author xiasq
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */
@Cilent
public class AmUserClientImpl implements AmUserClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${am.user.service.name}")
	private String amUserServiceName;

	/**
	 * 根据手机号查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	@Override
	public UserVO findUserByMobile(final String mobile) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findByMobile/" + mobile, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public  int getTenderAgeByRange(Date date, int firstAge, int endAge, List<OperationReportJobVO> ageRangeUserIds) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		request.setFirstAge(firstAge);
		request.setEndAge(endAge);
		request.setOperationReportJobVOList(ageRangeUserIds);
		OperationReportJobResponse response = restTemplate.postForObject("http://AM-USER/am-user/batch/operation_report_job/tenderagebyrange",
				request, OperationReportJobResponse.class);
		if(response != null ){
			return response.getCount();
		}
		return 0;
	}

	@Override
	public int countRegistUser(){
		OperationReportJobResponse response =  restTemplate.getForEntity("http://AM-USER/am-user/batch/operation_report_job/countregistuser", OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getCount();
		}
		return 0;
	}
	@Override
	public List<OperationReportJobVO> getAgeCount(List<OperationReportJobVO> list){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setOperationReportJobVOList(list);
		OperationReportJobResponse response =  restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/agecount",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getUserNames( List<OperationReportJobVO> list){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setOperationReportJobVOList(list);
		OperationReportJobResponse response =  restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/usernames",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public OperationReportJobVO getUserAgeAndArea(Integer userId){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setUserId(userId);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/userageandarea",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getSexCount(List<OperationReportJobVO> list){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setOperationReportJobVOList(list);
		OperationReportJobResponse response =  restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/sexcount",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据userId查询用户
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public UserVO findUserById(final int userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findById/" + userId, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoVO findUsersInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据手机号查询推送别名
	 * 
	 * @param mobile
	 * @return
	 */
	@Override
	public UserAliasVO findAliasByMobile(final String mobile) {
		UserAliasResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userAlias/findAliasByMobile/" + mobile, UserAliasResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据手机号查询推送别名 - 批量
	 * 
	 * @param mobiles
	 * @return
	 */
	@Override
	public List<UserAliasVO> findAliasesByMobiles(final List<String> mobiles) {
		FindAliasesForMsgPushRequest request = new FindAliasesForMsgPushRequest();
		request.setMobiles(mobiles);
		UserAliasResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/userAlias/findAliasesByMobiles",request, UserAliasResponse.class)
				.getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据设备类型统计用户人数
	 * 
	 * @param clientAndroid
	 * @return
	 */
	@Override
	public int countAliasByClient(String clientAndroid) {
		UserAliasResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userAlias/countAliasByClient/" + clientAndroid,
						UserAliasResponse.class)
				.getBody();
		if (response != null) {
			return response.getCount();
		}
		return 0;
	}
	/**
	 * 查看用户详情
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoCustomizeVO queryUserInfoCustomizeByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/userInfo/queryUserInfoCustomizeByUserId/" + userId;
		UserInfoCustomizeResponse response = restTemplate.getForEntity(url,UserInfoCustomizeResponse.class).getBody();
		if (response!=null){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 获取用户表总记录数
	 *
	 * @return
	 */
	@Override
	public Integer countAllUser(){
		UserResponse response = restTemplate.getForEntity("http://AM-USER/am-user/user/countAll",UserResponse.class).getBody();
		if (!Response.isSuccess(response)) {
			return 0;
		}
		return response.getCount();
	}

	@Override
	public List<UtmVO> selectUtmPlatList(String type) {
		Map<String, Object> params = new HashMap<>();
		if ("pc".equals(type)) {
			params.put("sourceType", 0);// 渠道0 PC
			params.put("flagType", 0);// 未删除
		} else if ("app".equals(type)) {
			params.put("sourceType", 1);// 渠道1 APP
			params.put("flagType", 0);// 未删除
		}
//		UtmResponse response = restTemplate.postForObject("http://AM-USER/am-user/promotion/utm/getbypagelist",
//				params,
//				UtmResponse.class);
		HttpEntity httpEntity = new HttpEntity(params);
		ResponseEntity<UtmResponse<UtmVO>> response =
				restTemplate.exchange("http://AM-USER/am-user/channel/getbypagelist",
						HttpMethod.POST, httpEntity, new ParameterizedTypeReference<UtmResponse<UtmVO>>() {});

		if (response.getBody() != null) {
			return response.getBody().getResultListS();
		}
		return null;
	}

	@Override
	public int getAppChannelStatisticsDetailVO(AppChannelStatisticsRequest request) {
		IntegerResponse response = restTemplate.postForObject("http://AM-USER/am-user/app_utm_reg/getRegistNumberCount",
				request, IntegerResponse.class);
		return response.getResultInt();
	}

	@Override
	public List<AppUtmRegVO> getAppChannelStatisticsDetailVOList(AppChannelStatisticsRequest request) {
		AppUtmRegResponse response = restTemplate.postForObject("http://AM-USER/am-user/app_utm_reg/getRegistNumber",
				request, AppUtmRegResponse.class);
		return response.getResultList();
	}

	@Override
	public int getOpenAccountAttrCount(AppChannelStatisticsRequest request) {
		IntegerResponse response = restTemplate.postForObject("http://AM-USER/am-user/app_utm_reg/getOpenAccountAttrCount",
				request, IntegerResponse.class);
		return response.getResultInt();
	}

	@Override
	public List<Integer> getUsersList(String source) {
		IntegerResponse response = restTemplate
				.getForObject("http://AM-USER/am-user/channel/getUsersList/"+source, IntegerResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<Integer> getUsersInfoList() {
		IntegerResponse response = restTemplate
				.getForObject("http://AM-USER/am-user/channel/getUsersInfoList", IntegerResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public AppUtmRegResponse getAppUtmRegResponse(AppChannelStatisticsRequest request) {
		return restTemplate.postForObject("http://AM-USER/am-user/app_utm_reg/getRegistNumber",
				request, AppUtmRegResponse.class);
	}

	@Override
	public List<String> queryUserByBirthday(SmsCodeUserRequest request) {
		Response response = restTemplate
				.postForEntity("http://AM-USER/am-user/smsCode/queryUserByBirthday", request, Response.class).getBody();
		if (response != null && response.getResultList() != null) {
			return response.getResultList();
		}
		return new ArrayList<>();
	}

	/**
	 * 通过用户id查询 ca客户编号
	 * @param request
	 * @return
	 */
	@Override
	public List<CertificateAuthorityVO> queryCustomerId(Set<Integer> request) {
		CertificateAuthorityResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/certificateauthority/queryCustomerId", request, CertificateAuthorityResponse.class).getBody();
		if (response != null && response.getResultList() != null) {
			return response.getResultList();
		}
		return new ArrayList<>();
	}
}
