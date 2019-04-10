package com.hyjf.am.trade.service.front.trade;

import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
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
	 * 获取该期间的预计当日新增复投额
	 * @param date
	 * @return
	 */
    List<HjhPlanCapitalPredictionVO> getPlanCapitalPredictionForProformaList(Date date);

	/**
	 * 获取该期间的预计当日新增债转额
	 *
	 * @param dualDate
	 * @return
	 */
	List<HjhPlanCapitalVO> getPlanCapitalForCreditList(Date dualDate);
}
