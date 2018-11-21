package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.WrbTenderNotifyResponse;
import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmTradeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/7/6 11:21
 */
@Cilent
public class AmTradeClientImpl implements AmTradeClient {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 查询投之家每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
	 * 
	 * @param tzjUserIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public TzjDayReportVO queryTradeDataOnToday(Set<Integer> tzjUserIds, Date startTime, Date endTime) {
		TzjDayReportRequest request = new TzjDayReportRequest();
		request.setTzjUserIds(tzjUserIds);
		request.setStartTime(startTime);
		request.setEndTime(endTime);

		TzjDayReportResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/tzj/queryTradeDataOnToday",
				request, TzjDayReportResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 新充人数 新投人数
	 * 
	 * @param registerUserIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public TzjDayReportVO queryTradeNewDataOnToday(Set<Integer> registerUserIds, Date startTime, Date endTime) {
		TzjDayReportRequest request = new TzjDayReportRequest();
		request.setTzjUserIds(registerUserIds);
		request.setStartTime(startTime);
		request.setEndTime(endTime);

		TzjDayReportResponse response = restTemplate.postForEntity(
				"http://AM-TRADE/am-trade/tzj/queryTradeNewDataOnToday", request, TzjDayReportResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<TenderCityCountVO> getTenderCityGroupBy(Date lastDay) {
		return null;
	}

	@Override
	public List<TenderSexCountVO> getTenderSexGroupBy(Date lastDay) {
		return null;
	}

	@Override
	public int getTenderAgeByRange(Date date, int firstAge, int endAge) {
		return 0;
	}

	@Override
	public Integer countBorrowUser() {
		IntegerResponse response = restTemplate.getForObject(
				"http://AM-TRADE/am-trade/borrow_user_statistics/count_borrow_user", IntegerResponse.class);
		if (response != null) {
			return response.getResultInt();
		}
		return 0;
	}

	@Override
	public Integer countCurrentBorrowUser() {
        IntegerResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/borrow_user_statistics/count_current_borrow_user", IntegerResponse.class);
        if (response != null) {
            return response.getResultInt();
        }
        return 0;
	}

	@Override
	public Integer countCurrentTenderUser() {
        IntegerResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/borrow_user_statistics/count_current_tender_user", IntegerResponse.class);
        if (response != null) {
            return response.getResultInt();
        }
        return 0;
	}

	@Override
	public BigDecimal sumBorrowUserMoney(Date lastDay) {
        BigDecimalResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/borrow_user_statistics/sum_borrow_user_money", BigDecimalResponse.class);
        if (response != null) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal sumBorrowUserMoneyTopTen() {
        BigDecimalResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/borrow_user_statistics/sum_borrow_user_money_top_ten", BigDecimalResponse.class);
        if (response != null) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal sumBorrowUserMoneyTopOne() {
        BigDecimalResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/borrow_user_statistics/sum_borrow_user_money_top_one", BigDecimalResponse.class);
        if (response != null) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
	}

	/**
	 * 查询千乐散标数据
	 * @param dataSearchRequest
	 * @return
	 */
    @Override
    public DataSearchCustomizeResponse querySanList(DataSearchRequest dataSearchRequest) {
		return restTemplate.postForEntity("http://AM-TRADE/am-trade/qianle/querysanlist", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();
	}
	/**
	 * 查询千乐计划数据
	 * @param dataSearchRequest
	 * @return
	 */
	@Override
	public DataSearchCustomizeResponse queryPlanList(DataSearchRequest dataSearchRequest) {
		return restTemplate.postForEntity("http://AM-TRADE/am-trade/qianle/queryPlanList", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();

	}
	/**
	 * 查询千乐全部数据
	 * @param dataSearchRequest
	 * @return
	 */
	@Override
	public DataSearchCustomizeResponse queryQianleList(DataSearchRequest dataSearchRequest) {
		return restTemplate.postForEntity("http://AM-TRADE/am-trade/qianle/queryList", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();

	}

	@Override
	public Map<String,Object> querySanMoney(DataSearchRequest dataSearchRequest) {
		DataSearchCustomizeResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/qianle/querySanMoney", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();
		return response.getMoney();
	}

	@Override
	public Map<String,Object> queryPlanMoney(DataSearchRequest dataSearchRequest) {
		DataSearchCustomizeResponse response =restTemplate.postForEntity("http://AM-TRADE/am-trade/qianle/queryPlanMoney", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();
		return response.getMoney();
	}

	/**
	 *  汇直投投资总额
	 */
	@Override
	public List<WrbTenderNotifyCustomizeVO> getBorrowTenderByAddtime(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getBorrowTenderByAddtime",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 *  汇转让投资总额
	 */
	@Override
	public List<WrbTenderNotifyCustomizeVO> getCreditTenderByAddtime(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getCreditTenderByAddtime",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 *  app渠道用户充值金额
	 */
	@Override
	public List<WrbTenderNotifyCustomizeVO> getAccountRechargeByAddtime(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getAccountRechargeByAddtime",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 *  1
	 */
	@Override
	public List<WrbTenderNotifyCustomizeVO> getBorrowTenderByClient(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getBorrowTenderByClient",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 *  2
	 */
	@Override
	public List<WrbTenderNotifyCustomizeVO> getProductListByClient(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getProductListByClient",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 *  3
	 */
	@Override
	public List<WrbTenderNotifyCustomizeVO> getDebtPlanAccedeByClient(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getDebtPlanAccedeByClient",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 *  4
	 */
	@Override
	public List<WrbTenderNotifyCustomizeVO> getCreditTenderByClient(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getCreditTenderByClient",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}


}
