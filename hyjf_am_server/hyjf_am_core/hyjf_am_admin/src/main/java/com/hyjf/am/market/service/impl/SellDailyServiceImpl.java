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
    public SellDailyVO constructionSellDaily(String primaryDivision, String twoDivision) {
        return constructionSellDaily(primaryDivision, twoDivision, 0, 0);
    }

    /**
     * SellDaily 是mybatis自动生成工具生成，无法添加符合的构造函数， 此方法可以替换
     *
     * @param primaryDivision
     *            一级部门
     * @param twoDivision
     *            二级部门
     * @param drawOrder
     *            绘制顺序
     * @param storeNum
     *            门店数量
     * @return
     */
    private SellDailyVO constructionSellDaily(String primaryDivision, String twoDivision, int drawOrder, int storeNum) {
        SellDailyVO record = new SellDailyVO();
        record.setDateStr(GetDate.getFormatDateStr());
        record.setPrimaryDivision(primaryDivision);
        record.setTwoDivision(twoDivision);
        record.setDrawOrder(drawOrder);
        record.setStoreNum(storeNum);
        record.setInvestTotalMonth(BigDecimal.ZERO);
        record.setInvestTotalPreviousMonth(BigDecimal.ZERO);
        record.setRepaymentTotalMonth(BigDecimal.ZERO);
        record.setRepaymentTotalYesterday(BigDecimal.ZERO);
        record.setNonRepaymentToday(BigDecimal.ZERO);
        record.setInvestAnnualTotalMonth(BigDecimal.ZERO);
        record.setInvestAnnualTotalPreviousMonth(BigDecimal.ZERO);
        record.setInvestTotalPreviousMonth(BigDecimal.ZERO);
        record.setInvestAnnualTotalYesterday(BigDecimal.ZERO);
        record.setInvestTotalYesterday(BigDecimal.ZERO);
        record.setNonRepaymentToday(BigDecimal.ZERO);
        record.setRechargeTotalMonth(BigDecimal.ZERO);
        record.setRechargeTotalYesterday(BigDecimal.ZERO);
        record.setNetCapitalInflowYesterday(BigDecimal.ZERO);
        record.setWithdrawTotalMonth(BigDecimal.ZERO);
        record.setWithdrawTotalYesterday(BigDecimal.ZERO);
        record.setInvestAnnularRatioGrowth("");
        record.setInvestRatioGrowth("");
        record.setWithdrawRate("");
        record.setRegisterTotalYesterday(0);
        record.setInvestGt3000MonthUserNum(0);
        record.setInvestGt3000UserNum(0);
        record.setRechargeGt3000UserNum(0);
        return record;
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
    public BigDecimal addValue(BigDecimal tmp, String column, SellDailyVO entity) {
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
    public SellDailyVO constructionSellDaily(SellDailyVO ocSellDaily, String primaryDivision, String twoDivision, int drawOrder, int storeNum) {
        ocSellDaily.setDateStr(GetDate.getFormatDateStr());
        ocSellDaily.setPrimaryDivision(primaryDivision);
        ocSellDaily.setTwoDivision(twoDivision);
        ocSellDaily.setDrawOrder(drawOrder);
        ocSellDaily.setStoreNum(storeNum);
        return ocSellDaily;
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
    public void batchUpdate(List<SellDailyVO> list) {
        sellDailyCustomizeMapper.batchUpdate(list);
    }

    @Override
    public void update(SellDailyVO vo) {
        sellDailyCustomizeMapper.update(vo);
    }
}
