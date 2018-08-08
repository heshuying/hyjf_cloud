package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.trade.service.admin.AccountBalanceService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  11:46
 */
@Service
public class AccountBalanceServiceImpl extends BaseServiceImpl implements AccountBalanceService {

    @Override
    public int getHjhAccountBalanceMonthCountNew(HjhAccountBalanceRequest request) {
        return hjhAccountBalanceCustomizeMapper.getHjhAccountBalanceMonthCountNew(request);
    }

    @Override
    public int getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest request) {

        return hjhAccountBalanceCustomizeMapper.getHjhAccountBalanceMonthCount(request);
    }

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(HjhAccountBalanceRequest request) {
        List<HjhAccountBalanceVO> list = null;
        try{
            list = hjhAccountBalanceCustomizeMapper.getHjhAccountBalanceMonthList(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int getHjhAccountBalancecountByDay(HjhAccountBalanceRequest request) {
        return hjhAccountBalanceCustomizeMapper.getHjhAccountBalancecount(request);
    }

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceListByDay(HjhAccountBalanceRequest request) {
        return hjhAccountBalanceCustomizeMapper.getHjhAccountBalanceList(request);
    }
}
