package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.trade.dao.mapper.customize.HjhInfoAccountBalanceCustomizeMapper;
import com.hyjf.am.trade.service.admin.AccountBalanceService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  11:46
 */
@Service
public class AccountBalanceServiceImpl extends BaseServiceImpl implements AccountBalanceService {


    @Autowired
    protected HjhInfoAccountBalanceCustomizeMapper hjhInfoAccountBalanceCustomizeMapper;

    @Override
    public int getHjhAccountBalanceMonthCountNew(HjhAccountBalanceRequest request) {
        return hjhInfoAccountBalanceCustomizeMapper.getHjhAccountBalanceMonthCountNew(request);
    }

    @Override
    public int getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest request) {

        return hjhInfoAccountBalanceCustomizeMapper.getHjhAccountBalanceMonthCount(request);
    }

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(HjhAccountBalanceRequest request) {
        List<HjhAccountBalanceVO> list = null;
        try{
            list = hjhInfoAccountBalanceCustomizeMapper.getHjhAccountBalanceMonthList(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * 查询月和日合计数据
     */
    @Override
    public HjhAccountBalanceVO getHjhAccountBalanceMonthSum(HjhAccountBalanceRequest request) {
        if (request.getAddTimeStart() != null
                && request.getAddTimeEnd() != null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
                Date start = formatter.parse(request.getAddTimeStart());
                Date end = formatter.parse(request.getAddTimeEnd());
                Date firstDay = GetDate.getFirstDayOnMonth(start);
                Date lastDay = GetDate.getLastDayOnMonth(end);

                request.setYuechu(firstDay);
                request.setYuemo(lastDay);
                return hjhInfoAccountBalanceCustomizeMapper.getHjhAccountBalanceSum(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return hjhInfoAccountBalanceCustomizeMapper.getHjhAccountBalanceSum(request);
    }

    @Override
    public int getHjhAccountBalancecountByDay(HjhAccountBalanceRequest request) {
        return hjhInfoAccountBalanceCustomizeMapper.getHjhAccountBalancecount(request);
    }

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceListByDay(HjhAccountBalanceRequest request) {
        return hjhInfoAccountBalanceCustomizeMapper.getHjhAccountBalanceList(request);
    }
}
