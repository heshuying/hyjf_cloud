package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.WeeklyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version WeeklyServiceImpl, v0.1 2018/7/30 14:29
 */
@Service
public class WeeklyServiceImpl implements WeeklyService {
    @Autowired
    BorrowTenderMapper borrowTenderMapper;
    @Autowired
    CreditTenderMapper creditTenderMapper;
    @Autowired
    protected HjhAccedeMapper hjhAccedeMapper;
    @Autowired
    protected BorrowTenderCpnMapper borrowTenderCpnMapper;
    @Autowired
    protected BorrowRecoverMapper borrowRecoverMapper;
    @Autowired
    protected CreditRepayMapper creditRepayMapper;
    @Autowired
    protected CouponUserMapper couponUserMapper;





    @Override
    public List<BorrowTender> getBorrowTender(int userid, int begin, int end) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userid);
        criteria.andStatusEqualTo(1);
        criteria.andCreateTimeBetween(new Date(begin), new Date(end));
        criteria.andAccedeOrderIdIsNull();
        example.setOrderByClause(" addtime asc ");
        return borrowTenderMapper.selectByExample(example);


    }

    @Override
    public List<CreditTender> getCreditTender(int userid, String begin, String end) {
        CreditTenderExample example = new CreditTenderExample();
        CreditTenderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userid);
//		criteria.andStatusEqualTo(1);
        criteria.andCreateTimeBetween(new Date(begin), new Date(end));
        example.setOrderByClause(" add_time asc ");
        return creditTenderMapper.selectByExample(example);

    }

    @Override
    public List<HjhAccede> getAccede(int userid, int begin, int end) {
        HjhAccedeExample example =new HjhAccedeExample();
        HjhAccedeExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userid);
        criteria.andCreateTimeBetween(new Date(begin), new Date(end));

        return hjhAccedeMapper.selectByExample(example);
    }

    @Override
    public List<BorrowTenderCpn> getBorrowTenderCPN(int userid, int begin, int end) {
        BorrowTenderCpnExample example = new BorrowTenderCpnExample();
        BorrowTenderCpnExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userid);
        criteria.andCreateTimeBetween(new Date(begin), new Date(end));
        example.setOrderByClause(" addtime asc ");
        return borrowTenderCpnMapper.selectByExample(example);
    }

    @Override
    public List<BorrowRecover> getBorrowRecover(int userid, String begin, String end) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userid);
        criteria.andRecoverYestimeBetween(Integer.valueOf(begin), Integer.valueOf(end));
        example.setOrderByClause(" recover_yestime asc ");
        return borrowRecoverMapper.selectByExample(example);
    }

    @Override
    public List<CreditRepay> getCreditRepay(int userid, int begin, int end) {
        CreditRepayExample example = new CreditRepayExample();
        CreditRepayExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userid);
        criteria.andAssignRepayYesTimeBetween(begin, end);
        example.setOrderByClause(" assign_repay_yes_time asc ");
        return creditRepayMapper.selectByExample(example);
    }

    @Override
    public List<CreditRepay> getCreditRepayToCredit(int userid, int begin, int end) {
        CreditRepayExample example = new CreditRepayExample();
        CreditRepayExample.Criteria criteria = example.createCriteria();
        criteria.andCreditUserIdEqualTo(userid);
        criteria.andAssignRepayYesTimeBetween(begin, end);
        return creditRepayMapper.selectByExample(example);
    }

    @Override
    public boolean coupon(int userid) {
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userid);
        criteria.andUsedFlagEqualTo(0);
        if(couponUserMapper.selectByExample(example).size()!=0) {
            return true;
        }else {
            return false;
        }
    }



}
