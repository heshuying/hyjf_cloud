package com.hyjf.am.market.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hyjf.am.market.dao.mapper.customize.market.SellDailyCustomizeMapper;
import com.hyjf.am.market.service.SellDailyService;
import com.hyjf.am.vo.market.SellDailyVO;
import org.springframework.util.Assert;

/**
 * @author fuqiang
 * @version SellDailyServiceImpl, v0.1 2018/11/22 10:39
 */
@Service
public class SellDailyServiceImpl implements SellDailyService {
    private Logger logger = LoggerFactory.getLogger(SellDailyServiceImpl.class);

    @Resource
    private SellDailyCustomizeMapper sellDailyCustomizeMapper;

    @Override
    public List<SellDailyVO> countTotalInvestOnMonth(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalInvestOnMonth(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalRepayOnMonth(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalRepayOnMonth(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalInvestOnPreviousMonth(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalInvestOnPreviousMonth(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalWithdrawOnMonth(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalWithdrawOnMonth(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalRechargeOnMonth(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalRechargeOnMonth(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalAnnualInvestOnMonth(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalAnnualInvestOnMonth(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalAnnualInvestOnPreviousMonth(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalAnnualInvestOnPreviousMonth(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalTenderYesterday(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalTenderYesterday(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalRepayYesterday(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalRepayYesterday(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalAnnualInvestYesterday(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalAnnualInvestYesterday(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalWithdrawYesterday(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalWithdrawYesterday(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countTotalRechargeYesterday(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countTotalRechargeYesterday(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countNoneRepayToday(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countNoneRepayToday(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countRegisterTotalYesterday(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countRegisterTotalYesterday(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countRechargeGt3000UserNum(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countRechargeGt3000UserNum(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countInvestGt3000UserNum(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countInvestGt3000UserNum(startTime, endTime, type);
    }

    @Override
    public List<SellDailyVO> countInvestGt3000MonthUserNum(Date startTime, Date endTime, Integer type) {
        return sellDailyCustomizeMapper.countInvestGt3000MonthUserNum(startTime, endTime, type);
    }

    @Override
    public SellDailyVO countTotalInvestOnMonthQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalInvestOnMonthQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalRepayOnMonthQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalRepayOnMonthQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalInvestOnPreviousMonthQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalInvestOnPreviousMonthQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalWithdrawOnMonthQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalWithdrawOnMonthQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalRechargeOnMonthQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalRechargeOnMonthQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalAnnualInvestOnMonthQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalAnnualInvestOnMonthQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalAnnualInvestOnPreviousMonthQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalAnnualInvestOnPreviousMonthQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalTenderYesterdayQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalTenderYesterdayQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalRepayYesterdayQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalRepayYesterdayQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalAnnualInvestYesterdayQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalAnnualInvestYesterdayQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalWithdrawYesterdayQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalWithdrawYesterdayQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalRechargeYesterdayQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countTotalRechargeYesterdayQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countNoneRepayTodayQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countNoneRepayTodayQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countRegisterTotalYesterdayQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countRegisterTotalYesterdayQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countRechargeGt3000UserNumQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countRechargeGt3000UserNumQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countInvestGt3000UserNumQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countInvestGt3000UserNumQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countInvestGt3000MonthUserNumQl(Date startTime, Date endTime, String sourceId) {
        return sellDailyCustomizeMapper.countInvestGt3000MonthUserNumQl(startTime, endTime, sourceId);
    }

    @Override
    public SellDailyVO countTotalCredit(Date startTime, Date endTime) {
        return sellDailyCustomizeMapper.countTotalCredit(startTime, endTime);
    }

    @Override
    public void batchUpdate(List<SellDailyVO> list) {
        sellDailyCustomizeMapper.batchUpdate(list);
    }

    @Override
    public void update(SellDailyVO vo) {
        sellDailyCustomizeMapper.update(vo);
    }

    @Override
	public SellDailyVO addValue(SellDailyVO source, SellDailyVO target, int column, int operateType) {
        Assert.notNull(source, "source must not be null...");
        Assert.notNull(target, "target must not be null...");
        logger.debug("source is: {}, target is: {}, column is: {}, operateType is: {}", source.print(), target.print(), column, operateType);

		switch (column) {
		case 1:
			target.setInvestTotalMonth(
					this.operating(source.getInvestTotalMonth(), target.getInvestTotalMonth(), operateType));
			break;
		case 2:
			target.setRepaymentTotalMonth(
					this.operating(source.getRepaymentTotalMonth(), target.getRepaymentTotalMonth(), operateType));
			break;
		case 3:
			target.setInvestTotalPreviousMonth(this.operating(source.getInvestTotalPreviousMonth(),
					target.getInvestTotalPreviousMonth(), operateType));
			break;
		case 5:
			target.setWithdrawTotalMonth(
					this.operating(source.getWithdrawTotalMonth(), target.getWithdrawTotalMonth(), operateType));
			break;
		case 7:
			target.setRechargeTotalMonth(
					this.operating(source.getRechargeTotalMonth(), target.getRechargeTotalMonth(), operateType));
			break;
		case 8:
			target.setInvestAnnualTotalMonth(this.operating(source.getInvestAnnualTotalMonth(),
					target.getInvestAnnualTotalMonth(), operateType));
			break;
		case 9:
			target.setInvestAnnualTotalPreviousMonth(this.operating(source.getInvestAnnualTotalPreviousMonth(),
					target.getInvestAnnualTotalPreviousMonth(), operateType));
			break;
		case 11:
			target.setInvestTotalYesterday(
					this.operating(source.getInvestTotalYesterday(), target.getInvestTotalYesterday(), operateType));
			break;
		case 12:
			target.setRepaymentTotalYesterday(this.operating(source.getRepaymentTotalYesterday(),
					target.getRepaymentTotalYesterday(), operateType));
			break;
		case 13:
			target.setInvestAnnualTotalYesterday(this.operating(source.getInvestAnnualTotalYesterday(),
					target.getInvestAnnualTotalYesterday(), operateType));
			break;
		case 14:
			target.setWithdrawTotalYesterday(this.operating(source.getWithdrawTotalYesterday(),
					target.getWithdrawTotalYesterday(), operateType));
			break;
		case 15:
			target.setRechargeTotalYesterday(this.operating(source.getRechargeTotalYesterday(),
					target.getRechargeTotalYesterday(), operateType));
			break;
		case 17:
			target.setNonRepaymentToday(
					this.operating(source.getNonRepaymentToday(), target.getNonRepaymentToday(), operateType));
			break;
		case 18:
			target.setRegisterTotalYesterday(this.operating(source.getRegisterTotalYesterday(),
					target.getRegisterTotalYesterday(), operateType));
			break;
		case 19:
			target.setRechargeGt3000UserNum(
					this.operating(source.getRechargeGt3000UserNum(), target.getRechargeGt3000UserNum(), operateType));
			break;
		case 20:
			target.setInvestGt3000UserNum(
					this.operating(source.getInvestGt3000UserNum(), target.getInvestGt3000UserNum(), operateType));
			break;
		case 21:
			target.setInvestGt3000MonthUserNum(this.operating(source.getInvestGt3000MonthUserNum(),
					target.getInvestGt3000MonthUserNum(), operateType));
			break;
		}

		return target;
	}

	private <T> T operating(T source, T target, int operateType) {
		if (source instanceof BigDecimal && target instanceof BigDecimal) {
			BigDecimal b1 = (BigDecimal) source;
			BigDecimal b2 = (BigDecimal) target;
			if (operateType == 1) {
				return (T) b2.add(b1);
			} else if (operateType == -1) {
				return (T) b2.subtract(b1);
			}

		} else if (source instanceof Integer && target instanceof Integer) {
			Integer i1 = (Integer) source;
			Integer i2 = (Integer) target;
			if (operateType == 1) {
				return (T) new Integer(i2 + i1);
			} else if (operateType == -1) {
				return (T) new Integer(i2 - i1);
			}
		}
		logger.warn("operating fail, source is: {}, target is： {}， operateType is: {} ", source, target, operateType);
		return null;
	}
}
