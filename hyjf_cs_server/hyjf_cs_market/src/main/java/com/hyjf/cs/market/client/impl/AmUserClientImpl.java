package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmUserClient;
import org.springframework.beans.factory.annotation.Autowired;
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
		UtmResponse response = restTemplate.postForObject("http://AM-USER/am-user/promotion/utm/getbypagelist", params,
				UtmResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public Integer getAccessNumber(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-USER/am-user/promotion/utm/getaccessnumber/" + sourceId,
				UtmResponse.class);
		if (response != null) {
			return response.getAccessNumber();
		}
		return null;
	}

	@Override
	public Integer getRegistNumber(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-USER/am-user/promotion/utm/getregistnumber/" + sourceId,
				UtmResponse.class);
		if (response != null) {
			return response.getRegistNumber();
		}
		return null;
	}

	@Override
	public Integer getOpenAccountNumber(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject("http://AM-USER/am-user/promotion/utm/getopenaccountnumber/" + sourceId,
				UtmResponse.class);
		if (response != null) {
			return response.getOpenAccountNumber();
		}
		return null;
	}

	@Override
	public Integer getTenderNumber(Integer sourceId, String type) {
		return null;// todo
	}

	@Override
	public BigDecimal getCumulativeRecharge(Integer sourceId, String type) {
		return null;
	}

	@Override
	public BigDecimal getHztTenderPrice(Integer sourceId, String type) {
		return null;
	}

	@Override
	public BigDecimal getHxfTenderPrice(Integer sourceId, String type) {
		return null;
	}

	@Override
	public BigDecimal getHtlTenderPrice(Integer sourceId, String type) {
		return null;
	}

	@Override
	public BigDecimal getHtjTenderPrice(Integer sourceId, String type) {
		return null;
	}

	@Override
	public BigDecimal getRtbTenderPrice(Integer sourceId, String type) {
		return null;
	}

	@Override
	public BigDecimal getHzrTenderPrice(Integer sourceId, String type) {
		return null;
	}

	@Override
	public BigDecimal getRegisterAttrCount(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getAccountNumberIos(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getAccountNumberPc(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getAccountNumberAndroid(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getAccountNumberWechat(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getTenderNumberAndroid(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getTenderNumberIos(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getTenderNumberPc(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getTenderNumberWechat(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal getCumulativeAttrCharge(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getOpenAccountAttrCount(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getInvestAttrNumber(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal getCumulativeAttrInvest(Integer sourceId) {
		return null;
	}
}
