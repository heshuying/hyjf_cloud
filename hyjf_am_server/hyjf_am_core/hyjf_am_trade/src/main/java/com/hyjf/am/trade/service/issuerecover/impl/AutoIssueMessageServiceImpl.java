package com.hyjf.am.trade.service.issuerecover.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.dao.mapper.auto.HjhAllocationEngineMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.issuerecover.AutoIssueMessageService;
import com.hyjf.common.bean.RedisBorrow;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/12 11:10 关联计划消息
 * @Description: AutoIssueMessageServiceImpl
 */
@Service
public class AutoIssueMessageServiceImpl extends BaseServiceImpl implements AutoIssueMessageService {
	private static final Logger logger = LoggerFactory.getLogger(AutoIssueMessageServiceImpl.class);

	@Resource
	private HjhAllocationEngineMapper hjhAllocationEngineMapper;

	@Override
	public String getPlanNid(Integer labelId) {
		HjhAllocationEngineExample example = new HjhAllocationEngineExample();
		HjhAllocationEngineExample.Criteria cra = example.createCriteria();
		cra.andDelFlagEqualTo(0);
		cra.andLabelIdEqualTo(labelId);
		cra.andConfigStatusEqualTo(1);
		cra.andLabelStatusEqualTo(1);
		List<HjhAllocationEngine> list = this.hjhAllocationEngineMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0).getPlanNid();
	}

	@Override
	public boolean updateIssueBorrow(BorrowInfo borrowInfo, Borrow borrow, String planNid, HjhPlanAsset hjhPlanAsset) {
		borrow.setPlanNid(planNid);
		borrow.setLabelId(borrow.getLabelId());
		// 更新时间
		borrow.setUpdatetime(GetDate.getDate(GetDate.getNowTime10()));
		this.borrowMapper.updateByPrimaryKeySelective(borrow);

		// 增加redis相应计划可投金额
		// 更新汇计划表
		HjhPlan hjhPlan = new HjhPlan();
		hjhPlan.setPlanNid(planNid);
		hjhPlan.setAvailableInvestAccount(borrow.getAccount());
		this.hjhPlanCustomizeMapper.updatePlanAccount(hjhPlan);
		logger.info(borrow.getBorrowNid() + " 成功更新计划池" + planNid + "总额 + " + borrow.getAccount());

		// 更新资产表
		if(hjhPlanAsset != null){
			logger.info("==========更新资产表计划编号:" + borrow.getBorrowNid() + ",计划编号：" + planNid);
			hjhPlanAsset.setPlanNid(planNid);
			// 获取当前时间
			hjhPlanAsset.setUpdateTime(new Date());
			hjhPlanAsset.setUpdateUserId(1);
			hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAsset);
		}else{
			logger.info("==========更新资产表计划编号时，资产为空！:" + borrow.getBorrowNid() + ",计划编号：" + planNid);
		}

		// 增加redis相应计划可投金额
		redisAdd(RedisConstants.HJH_PLAN + planNid, borrow.getAccount().toString());
		RedisBorrow redisBorrow = new RedisBorrow();
		redisBorrow.setBorrowNid(borrow.getBorrowNid());
		redisBorrow.setBorrowAccountWait(borrow.getAccount());
        // redis相应计划
		RedisUtils.leftpush(RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + planNid,
				JSON.toJSONString(redisBorrow));
		logger.info(borrow.getBorrowNid() + " 计划编号：" + planNid + " 关联计划成功");
		return true;
	}

	@Override
	public HjhDebtCredit serchCreditByNid(String creditNid) {
		HjhDebtCreditExample example = new HjhDebtCreditExample();
		HjhDebtCreditExample.Criteria criteria = example.createCriteria();
		criteria.andCreditNidEqualTo(creditNid);
		List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public HjhLabel getLabelId(HjhDebtCredit credit) {
		HjhLabel resultLabel = null;

        List<HjhLabel> list = this.getLabelListByBorrowStyle(credit.getBorrowStyle());
        if (CollectionUtils.isEmpty(list)) {
            logger.info(credit.getBorrowStyle() + " 该债转还款方式 没有一个标签");
            return resultLabel;
        }

		// continue过滤输入了但是不匹配的标签，如果找到就是第一个
		for (HjhLabel hjhLabel : list) {

			// 标的是否逾期 ,此为必须字段
			if (hjhLabel.getIsLate() != null && hjhLabel.getIsLate().intValue() == 1) {
				if (credit.getIsLateCredit() != null && credit.getIsLateCredit() == 1) {
					;
				} else {
					continue;
				}
			} else if (hjhLabel.getIsLate() != null && hjhLabel.getIsLate().intValue() == 0) {
				if (credit.getIsLateCredit() != null && credit.getIsLateCredit() == 0) {
					;
				} else {
					continue;
				}
			}
			// 标的期限
			// int score = 0;
			if (hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue() > 0
					&& hjhLabel.getLabelTermStart() != null && hjhLabel.getLabelTermStart().intValue() > 0) {
				if (credit.getBorrowPeriod() >= hjhLabel.getLabelTermStart()
						&& credit.getBorrowPeriod() <= hjhLabel.getLabelTermEnd()) {
					// score = score+1;
				} else {
					continue;
				}
			} else if ((hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue() > 0)
					|| (hjhLabel.getLabelTermStart() != null && hjhLabel.getLabelTermStart().intValue() > 0)) {
				if (credit.getBorrowPeriod().equals(hjhLabel.getLabelTermStart())
						|| credit.getBorrowPeriod().equals(hjhLabel.getLabelTermEnd())) {
					// score = score+1;
				} else {
					continue;
				}
			}
			// 标的实际利率
			if (hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO) > 0
					&& hjhLabel.getLabelAprEnd() != null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO) > 0) {
				if (credit.getActualApr().compareTo(hjhLabel.getLabelAprStart()) >= 0
						&& credit.getActualApr().compareTo(hjhLabel.getLabelAprEnd()) <= 0) {
					;
				} else {
					continue;
				}
			} else if (hjhLabel.getLabelAprStart() != null
					&& hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO) > 0) {
				if (credit.getActualApr().compareTo(hjhLabel.getLabelAprStart()) == 0) {
					// score = score+1;
				} else {
					continue;
				}

			} else if (hjhLabel.getLabelAprEnd() != null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO) > 0) {
				if (credit.getActualApr().compareTo(hjhLabel.getLabelAprEnd()) == 0) {
					;
				} else {
					continue;
				}
			}
			// 标的实际支付金额
			if (hjhLabel.getLabelPaymentAccountStart() != null
					&& hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO) > 0
					&& hjhLabel.getLabelPaymentAccountEnd() != null
					&& hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO) > 0) {
				if (credit.getLiquidationFairValue().compareTo(hjhLabel.getLabelPaymentAccountStart()) >= 0
						&& credit.getLiquidationFairValue().compareTo(hjhLabel.getLabelPaymentAccountEnd()) <= 0) {
					;
				} else {
					continue;
				}
			} else if (hjhLabel.getLabelPaymentAccountStart() != null
					&& hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO) > 0) {
				if (credit.getLiquidationFairValue().compareTo(hjhLabel.getLabelPaymentAccountStart()) == 0) {
					;
				} else {
					continue;
				}

			} else if (hjhLabel.getLabelPaymentAccountEnd() != null
					&& hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO) > 0) {
				if (credit.getLiquidationFairValue().compareTo(hjhLabel.getLabelPaymentAccountEnd()) == 0) {
					;
				} else {
					continue;
				}
			}
			// 资产来源
			if (StringUtils.isNotBlank(hjhLabel.getInstCode())) {
				if (hjhLabel.getInstCode().equals(credit.getInstCode())) {
					// score = score+1;
				} else {
					continue;
				}
			}
			// 产品类型
			if (hjhLabel.getAssetType() != null && hjhLabel.getAssetType().intValue() >= 0) {
				if (hjhLabel.getAssetType().equals(credit.getAssetType())) {
					;
				} else {
					continue;
				}
			}
			// 项目类型
			if (hjhLabel.getProjectType() != null && hjhLabel.getProjectType().intValue() >= 0) {
				if (hjhLabel.getProjectType().equals(credit.getProjectType())) {
					;
				} else {
					continue;
				}
			}

			// 剩余天数
			if (hjhLabel.getRemainingDaysEnd() != null && hjhLabel.getRemainingDaysEnd().intValue() >= 0
					&& hjhLabel.getRemainingDaysStart() != null && hjhLabel.getRemainingDaysStart().intValue() >= 0) {
				if (credit.getRemainDays() != null && credit.getRemainDays() >= hjhLabel.getRemainingDaysStart()
						&& credit.getRemainDays() <= hjhLabel.getRemainingDaysEnd()) {
					;
				} else {
					continue;
				}
			} else if ((hjhLabel.getRemainingDaysEnd() != null && hjhLabel.getRemainingDaysEnd().intValue() >= 0)
					|| (hjhLabel.getRemainingDaysStart() != null && hjhLabel.getRemainingDaysStart().intValue() >= 0)) {
				if (credit.getRemainDays() != null && credit.getRemainDays().equals(hjhLabel.getRemainingDaysStart())
						|| credit.getRemainDays().equals(hjhLabel.getRemainingDaysEnd())) {
					;
				} else {
					continue;
				}
			}

			// 找出即为最新的标签
			return hjhLabel;

		}

		return resultLabel;
	}

	@Override
	public boolean updateIssueCredit(HjhDebtCredit credit, String planNid) {
		// 关联计划
        credit.setId(credit.getId());
        credit.setPlanNidNew(planNid);
        credit.setLabelId(credit.getLabelId());
        credit.setLabelName(credit.getLabelName());
		// 更新时间
        credit.setUpdateTime(new Date());
		this.hjhDebtCreditMapper.updateByPrimaryKeySelective(credit);

		// 增加redis相应计划可投金额
		// 更新汇计划表
		HjhPlan hjhPlanNew = null;
		HjhPlanExample hjhPlanExample = new HjhPlanExample();
		HjhPlanExample.Criteria cra =  hjhPlanExample.createCriteria();
		cra.andPlanNidEqualTo(planNid);
		List<HjhPlan> hjhPlanList = this.hjhPlanMapper.selectByExample(hjhPlanExample);
		if(hjhPlanList!=null && hjhPlanList.size()>0){
			hjhPlanNew = hjhPlanList.get(0);
		}
		HjhPlan hjhPlan = new HjhPlan();
		hjhPlan.setPlanNid(planNid);
		hjhPlan.setAvailableInvestAccount(credit.getLiquidationFairValue());
		// hjhPlan.setJoinTotal(credit.getLiquidationFairValue());
		this.hjhPlanCustomizeMapper.updatePlanAccount(hjhPlan);
		logger.info(credit.getCreditNid() + " 成功更新计划池" + planNid + "总额 + " + credit.getLiquidationFairValue());
        logger.info("计划:["+planNid +"],原开放额度:["+hjhPlanNew.getAvailableInvestAccount()+"],债转编号:["+credit.getCreditNid()+"],债转的债权价值:["+ credit.getLiquidationFairValue()+"]");
		redisAdd(RedisConstants.HJH_PLAN + planNid, credit.getLiquidationFairValue().toString());// 增加redis相应计划可投金额
		RedisBorrow redisBorrow = new RedisBorrow();
		redisBorrow.setBorrowNid(credit.getCreditNid());
		redisBorrow.setBorrowAccountWait(credit.getLiquidationFairValue());

		RedisUtils.leftpush(RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + planNid,
				JSON.toJSONString(redisBorrow));// redis相应计划

		logger.info(credit.getCreditNid() + " 计划编号：" + planNid + " 关联计划成功");
		return true;
	}


    /**
     * 债转标-根据还款方式获取标签列表
     * @param borrowStyle
     * @return
     */
    private List<HjhLabel> getLabelListByBorrowStyle(String borrowStyle){
        HjhLabelExample example = new HjhLabelExample();
        HjhLabelExample.Criteria cra = example.createCriteria();

        cra.andDelFlagEqualTo(0);
        cra.andLabelStateEqualTo(1);
        cra.andBorrowStyleEqualTo(borrowStyle);
        cra.andIsCreditEqualTo(1); // 债转标
        example.setOrderByClause(" update_time desc ");
        return this.hjhLabelMapper.selectByExample(example);
    }

    /**
	 * 并发情况下保证设置一个值
	 * 
	 * @param key
	 * @param value
	 */
	private void redisAdd(String key, String value) {
		JedisPool poolNew = RedisUtils.getPool();
		Jedis jedis = poolNew.getResource();

		try {
			while ("OK".equals(jedis.watch(key))) {
				List<Object> results = null;

				String balance = jedis.get(key);
				BigDecimal bal = new BigDecimal(0);
				if (balance != null) {
					bal = new BigDecimal(balance);
				}
				BigDecimal val = new BigDecimal(value);

				Transaction tx = jedis.multi();
				String valbeset = bal.add(val).toString();
				tx.set(key, valbeset);
				results = tx.exec();
				if (results == null || results.isEmpty()) {
					jedis.unwatch();
				} else {
					String ret = (String) results.get(0);
					if (ret != null && "OK".equals(ret)) {
						// 成功后
						break;
					} else {
						jedis.unwatch();
					}
				}
			}
		} catch (Exception e) {
			logger.error("连接Redis异常！", e);
		} finally {
			RedisUtils.returnResource(poolNew, jedis);
		}
	}
}
