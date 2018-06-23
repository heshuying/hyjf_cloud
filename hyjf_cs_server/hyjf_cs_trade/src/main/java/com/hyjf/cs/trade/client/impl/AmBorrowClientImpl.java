package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BorrowRepayPlanResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.client.AmBorrowClient;
import com.hyjf.cs.trade.client.AmUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Description 标的相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 13:57
 */

@Service
public class AmBorrowClientImpl implements AmBorrowClient {
	private static Logger logger = LoggerFactory.getLogger(AmBorrowClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @param borrowNid
	 * @Description 根据计划编号查询计划
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 13:53
	 */
	@Override
	public HjhPlanVO getPlanByNid(String borrowNid) {
		HjhPlanResponse response = restTemplate
				.getForEntity("http://AM-USER/am-trade/hjhPlan/getHjhPlanByPlanNid/" + borrowNid, HjhPlanResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 检索正在还款中的标的
	 *
	 * @Author liushouyi
	 * @return
	 */
	@Override
	public List<BorrowVO> selectBorrowList() {
		BorrowResponse response = restTemplate.getForEntity(
				"http://AM-TRADE/am-trade/trade/selectRepayBorrowList/",
				BorrowResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
}
