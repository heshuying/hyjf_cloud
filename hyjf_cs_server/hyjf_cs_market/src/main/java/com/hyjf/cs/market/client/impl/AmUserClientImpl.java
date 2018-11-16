package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import com.hyjf.am.response.user.SmsCodeResponse;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xiasq
 * @version AmUserClientImpl, v0.1 2018/7/6 11:04
 */
@Cilent
public class AmUserClientImpl implements AmUserClient {
	@Autowired
	private RestTemplate restTemplate;
	@Value("${am.user.service.name}")
	private String userService;

	/**
	 * 查询投之家注册人数、开户人数、绑卡人数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public TzjDayReportVO queryUserDataOnToday(Date startTime,Date endTime) {
		TzjDayReportRequest request = new TzjDayReportRequest();
		request.setStartTime(startTime);
		request.setEndTime(endTime);

		TzjDayReportResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/tzj/queryUserDataOnToday", request, TzjDayReportResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询投之家注册用户
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public Set<Integer> queryRegisterUsersOnToday(Date startTime,Date endTime) {
		TzjDayReportRequest request = new TzjDayReportRequest();
		request.setStartTime(startTime);
		request.setEndTime(endTime);

		TzjDayReportResponse response = restTemplate.postForEntity(
				"http://AM-USER/am-user/tzj/queryRegisterUsersOnToday", request, TzjDayReportResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getUserIds();
		}
		return null;
	}

	/**
	 * 查询投之家所有注册用户
	 * 
	 * @return
	 */
	@Override
	public Set<Integer> queryAllTzjUsers() {
		TzjDayReportResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/tzj/queryAllTzjUsers", TzjDayReportResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getUserIds();
		}
		return null;
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
	public Integer getAccessNumber(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getaccessnumber/" + sourceId,
				UtmResponse.class);
		if (response != null) {
			return response.getAccessNumber();
		}
		return null;
	}

	@Override
	public Integer getRegistNumber(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getregistnumber/" + sourceId,
				UtmResponse.class);
		if (response != null) {
			return response.getRegistNumber();
		}
		return null;
	}

	@Override
	public Integer getOpenAccountNumber(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getopenaccountnumber/" + sourceId+"/"+type,
				UtmResponse.class);
		if (response != null) {
			return response.getOpenAccountNumber();
		}
		return null;
	}

	@Override
	public Integer getTenderNumber(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gettendernumber/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getTenderNumber();
        }
        return null;
	}

	@Override
	public BigDecimal getCumulativeRecharge(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getcumulativerecharge/" + sourceId+"/"+type,
				UtmResponse.class);
		if (response != null) {
			return response.getCumulativeRecharge();
		}
		return null;
	}

	@Override
	public BigDecimal getHztTenderPrice(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethzttenderprice/" + sourceId+"/"+type,
				UtmResponse.class);
		if (response != null) {
			return response.getHztTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getHxfTenderPrice(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethxftenderprice/" + sourceId+"/"+type,
				UtmResponse.class);
		if (response != null) {
			return response.getHxfTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getHtlTenderPrice(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethtltenderprice/" + sourceId+"/"+type,
				UtmResponse.class);
		if (response != null) {
			return response.getHtlTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getHtjTenderPrice(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethtjtenderprice/" + sourceId+"/"+type,
				UtmResponse.class);
		if (response != null) {
			return response.getHtjTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getRtbTenderPrice(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getrtbtenderprice/" + sourceId+"/"+type,
				UtmResponse.class);
		if (response != null) {
			return response.getRtbTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getHzrTenderPrice(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethzrtenderprice/" + sourceId+"/"+type,
				UtmResponse.class);
		if (response != null) {
			return response.getHzrTenderPrice();
		}
		return null;
	}

	@Override
	public Integer getOpenAccountAttrCount(Integer sourceId) {
		// 0无主单
		UtmResponse response = restTemplate.getForObject(
				"http://AM-USER/am-user/promotion/utm/getopenaccountnumber/" + sourceId + "/0", UtmResponse.class);
		if (response != null) {
			return response.getOpenAccountNumber();
		}
		return null;
	}

	/**
	 * 修改短信与邮件是否开启状态
	 * @param userId
	 * @param smsOpenStatus
	 * @param emailOpenStatus
	 * @return
	 */
    @Override
    public Integer updateStatusByUserId(Integer userId, String smsOpenStatus, String emailOpenStatus) {
		Integer result = restTemplate.getForObject("http://AM-USER/am-user/user/updateStatusByUserId/" + userId + "/" + smsOpenStatus + "/" + emailOpenStatus, Integer.class);
		return result;
    }

	/**
	 * 查询千乐用户
	 * @return
	 */
	@Override
    public List<Integer> getQianleUser() {
		return restTemplate.getForObject("http://AM-ADMIN/am-user/user/getQianleUser", List.class);
    }


	/**
	 * 保存验证码
	 * @param mobile
	 * @param checkCode
	 * @param validCodeType
	 * @param status
	 * @param platform
	 * @return
	 */
	@Override
	public int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(checkCode);
		request.setVerificationType(validCodeType);
		request.setStatus(status);
		request.setPlatform(platform);
		SmsCodeResponse response = restTemplate
				.postForEntity(userService+"/smsCode/save", request, SmsCodeResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getCnt();
		} else {
			throw new RuntimeException("发送验证码失败...");
		}
	}

	@Override
	public int onlyCheckMobileCode(String mobile, String code) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(code);
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/smsCode/qianle_check/", request, IntegerResponse.class)
				.getBody().getResultInt();
		if (result == null) {
			return 0;
		}
		return result;
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
	public List<Integer> getUsersList(String source) {
		IntegerResponse response = restTemplate
				.getForObject("http://AM-USER/am-user/channel/getUsersList/"+source, IntegerResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
}
