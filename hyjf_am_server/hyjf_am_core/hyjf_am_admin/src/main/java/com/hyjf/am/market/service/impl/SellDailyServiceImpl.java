package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.SellDailyCustomizeMapper;
import com.hyjf.am.market.service.SellDailyService;
import com.hyjf.am.vo.market.SellDailyVO;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version SellDailyServiceImpl, v0.1 2018/11/22 10:39
 */
@Service
public class SellDailyServiceImpl implements SellDailyService {
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
    public void batchUpdate(List<SellDailyVO> list) {
        sellDailyCustomizeMapper.batchUpdate(list);
    }

    @Override
    public void update(SellDailyVO vo) {
        sellDailyCustomizeMapper.update(vo);
    }

    @Override
    public SellDailyVO setValueHz(BigDecimal hzTotalTmp, int column, SellDailyVO hzRecord, SellDailyVO shOCSellDaily, SellDailyVO qianleSellDaily, BigDecimal vipTmp) {
        switch (column) {
            case 1:
                hzRecord.setInvestTotalMonth(hzTotalTmp.subtract(shOCSellDaily.getInvestTotalMonth()).subtract(qianleSellDaily.getInvestTotalMonth()).add(vipTmp));
                break;
            case 2:
                hzRecord.setRepaymentTotalMonth(hzTotalTmp.subtract(shOCSellDaily.getRepaymentTotalMonth()).subtract(qianleSellDaily.getRepaymentTotalMonth()).add(vipTmp));
                break;
            case 3:
                hzRecord.setInvestTotalPreviousMonth(hzTotalTmp.subtract(shOCSellDaily.getInvestTotalPreviousMonth()).subtract(qianleSellDaily.getInvestTotalPreviousMonth()).add(vipTmp));
                break;
            case 5:
                hzRecord.setWithdrawTotalMonth(hzTotalTmp.subtract(shOCSellDaily.getWithdrawTotalMonth()).subtract(qianleSellDaily.getWithdrawTotalMonth()).add(vipTmp));
                break;
            case 7:
                hzRecord.setRechargeTotalMonth(hzTotalTmp.subtract(shOCSellDaily.getRechargeTotalMonth()).subtract(qianleSellDaily.getRechargeTotalMonth()).add(vipTmp));
                break;
            case 8:
                hzRecord.setInvestAnnualTotalMonth(hzTotalTmp.subtract(shOCSellDaily.getInvestAnnualTotalMonth()).subtract(qianleSellDaily.getInvestAnnualTotalMonth()).add(vipTmp));
                break;
            case 9:
                hzRecord.setInvestAnnualTotalPreviousMonth(
                        hzTotalTmp.subtract(shOCSellDaily.getInvestAnnualTotalPreviousMonth()).subtract(qianleSellDaily.getInvestTotalPreviousMonth()).add(vipTmp));
                break;
            case 11:
                hzRecord.setInvestTotalYesterday(hzTotalTmp.subtract(shOCSellDaily.getInvestTotalYesterday()).subtract(qianleSellDaily.getInvestTotalYesterday()).add(vipTmp));
                break;
            case 12:
                hzRecord.setRepaymentTotalYesterday(hzTotalTmp.subtract(shOCSellDaily.getRepaymentTotalYesterday()).subtract(qianleSellDaily.getRepaymentTotalYesterday()).add(vipTmp));
                break;
            case 13:
                hzRecord.setInvestAnnualTotalYesterday(hzTotalTmp.subtract(shOCSellDaily.getInvestAnnualTotalYesterday()).subtract(qianleSellDaily.getInvestAnnualTotalYesterday()).add(vipTmp));
                break;
            case 14:
                hzRecord.setWithdrawTotalYesterday(hzTotalTmp.subtract(shOCSellDaily.getWithdrawTotalYesterday()).subtract(qianleSellDaily.getWithdrawTotalYesterday()).add(vipTmp));
                break;
            case 15:
                hzRecord.setRechargeTotalYesterday(hzTotalTmp.subtract(shOCSellDaily.getRechargeTotalYesterday()).subtract(qianleSellDaily.getRechargeTotalYesterday()).add(vipTmp));
                break;
            case 17:
                hzRecord.setNonRepaymentToday(hzTotalTmp.subtract(shOCSellDaily.getNonRepaymentToday()).subtract(qianleSellDaily.getNonRepaymentToday()).add(vipTmp));
                break;
            case 18:
                hzRecord.setRegisterTotalYesterday(hzTotalTmp.intValue() - shOCSellDaily.getRegisterTotalYesterday() - qianleSellDaily.getRegisterTotalYesterday() + vipTmp.intValue());
                break;
            case 19:
                hzRecord.setRechargeGt3000UserNum(hzTotalTmp.intValue() - shOCSellDaily.getRechargeGt3000UserNum() - qianleSellDaily.getRechargeGt3000UserNum() + vipTmp.intValue());
                break;
            case 20:
                hzRecord.setInvestGt3000UserNum(hzTotalTmp.intValue() - shOCSellDaily.getInvestGt3000UserNum() - qianleSellDaily.getInvestGt3000UserNum() + vipTmp.intValue());
                break;
            case 21:
                hzRecord.setInvestGt3000MonthUserNum(hzTotalTmp.intValue() - shOCSellDaily.getInvestGt3000MonthUserNum() - qianleSellDaily.getInvestGt3000MonthUserNum() + vipTmp.intValue());
                break;
        }

        return hzRecord;
    }

