package com.hyjf.am.trade.service.task.impl;

import com.hyjf.am.trade.dao.mapper.customize.batch.BorrowUserStatisticsMapper;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.BorrowUserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fuqiang
 * @version BorrowUserStatisticsServiceImpl, v0.1 2018/11/20 14:23
 */
@Service
public class BorrowUserStatisticsServiceImpl extends BaseServiceImpl implements BorrowUserStatisticsService {

    @Resource
    private BorrowUserStatisticsMapper borrowUserStatisticsMapper;

    @Override
    public int countBorrowUser() {
        return borrowUserStatisticsMapper.countBorrowUser();
    }

    @Override
    public int countCurrentBorrowUser() {
        return borrowUserStatisticsMapper.countCurrentBorrowUser();
    }

    @Override
    public int countCurrentTenderUser() {
        return borrowUserStatisticsMapper.countCurrentTenderUser();
    }

    @Override
    public BigDecimal sumBorrowUserMoney() {
        Calendar calendar = Calendar.getInstance();
        // 要统计前一个月的数据，所以月份要减一
        calendar.add(Calendar.MONTH, -1);
        return borrowUserStatisticsMapper.sumBorrowUserMoney(getLastDay(calendar));
    }

    @Override
    public BigDecimal sumBorrowUserMoneyTopTen() {
        return borrowUserStatisticsMapper.sumBorrowUserMoneyTopTen();
    }

    @Override
    public BigDecimal sumBorrowUserMoneyTopOne() {
        return borrowUserStatisticsMapper.sumBorrowUserMoneyTopOne();
    }

    /**
     * 通过输入的日期，获取这个日期所在月份的最后一天
     *
     * @param cal
     * @return
     */
    public static Date getLastDay(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
