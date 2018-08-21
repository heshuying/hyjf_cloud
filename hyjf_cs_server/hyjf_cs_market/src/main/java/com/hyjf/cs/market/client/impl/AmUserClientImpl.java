package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import com.hyjf.am.response.market.UtmRegResponse;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.response.app.AppChannelStatisticsDetailResponse;
import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.CreditTenderResponse;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
		// 获取utm注册用户id
		List<Integer> list = geUtmRegUserIdtList(sourceId, "1");
		BorrowTenderResponse tenderResponse = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/borrowTender/getutmtendernum", list, BorrowTenderResponse.class);
		if (tenderResponse != null) {
			return tenderResponse.getTenderCount();
		}
		return null;
	}

	@Override
	public BigDecimal getCumulativeRecharge(Integer sourceId, String type) {
		// 获取utm注册用户id
		List<Integer> list = geUtmRegUserIdtList(sourceId, "1");
		AccountRechargeResponse response = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/accountrecharge/getrechargeprice", list, AccountRechargeResponse.class);
		if (response != null) {
			return response.getRechargePrice();
		}
		return null;
	}

	@Override
	public BigDecimal getHztTenderPrice(Integer sourceId, String type) {
		// 获取utm注册用户id
		List<Integer> list = geUtmRegUserIdtList(sourceId, "1");
		BorrowTenderResponse tenderResponse = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/borrowTender/gethzttenderprice", list, BorrowTenderResponse.class);
		if (tenderResponse != null) {
			return tenderResponse.getHztTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getHxfTenderPrice(Integer sourceId, String type) {
		// 获取utm注册用户id
		List<Integer> list = geUtmRegUserIdtList(sourceId, "1");
		BorrowTenderResponse tenderResponse = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/borrowTender/gethxftenderprice", list, BorrowTenderResponse.class);
		if (tenderResponse != null) {
			return tenderResponse.getHxfTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getHtlTenderPrice(Integer sourceId, String type) {
		return null;// todo
	}

	@Override
	public BigDecimal getHtjTenderPrice(Integer sourceId, String type) {
		return null;// todo
	}

	@Override
	public BigDecimal getRtbTenderPrice(Integer sourceId, String type) {
		// 获取utm注册用户id
		List<Integer> list = geUtmRegUserIdtList(sourceId, "1");
		BorrowTenderResponse tenderResponse = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/borrowTender/getrtbtenderprice", list, BorrowTenderResponse.class);
		if (tenderResponse != null) {
			return tenderResponse.getHxfTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getHzrTenderPrice(Integer sourceId, String type) {
		// 获取utm注册用户id
		List<Integer> list = geUtmRegUserIdtList(sourceId, "1");
		CreditTenderResponse response = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/creditTender/gethzrtenderprice", list, CreditTenderResponse.class);
		if (response != null) {
			return response.getHzrTenderPrice();
		}
		return null;
	}

	@Override
	public BigDecimal getRegisterAttrCount(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		UtmRegResponse response = restTemplate.postForObject(
				"http://AM-USER/am-user/promotion/utmreg/getregisterattrcount", userIdList, UtmRegResponse.class);
		if (response != null) {
			return response.getRegisterAttrCount();
		}
		return null;
	}



	@Override
	public Integer getAccountNumberIos(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		UtmRegResponse response = restTemplate.postForObject(
				"http://AM-USER/am-user/promotion/utmreg/getaccountnumberios", userIdList, UtmRegResponse.class);
		if (response != null) {
			return response.getAccountNumberIos();
		}
		return null;
	}

	@Override
	public Integer getAccountNumberPc(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		UtmRegResponse response = restTemplate.postForObject(
				"http://AM-USER/am-user/promotion/utmreg/getaccountnumberpc", userIdList, UtmRegResponse.class);
		if (response != null) {
			return response.getAccountNumberPc();
		}
		return null;
	}

	@Override
	public Integer getAccountNumberAndroid(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		UtmRegResponse response = restTemplate.postForObject(
				"http://AM-USER/am-user/promotion/utmreg/getaccountnumberandroid", userIdList, UtmRegResponse.class);
		if (response != null) {
			return response.getAccountNumberAndroid();
		}
		return null;
	}

	@Override
	public Integer getAccountNumberWechat(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		UtmRegResponse response = restTemplate.postForObject(
				"http://AM-USER/am-user/promotion/utmreg/getaccountnumberwechat", userIdList, UtmRegResponse.class);
		if (response != null) {
			return response.getAccountNumberWechat();
		}
		return null;
	}

	@Override
	public Integer getTenderNumberAndroid(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		BorrowTenderResponse response = restTemplate.postForObject(
				"http://AM-USER/am-trade/borrowTender/gettendernumberandroid", userIdList, BorrowTenderResponse.class);
		if (response != null) {
			return response.getTenderNumberAndroid();
		}
		return null;
	}

	@Override
	public Integer getTenderNumberIos(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		BorrowTenderResponse response = restTemplate.postForObject(
				"http://AM-USER/am-trade/borrowTender/gettendernumberios", userIdList, BorrowTenderResponse.class);
		if (response != null) {
			return response.getTenderNumberIos();
		}
		return null;
	}

	@Override
	public Integer getTenderNumberPc(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		BorrowTenderResponse response = restTemplate.postForObject(
				"http://AM-USER/am-trade/borrowTender/gettendernumberpc", userIdList, BorrowTenderResponse.class);
		if (response != null) {
			return response.getTenderNumberPc();
		}
		return null;
	}

	@Override
	public Integer getTenderNumberWechat(Integer sourceId) {
		// 获取所有app渠道用户id
		List<Integer> userIdList = getIAppChannerUserIdList();

		BorrowTenderResponse response = restTemplate.postForObject(
				"http://AM-USER/am-trade/borrowTender/gettendernumberwechat", userIdList, BorrowTenderResponse.class);
		if (response != null) {
			return response.getTenderNumberWechat();
		}
		return null;
	}

	@Override
	public BigDecimal getCumulativeAttrCharge(Integer sourceId) {
		List<Integer> list = geUtmRegUserIdtList(sourceId, "0");
		AccountRechargeResponse response = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/accountrecharge/getrechargeprice", list, AccountRechargeResponse.class);
		if (response != null) {
			return response.getRechargePrice();
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

	@Override
	public Integer getInvestAttrNumber(Integer sourceId) {
		// 0无主单
		List<Integer> list = geUtmRegUserIdtList(sourceId, "0");
		BorrowTenderResponse tenderResponse = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/borrowTender/getutmtendernum", list, BorrowTenderResponse.class);
		if (tenderResponse != null) {
			return tenderResponse.getTenderCount();
		}
		return null;
	}

	@Override
	public BigDecimal getCumulativeAttrInvest(Integer sourceId) {
		// 获取utm注册用户id 0无主单
		List<Integer> list = geUtmRegUserIdtList(sourceId, "0");
		BorrowTenderResponse tenderResponse = restTemplate.postForObject(
				"http://AM-TRADE/am-trade/borrowTender/gethzttenderprice", list, BorrowTenderResponse.class);
		if (tenderResponse != null) {
			return tenderResponse.getHztTenderPrice();
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
	 * 获取渠道用户userid集合
	 * @param type 0无主单 1有主单
	 * @return
	 */
	private List<Integer> geUtmRegUserIdtList(Integer sourceId, String type) {
		UtmResponse response = restTemplate.getForObject(
				"http://AM-USER/am-user/promotion/utmreg/getutmreglist/" + sourceId + "/" + type, UtmResponse.class);
		List<Integer> userIdList = new ArrayList<>();
		if (response != null) {
			List<UtmRegVO> list = response.getResultList();
			for (UtmRegVO vo : list) {
				userIdList.add(vo.getUserId());
			}
		}
		return userIdList;
	}

	/**
	 * 获取app渠道注册用户id集合
	 * @return
	 */
	private List<Integer> getIAppChannerUserIdList() {
		AppChannelStatisticsDetailResponse detailResponse = restTemplate.getForObject(
				"http://CS-MESSAGE/am-statistics/getappchannelstatisticsdetail",
				AppChannelStatisticsDetailResponse.class);
		List<Integer> userIdList = new ArrayList<>();
		if (detailResponse != null) {
			List<AppChannelStatisticsDetailVO> resultList = detailResponse.getResultList();
			if (!CollectionUtils.isEmpty(resultList)) {
				for (AppChannelStatisticsDetailVO vo : resultList) {
					userIdList.add(vo.getUserId());
				}
			}
		}
		return userIdList;
	}
}