    @Override
    public BigDecimal addValue(BigDecimal tmp, Integer column, SellDailyVO entity) {
        BigDecimal result = BigDecimal.ZERO;
        switch (Integer.valueOf(column)) {
            case 1:
                result = tmp.add(entity.getInvestTotalMonth());
                break;
            case 2:
                result = tmp.add(entity.getRepaymentTotalMonth());
                break;
            case 3:
                result = tmp.add(entity.getInvestTotalPreviousMonth());
                break;
            case 5:
                result = tmp.add(entity.getWithdrawTotalMonth());
                break;
            case 7:
                result = tmp.add(entity.getRechargeTotalMonth());
                break;
            case 8:
                result = tmp.add(entity.getInvestAnnualTotalMonth());
                break;
            case 9:
                result = tmp.add(entity.getInvestAnnualTotalPreviousMonth());
                break;
            case 11:
                result = tmp.add(entity.getInvestTotalYesterday());
                break;
            case 12:
                result = tmp.add(entity.getRepaymentTotalYesterday());
                break;
            case 13:
                result = tmp.add(entity.getInvestAnnualTotalYesterday());
                break;
            case 14:
                result = tmp.add(entity.getWithdrawTotalYesterday());
                break;
            case 15:
                result = tmp.add(entity.getRechargeTotalYesterday());
                break;
            case 17:
                result = tmp.add(entity.getNonRepaymentToday());
                break;
            case 18:
                result = new BigDecimal(tmp.intValue() + entity.getRegisterTotalYesterday());
                break;
            case 19:
                result = new BigDecimal(tmp.intValue() + entity.getRechargeGt3000UserNum());
                break;
            case 20:
                result = new BigDecimal(tmp.intValue() + entity.getInvestGt3000UserNum());
                break;
            case 21:
                result = new BigDecimal(tmp.intValue() + entity.getInvestGt3000MonthUserNum());
                break;
        }

        return result;
    }

    @Override
    public SellDailyVO setValue(BigDecimal tmp, int column, SellDailyVO sellDaily, SellDailyVO reduceSellDaily) {
        switch (column) {
            case 1:
                sellDaily.setInvestTotalMonth(tmp.subtract(reduceSellDaily.getInvestTotalMonth()));
                break;
            case 2:
                sellDaily.setRepaymentTotalMonth(tmp.subtract(reduceSellDaily.getRepaymentTotalMonth()));
                break;
            case 3:
                sellDaily.setInvestTotalPreviousMonth(tmp.subtract(reduceSellDaily.getInvestTotalPreviousMonth()));
                break;
            case 5:
                sellDaily.setWithdrawTotalMonth(tmp.subtract(reduceSellDaily.getWithdrawTotalMonth()));
                break;
            case 7:
                sellDaily.setRechargeTotalMonth(tmp.subtract(reduceSellDaily.getRechargeTotalMonth()));
                break;
            case 8:
                sellDaily.setInvestAnnualTotalMonth(tmp.subtract(reduceSellDaily.getInvestAnnualTotalMonth()));
                break;
            case 9:
                sellDaily.setInvestAnnualTotalPreviousMonth(
                        tmp.subtract(reduceSellDaily.getInvestAnnualTotalPreviousMonth()));
                break;
            case 11:
                sellDaily.setInvestTotalYesterday(tmp.subtract(reduceSellDaily.getInvestTotalYesterday()));
                break;
            case 12:
                sellDaily.setRepaymentTotalYesterday(tmp.subtract(reduceSellDaily.getRepaymentTotalYesterday()));
                break;
            case 13:
                sellDaily.setInvestAnnualTotalYesterday(tmp.subtract(reduceSellDaily.getInvestAnnualTotalYesterday()));
                break;
            case 14:
                sellDaily.setWithdrawTotalYesterday(tmp.subtract(reduceSellDaily.getWithdrawTotalYesterday()));
                break;
            case 15:
                sellDaily.setRechargeTotalYesterday(tmp.subtract(reduceSellDaily.getRechargeTotalYesterday()));
                break;
            case 17:
                sellDaily.setNonRepaymentToday(tmp.subtract(reduceSellDaily.getNonRepaymentToday()));
                break;
            case 18:
                sellDaily.setRegisterTotalYesterday(tmp.intValue() - reduceSellDaily.getRegisterTotalYesterday());
                break;
            case 19:
                sellDaily.setRechargeGt3000UserNum(tmp.intValue() - reduceSellDaily.getRechargeGt3000UserNum());
                break;
            case 20:
                sellDaily.setInvestGt3000UserNum(tmp.intValue() - reduceSellDaily.getInvestGt3000UserNum());
                break;
            case 21:
                sellDaily.setInvestGt3000MonthUserNum(tmp.intValue() - reduceSellDaily.getInvestGt3000MonthUserNum());
                break;
        }

        return sellDaily;
    }

