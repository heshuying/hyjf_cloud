package com.hyjf.am.trade.service.front.report.impl;

import com.hyjf.am.trade.dao.mapper.customize.TenderCityCountCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.TenderCityCount;
import com.hyjf.am.trade.dao.model.customize.TenderSexCount;
import com.hyjf.am.trade.service.front.report.TenderCityCountService;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:33
 */
@Service
public class TenderCityCountServiceImpl implements TenderCityCountService {

    @Autowired
    private TenderCityCountCustomizeMapper tenderCityCountCustomizeMapper;

    @Override
    public List<TenderCityCountVO> getTenderCityGroupBy(Date lastDay) {
        List<TenderCityCountVO> listVo = new ArrayList<>();
        List<TenderCityCount> list = tenderCityCountCustomizeMapper.getTenderCityGroupBy(lastDay);
        if (list.size() > 0) {
            listVo = CommonUtils.convertBeanList(list, TenderCityCountVO.class);
        }
        return listVo;
    }

    @Override
    public List<TenderSexCountVO> getTenderSexGroupBy(Date lastDay) {
        List<TenderSexCountVO> listVo = new ArrayList<>();
        List<TenderSexCount> list = tenderCityCountCustomizeMapper.getTenderSexGroupBy(lastDay);
        if (list.size() > 0) {
            listVo = CommonUtils.convertBeanList(list, TenderSexCountVO.class);
        }
        return listVo;
    }

    @Override
    public int getTenderAgeByRange(Date date, int firstAge, int endAge) {
        Integer i = tenderCityCountCustomizeMapper.getTenderAgeByRange(date,firstAge,endAge);
        return i;
    }

    @Override
    public BigDecimal getAccountByMonth(Date beginDate, Date endDate) {
        BigDecimal bigDecimal = tenderCityCountCustomizeMapper.getAccountByMonth(beginDate,endDate);
        return bigDecimal;
    }

    @Override
    public int getTradeCountByMonth(Date beginDate, Date endDate) {
        int i = tenderCityCountCustomizeMapper.getTradeCountByMonth(beginDate,endDate);
        return i;
    }

    @Override
    public int getLoanNum(Date date) {
        Integer i = tenderCityCountCustomizeMapper.getLoanNum(date);
        return i;
    }

    @Override
    public BigDecimal getInvestLastDate(Date date) {
        BigDecimal i = tenderCityCountCustomizeMapper.getInvestLastDate(date);
        return i;
    }

    @Override
    public int getTenderCount(Date date) {
        Integer i = tenderCityCountCustomizeMapper.getTenderCount(date);
        return i;
    }

    @Override
    public float getFullBillAverageTime(Date date) {
        float i = tenderCityCountCustomizeMapper.getFullBillAverageTime(date);
        return i;
    }

    @Override
    public BigDecimal getRepayTotal(Date date) {
        BigDecimal i = tenderCityCountCustomizeMapper.getRepayTotal(date);
        return i;
    }
}
