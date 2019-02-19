package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.trade.service.front.trade.PlanCapitalService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlanCapitalServiceImpl extends BaseServiceImpl implements PlanCapitalService {
	/**
	 * 获取该日期的实际债转和复投金额
	 * @param date
	 * @return
	 */
	@Override
	public List<HjhPlanCapitalVO> getPlanCapitalForActList(Date date) {
		List<HjhPlanCapitalVO> list = this.hjhPlanCapitalCustomizeMapper.selectPlanCapitalForActList(date);
		return list;
	}

	/**
	 * 获取该期间的预估债转和复投金额
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Override
	public List<HjhPlanCapitalVO> getPlanCapitalForProformaList(Date fromDate, Date toDate) {
		List<HjhPlanCapitalVO> list = this.hjhPlanCapitalCustomizeMapper.selectPlanCapitalForProformaList(fromDate, toDate);
		return list;
	}
}