    @Override
    public SellDailyVO setValue(BigDecimal tmp, int column, SellDailyVO sellDaily, SellDailyVO reduceSellDaily, SellDailyVO qianleSellDaily, BigDecimal vipTmp) {
        switch (column) {
            case 1:
                sellDaily.setInvestTotalMonth(tmp.subtract(reduceSellDaily.getInvestTotalMonth()).subtract(qianleSellDaily.getInvestTotalMonth()).subtract(vipTmp));
                break;
            case 2:
                sellDaily.setRepaymentTotalMonth(tmp.subtract(reduceSellDaily.getRepaymentTotalMonth()).subtract(qianleSellDaily.getRepaymentTotalMonth()).subtract(vipTmp));
                break;
            case 3:
                sellDaily.setInvestTotalPreviousMonth(tmp.subtract(reduceSellDaily.getInvestTotalPreviousMonth()).subtract(qianleSellDaily.getInvestTotalPreviousMonth()).subtract(vipTmp));
                break;
            case 5:
                sellDaily.setWithdrawTotalMonth(tmp.subtract(reduceSellDaily.getWithdrawTotalMonth()).subtract(qianleSellDaily.getWithdrawTotalMonth()).subtract(vipTmp));
                break;
            case 7:
                sellDaily.setRechargeTotalMonth(tmp.subtract(reduceSellDaily.getRechargeTotalMonth()).subtract(qianleSellDaily.getRechargeTotalMonth()).subtract(vipTmp));
                break;
            case 8:
                sellDaily.setInvestAnnualTotalMonth(tmp.subtract(reduceSellDaily.getInvestAnnualTotalMonth()).subtract(qianleSellDaily.getInvestAnnualTotalMonth()).subtract(vipTmp));
                break;
            case 9:
                sellDaily.setInvestAnnualTotalPreviousMonth(
                        tmp.subtract(reduceSellDaily.getInvestAnnualTotalPreviousMonth()).subtract(qianleSellDaily.getInvestTotalPreviousMonth()).subtract(vipTmp));
                break;
            case 11:
                sellDaily.setInvestTotalYesterday(tmp.subtract(reduceSellDaily.getInvestTotalYesterday()).subtract(qianleSellDaily.getInvestTotalYesterday()).subtract(vipTmp));
                break;
            case 12:
                sellDaily.setRepaymentTotalYesterday(tmp.subtract(reduceSellDaily.getRepaymentTotalYesterday()).subtract(qianleSellDaily.getRepaymentTotalYesterday()).subtract(vipTmp));
                break;
            case 13:
                sellDaily.setInvestAnnualTotalYesterday(tmp.subtract(reduceSellDaily.getInvestAnnualTotalYesterday()).subtract(qianleSellDaily.getInvestAnnualTotalYesterday()).subtract(vipTmp));
                break;
            case 14:
                sellDaily.setWithdrawTotalYesterday(tmp.subtract(reduceSellDaily.getWithdrawTotalYesterday()).subtract(qianleSellDaily.getWithdrawTotalYesterday()).subtract(vipTmp));
                break;
            case 15:
                sellDaily.setRechargeTotalYesterday(tmp.subtract(reduceSellDaily.getRechargeTotalYesterday()).subtract(qianleSellDaily.getRechargeTotalYesterday()).subtract(vipTmp));
                break;
            case 17:
                sellDaily.setNonRepaymentToday(tmp.subtract(reduceSellDaily.getNonRepaymentToday()).subtract(qianleSellDaily.getNonRepaymentToday()).subtract(vipTmp));
                break;
            case 18:
                sellDaily.setRegisterTotalYesterday(tmp.intValue() - reduceSellDaily.getRegisterTotalYesterday() - qianleSellDaily.getRegisterTotalYesterday() - vipTmp.intValue());
                break;
            case 19:
                sellDaily.setRechargeGt3000UserNum(tmp.intValue() - reduceSellDaily.getRechargeGt3000UserNum() - qianleSellDaily.getRechargeGt3000UserNum() - vipTmp.intValue());
                break;
            case 20:
                sellDaily.setInvestGt3000UserNum(tmp.intValue() - reduceSellDaily.getInvestGt3000UserNum() - qianleSellDaily.getInvestGt3000UserNum() - vipTmp.intValue());
                break;
            case 21:
                sellDaily.setInvestGt3000MonthUserNum(tmp.intValue() - reduceSellDaily.getInvestGt3000MonthUserNum() - qianleSellDaily.getInvestGt3000MonthUserNum() - vipTmp.intValue());
                break;
        }

        return sellDaily;
    }

}
