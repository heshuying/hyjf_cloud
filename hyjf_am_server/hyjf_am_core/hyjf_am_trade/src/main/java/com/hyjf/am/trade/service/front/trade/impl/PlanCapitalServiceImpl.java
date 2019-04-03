package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.BorrowCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhPlanRepayCustomizeMapper;
import com.hyjf.am.trade.service.front.trade.PlanCapitalService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlanCapitalServiceImpl extends BaseServiceImpl implements PlanCapitalService {

	private static final Logger logger = LoggerFactory.getLogger(PlanCapitalServiceImpl.class);

	@Autowired
	private HjhAccedeMapper hjhAccedeMapper; //汇计划加入明细

	@Autowired
	private HjhPlanMapper hjhPlanMapper; //汇计划

	@Autowired
	private HjhPlanRepayCustomizeMapper hjhPlanRepayCustomizeMapper; //计划还款表

	@Autowired
	private BorrowCustomizeMapper borrowCustomizeMapper;//标的表(标的状态金额时间等)

	@Autowired
	private BorrowRepayMapper borrowRepayMapper;//标的还款记录（借款人）总表

	@Autowired
	private BorrowTenderCustomizeMapper borrowTenderCustomizeMapper;//标的投资详情表

	@Autowired
	private BorrowRecoverMapper borrowRecoverMapper; //标的放款记录（投资人） 总表

	@Autowired
	private HjhDebtCreditTenderMapper hjhDebtCreditTenderMapper;//汇添金债权承接表相当于borrow_credit_tender

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

	/**
	 * 获取该期间的预计当日新增复投额
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@Override
	public List<HjhPlanCapitalPredictionVO> getPlanCapitalPredictionForProformaList(Date fromDate, Date toDate) {
		List<HjhPlanCapitalPredictionVO> list = new ArrayList<HjhPlanCapitalPredictionVO>();
		logger.info("获取该期间的预计当日新增复投额开始,fromDate:【" + fromDate + "】"+" *********** toDate:"+"【" + toDate + "】");
		// 检索还款日为T+1日   且与    “计划”关联的“标的还款明细” ht_borrow_recover / ht_borrow / ht_borrow_recover_plan

		// 根据投资人的用户名检索相应的 “计划订单” ht_hjh_accede

		// 再根据“计划订单”对应的‘计划编号’进行区分，将标的‘当前持有人的还款额’进行（累加）

		// 1.计划订单开始发起退出的时间小于T，大于T+3日的订单

		// 2.还款金额+计划订单 <10 元的不计算在当日的新增复投额之内

		logger.info("获取该期间的预计当日新增复投额结束,fromDate:【" + fromDate + "】"+" =========== toDate:"+"【" + toDate + "】");

		return list;
	}



}