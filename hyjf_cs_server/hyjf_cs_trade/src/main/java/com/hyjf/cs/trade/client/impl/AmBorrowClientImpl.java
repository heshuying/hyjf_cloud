package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.TenderBgVO;
import com.hyjf.am.vo.trade.borrow.TenderRetMsg;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.cs.trade.client.AmBorrowClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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
				.getForEntity("http://AM-TRADE/am-trade/hjhPlan/getHjhPlanByPlanNid/" + borrowNid, HjhPlanResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * @param planAccede
	 * @Description 插入计划交易明细表
	 * @Author sunss
	 * @Date 2018/6/22 10:34
	 */
	@Override
	public boolean insertHJHPlanAccede(HjhAccedeVO planAccede) {
		Integer result = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/hjhPlan/insertHJHPlanAccede", planAccede, Integer.class).getBody();
		if (result != null) {
			return result == 0 ? false : true;
		}
		return false;
	}

	/**
	 * 检索正在还款中的标的
	 *
	 * @Author liushouyi
	 * @return
	 */
	@Override
	public List<BorrowAndInfoVO> selectBorrowList() {
		BorrowResponse response = restTemplate.getForEntity(
				"http://AM-TRADE/am-trade/trade/selectRepayBorrowList/",
				BorrowResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 获取borrow对象
	 * @param borrowId
	 * @return
	 */
	@Override
	public BorrowAndInfoVO getBorrowByNid(String borrowId) {
		String url = "http://AM-TRADE/am-trade/borrow/getBorrowByNid/"+borrowId;
		BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
		if (response!=null){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 用户投资散标操作表
	 *
	 * @param tenderBg
	 * @return
	 */
	@Override
	public boolean borrowTender(TenderBgVO tenderBg) {
		Integer result = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/borrow/borrowTender", tenderBg, Integer.class).getBody();
		if (result != null) {
			return result == 0 ? false : true;
		}
		return false;
	}

	/**
	 * 修改状态临时表结果
	 *  @param logUserId
	 * @param logOrderId
	 * @param respCode
	 * @param retMsg
	 * @param productId
	 */
	@Override
	public boolean updateTenderResult(String logUserId, String logOrderId, String respCode, String retMsg, String productId) {
		TenderRetMsg tenderRetMsg = new TenderRetMsg();
		tenderRetMsg.setLogOrderId(logOrderId);
		tenderRetMsg.setLogUserId(logUserId);
		tenderRetMsg.setRespCode(respCode);
		tenderRetMsg.setRetMsg(retMsg);
		tenderRetMsg.setProductId(productId);
		Integer result = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/borrow/updateTenderResult", tenderRetMsg, Integer.class).getBody();
		if (result != null) {
			return result == 0 ? false : true;
		}
		return false;
	}

	/**
	 * 获取投资异步结果
	 *
	 * @param userId
	 * @param logOrdId
	 * @param borrowNid
	 * @return
	 */
	@Override
	public String getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid) {
		String url = "http://AM-TRADE/am-trade/borrow/getBorrowTenderResult/" + userId + "/" + logOrdId + "/" + borrowNid;
		String response = restTemplate.getForEntity(url, String.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}
	
    /**
     * 会计划投资详情
     * @param params
     * @return
     */
	@Override
	public UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params) {
		String url = "http://AM-TRADE/am-trade/hjhPlan/selectUserHjhInvistDetail";
		return null;
	}
	/**
	 * 获取还款方式
	 */
	@Override
	public BorrowStyleVO getBorrowStyle(String borrowStyle) {
		String url = "http://AM-TRADE/am-trade/borrow/getBorrowStyle/"+borrowStyle;
		BorrowStyleResponse response=restTemplate.getForEntity(url,BorrowStyleResponse.class).getBody();
		if(response!=null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public Integer getTotalInverestCount(String userId) {
		ProjectListResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/inverestCount/" + userId,ProjectListResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getCount();
		}
		return null;
	}

}
