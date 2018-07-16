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
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	public List<UtmVO> selectUtmPlatList() {
		UtmResponse response = restTemplate.postForObject("http://AM-USER//am-user/promotion/utm", null,
				UtmResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public Integer getAccessNumber(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getRegistNumber(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getOpenAccountNumber(Integer sourceId) {
		return null;
	}

	@Override
	public Integer getTenderNumber(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal getCumulativeRecharge(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal getHztTenderPrice(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal getHxfTenderPrice(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal gethtlTenderPrice(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal getHtjTenderPrice(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal getRtbTenderPrice(Integer sourceId) {
		return null;
	}

	@Override
	public BigDecimal getHzrTenderPrice(Integer sourceId) {
		return null;
	}
}
