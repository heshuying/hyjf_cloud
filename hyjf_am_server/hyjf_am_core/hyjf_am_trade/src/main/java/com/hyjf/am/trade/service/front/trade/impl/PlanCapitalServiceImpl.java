package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.trade.dao.mapper.customize.*;
import com.hyjf.am.trade.dao.model.auto.HjhDebtDetail;
import com.hyjf.am.trade.dao.model.customize.HjhAccedeCustomize;
import com.hyjf.am.trade.service.front.trade.PlanCapitalService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.utils.TransUtil;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PlanCapitalServiceImpl extends BaseServiceImpl implements PlanCapitalService {

	private static final Logger logger = LoggerFactory.getLogger(PlanCapitalServiceImpl.class);

	@Autowired
	private HjhDebtDetailCustomizeMapper hjhDebtDetailCustomizeMapper; //债权详情表

	@Autowired
	private BatchHjhAccedeCustomizeMapper batchHjhAccedeCustomizeMapper; //汇计划加入明细

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
	 * @param date
	 * @return
	 */
	@Override
	public List<HjhPlanCapitalPredictionVO> getPlanCapitalPredictionForProformaList(Date date) {
		List<HjhPlanCapitalPredictionVO> hjhPlanCapitalPredictionVO = new ArrayList<HjhPlanCapitalPredictionVO>();
			logger.info("获取该期间的预计当日新增复投额开始,date:【" + date + "】");
			// 检索还款日为T日  当前有效的债权（HJH_DEBT_DETAL） 有效条件（status=1，repayStatus=0，delFlag=0）
			// 日期不为空才做处理
			if(date!=null) {
				// 预估时间
				String dualDateStr = GetDate.date2Str(date, new SimpleDateFormat("yyyy-MM-dd"));
				// 预估时间+3天
				String dualDateAddThreeStr = GetDate.date2Str(TransUtil.datesAdd(date,3), new SimpleDateFormat("yyyy-MM-dd"));
				// 1.检索对应日期范围List的有效债权数据（2.还款金额+计划订单 <10 元的不计算在当日的新增复投额之内（未收本金+未收利息））
				List<HjhDebtDetail> hjhdebtdetal = hjhDebtDetailCustomizeMapper.selectDebtDetailToDate(dualDateStr);
				// 2.根据投资人的用户名检索相应的 “计划订单” （1.计划订单“开始发起退出的时间”小于T，大于T+3日的订单。）
				for (HjhDebtDetail detal:hjhdebtdetal) {
					BigDecimal account = new BigDecimal(String.valueOf(BigDecimal.ZERO));
					// 根据不同的planNid去放入hjhPlanCapitalPredictionVO中，根据条件查询 accede 表（1.计划订单“开始发起退出的时间”小于T，大于T+3日的订单。之外的日期不进行累加）
					Map<String, Object> paramMap = new HashMap<>();
					// 投资人用户编号
					paramMap.put("userId", detal.getUserId());
					// 计划订单号
					paramMap.put("planOrderId", detal.getPlanOrderId());
					// 查询汇计划加入明细表
					List<HjhAccedeCustomize> accede = batchHjhAccedeCustomizeMapper.selectHjhAccedeList(paramMap);
					if (accede.size() != 1) {
						logger.info("查询汇计划加入明细表时数据错误“accede.size() != 1”:" + "planOrderId:【" + detal.getPlanOrderId() + "】" + "userId:【" + detal.getUserId() + "】");
						continue;
					}
					// 退出时间（时间戳）
					int quiteTime = accede.get(0).getQuitTime();
					// 開始時間戳（預估日期+3天）
					// int dualDateAddThreeStrStart = GetDate.getDayStart10(dualDateAddThreeStr);
					// 結束時間戳（預估日期+3天）
					int dualDateAddThreeStrend = GetDate.getDayEnd10(dualDateAddThreeStr);
					// 開始時間戳（預估日期）
					int dualDateStrStart = GetDate.getDayStart10(dualDateStr);
					// 結束時間戳（預估日期）
					// int dualDateStrend = GetDate.getDayEnd10(dualDateStr);
					// 1.计划订单“开始发起退出的时间”小于T，大于T+3日的订单。
					if (!(quiteTime < dualDateStrStart && quiteTime > dualDateAddThreeStrend)) {
						continue;
					} else {
						boolean flagAddVo = true;
						// detal.getRepayCapitalWait(); // 未收本金 +  detal.getRepayInterestWait(); // 未收利息 = 当前计算k
						account = account.add(detal.getRepayCapitalWait()).add(detal.getRepayInterestWait());
						// 判断vo中是否已存在相同的PlaNid计划（相同金额累加，不相同新增一条vo）
						for (HjhPlanCapitalPredictionVO voStr : hjhPlanCapitalPredictionVO) {
							// 已经存在PlanNid
							if (detal.getPlanNid().equals(voStr.getPlanNid())) {
								// 再根据“计划订单”对应的‘计划编号’进行区分，将标的‘当前持有人的还款额’进行（累加）
								BigDecimal reinvestAccount = BigDecimal.ZERO;
								// 累加预计当日新增复投额
								reinvestAccount = reinvestAccount.add(voStr.getReinvestAccount()).add(account);
								voStr.setReinvestAccount(reinvestAccount);
								// 不新增新list數據
								flagAddVo = false;
							}
						}
						// 如果list中不存在則新增計劃
						if(flagAddVo){
							// 新增对应PlanNid的计划行
							HjhPlanCapitalPredictionVO hjhPlanCapitalPrediction = new HjhPlanCapitalPredictionVO();
							hjhPlanCapitalPrediction.setDate(date);
							hjhPlanCapitalPrediction.setPlanNid(detal.getPlanNid());// 智投编号
							// hjhPlanCapitalPrediction.setPlanName(""); // 智投名称
							// hjhPlanCapitalPrediction.setLockPeriod(0); // 锁定期
							hjhPlanCapitalPrediction.setReinvestAccount(account); //预计当日新增复投额
							hjhPlanCapitalPrediction.setCreateTime(new Date());
							hjhPlanCapitalPrediction.setDelFlg(0);
							hjhPlanCapitalPredictionVO.add(hjhPlanCapitalPrediction);
						}
					}
				}
			}else{
				logger.info("开始结束日期为空,date:【" + date + "】");
			}
			logger.info("获取该期间的预计当日新增复投额结束,date:【" + date + "】");
		return hjhPlanCapitalPredictionVO;
	}
}