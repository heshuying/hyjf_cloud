package com.hyjf.am.trade.service.front.trade;

import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.HjhAccountBalanceVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;

import java.util.Date;
import java.util.List;

public interface PlanCapitalService extends BaseService {

	/**
	 * 获取该日期的实际债转和复投金额
	 * @param date
	 * @return
	 */
	List<HjhPlanCapitalVO> getPlanCapitalForActList(Date date);

	/**
	 * 获取该期间的预估债转和复投金额
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	List<HjhPlanCapitalVO> getPlanCapitalForProformaList(Date fromDate, Date toDate);

	/**
	 *获取该期间的汇计划日交易量
	 * @param date
	 * @return
	 */
	List<HjhAccountBalanceVO> getHjhAccountBalanceForActList(Date date);


}
